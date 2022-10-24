package stockgen;

public class StockInfo {
        private String type;
        private String stockSymbol;
        private Long countShares;
        private Double pricePerShare;
    
        public StockInfo(String type, String stockSymbol, Long countShares, Double pricePerShare) {
            this.type = type;
            this.stockSymbol = stockSymbol;
            this.countShares = countShares;
            this.pricePerShare = pricePerShare;
        }
    
        public String getType() {
            return type;
        }
    
        public void setType(String type) {
            this.type = type;
        }
    
        public String getStockSymbol() {
            return stockSymbol;
        }
    
        public void setStockSymbol(String stockSymbol) {
            this.stockSymbol = stockSymbol;
        }
    
        public Long getCountShares() {
            return countShares;
        }
    
        public void setCountShares(Long countShares) {
            this.countShares = countShares;
        }
    
        public Double getPricePerShare() {
            return pricePerShare;
        }
    
        public void setPricePerShare(Double pricePerShare) {
            this.pricePerShare = pricePerShare;
        }
    
        @Override
        public String toString() {
            return "<tr>" +
                    "<td>" + type + "</td><br/>" +
                    "<td>" + stockSymbol + "</td><br/>" +
                    "<td>" + countShares + "</td><br/>" +
                    "<td> $" + pricePerShare + "</td><br/>" +
                    "</tr>";
        }
}
