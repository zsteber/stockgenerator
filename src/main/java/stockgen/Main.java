package stockgen;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;

public class Main {

    private static ArrayList<StockInfo> stockInfoList;
    private static Account account1;

    public static void main(String[] args) throws Exception {
        ReadJSONData();

        ConvertHTMLToPDF();
    }

    public static void ReadJSONData(){
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("stock_transations.by.account.holder.json"))
        {
            Object obj = jsonParser.parse(reader);

            JSONArray accountList = (JSONArray) obj;

            accountList.forEach( account -> {
                try {
                    CreateAccount( (JSONObject) account );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void CreateAccount (JSONObject account) throws Exception {
        Long accountNumber = (Long) account.get("account_number");
        String ssn =  (String) account.get("ssn");
        String firstName = (String) account.get("first_name");
        String lastName = (String) account.get("last_name");
        String email = (String) account.get("email");
        String phoneNumber = (String) account.get("phoneNumber");
        String startingBalance = (String) account.get("starting_balance");

        Double actualBalance = Double.parseDouble(startingBalance.replaceAll("[$]", ""));

        JSONArray trades = (JSONArray) account.get("trades");

        stockInfoList = new ArrayList<StockInfo>();

        trades.forEach(stockInfo -> stockInfoList.add(HandleStockTrades( (JSONObject) stockInfo)));

        account1 = new Account(accountNumber, ssn, firstName, lastName, email, phoneNumber, actualBalance);
        account1.setStockTradeList(stockInfoList);

        HandleCashAndStockHoldings(account1);
        ConvertJSONToHTML(account1);
    }

    public static void HandleCashAndStockHoldings(Account account) {
        account.getStockTradeList().forEach(stockTrade -> {
            if (stockTrade.getType().equals("Buy")){
                account.setCash_amount(( account.getCash_amount() - (stockTrade.getPricePerShare() * stockTrade.getCountShares())));
                account.setStock_holdings((double) account.getStock_holdings() + stockTrade.getCountShares());
			} else {
                account.setCash_amount(account.getCash_amount() + (stockTrade.getPricePerShare() * stockTrade.getCountShares()));
                account.setStock_holdings((double) account.getStock_holdings() - stockTrade.getCountShares());
            }
        });
    }

    public static StockInfo HandleStockTrades(JSONObject stock) {
        String type = (String) stock.get("type");
        String stockSymbol = (String) stock.get("stock_symbol");
        Long shareCount = (Long) stock.get("shares");
        String pricePerShare = (String) stock.get("price_per_share");

        Double perShare = Double.parseDouble(pricePerShare.replaceAll("[$]", ""));

        StockInfo stockInfo = new StockInfo(type, stockSymbol, shareCount, perShare);

        return stockInfo;
    }

    public static void ConvertJSONToHTML(Account account) throws Exception {
        OutputStream outputStream = new FileOutputStream("./HTMLFiles/" + account.getAccount_number() + ".html");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);

        outputStreamWriter.write("<!DOCTYPE html>");
        outputStreamWriter.write("<html><body>");

        outputStreamWriter.write(String.format("%s", account.toString()));

        outputStreamWriter.write("<table>");
        outputStreamWriter.write("<tr>" +
                "<th>Type</th>" +
                "<th>Stock Symbol</th>" +
                "<th>Count of Shares</th>" +
                "<th>Price Per Share</th>" +
                "</tr>");
        account.getStockTradeList().forEach(stockTrade -> {
            try {
                outputStreamWriter.write(String.format("%s", stockTrade.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        outputStreamWriter.write("</table>");

        outputStreamWriter.write("<h3> Current Cash Balance: " + account.getCash_amount() + "</h3>");
        outputStreamWriter.write("<h3> Current Stock Holdings: " + account.getStock_holdings() + "</h3>");

        outputStreamWriter.write("</body></html>");

        outputStreamWriter.close();
    }

    public static void ConvertHTMLToPDF() throws Exception {
        File dir = new File("./HTMLFiles/");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                String filename = child.toString().replaceAll(".\\\\HTMLFiles\\\\", "");
                filename = filename.replaceAll(".html", "");
                try (OutputStream os = new FileOutputStream("./PDF/" + filename + ".pdf")) {
                    PdfRendererBuilder builder = new PdfRendererBuilder();
                    builder.withUri("file:///C:\ A. Neumont\ Quarter 5\ CSC180\ stockgenerator\ htmls" + filename + ".html");
                    builder.toStream(os);
                    builder.run();
                }

            }
        } else {
        }
    }
}