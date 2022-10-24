package stockgen;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Account {
    private Long account_number;
    private String ssn;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private Double startingBalance;
    private Double cash_amount;
    private Double stock_holdings;
    private ArrayList<StockInfo> stockTradeList;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

    public Account(Long account_number, String ssn, String first_name, String last_name, String email, String phone, Double startingBalance) {
        this.account_number = account_number;
        this.ssn = ssn;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
        this.startingBalance = startingBalance;
        this.cash_amount = startingBalance;
        this.stock_holdings = 0.0;
    }

    public Long getAccount_number() {
        return account_number;
    }

    public void setAccount_number(Long account_number) {
        this.account_number = account_number;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getStartingBalance() {
        return startingBalance;
    }

    public void setStarting_balance(Double startingBalance) {
        this.startingBalance = startingBalance;
    }

    public Double getCash_amount() {
        return cash_amount;
    }

    public void setCash_amount(Double cash_amount) {
        this.cash_amount = cash_amount;
    }

    public Double getStock_holdings() {
        return stock_holdings;
    }

    public void setStock_holdings(Double stock_holdings) {
        this.stock_holdings = stock_holdings;
    }

    public ArrayList<StockInfo> getStockTradeList() {
        return stockTradeList;
    }

    public void setStockTradeList(ArrayList<StockInfo> stockTradeList) {
        this.stockTradeList = stockTradeList;
    }

    @Override
    public String toString() {
        return "<h2>Account</h2> <br/>" +
                "<p> Date Printed: " + dateTimeFormatter.format(LocalDateTime.now()) + "</p><br/>" +
                "<p> Account Number: " + account_number + "</p><br/>" +
                "<p> First Name: " + first_name + "</p><br/>" +
                "<p> Last Name: " + last_name + "</p><br/>" +
                "<p> Email: " + email + "</p><br/>" +
                "<p> Phone: " + phone + "</p><br/>" +
                "<p> Starting Balance: $" + startingBalance + "</p>";
    }
}