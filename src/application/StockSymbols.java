package application;

public class StockSymbols {
    private String symbol;
    private int stockPrice;
    private int oldStockPrice;
    private int newStockPrice;
    
    public StockSymbols(String stockSymbol) {
	this.symbol = stockSymbol;
    }
    
    //This will provide the info that will populate the list for this symbol
    public String toString(){
	String stockString = this.symbol +": "+this.stockPrice;
	return stockString;
    }
    public String getStockSymbol() {
        return symbol;
    }
    public void setStockSymbol(String stockSymbol) {
        this.symbol = stockSymbol;
    }
    public int getStockPrice() {
        return stockPrice;
    }
    public void setStockPrice(int stockPrice) {
        this.stockPrice = stockPrice;
    }
    public int getOldStockPrice() {
        return oldStockPrice;
    }
    public void setOldStockPrice(int oldStockPrice) {
        this.oldStockPrice = oldStockPrice;
    }
    public int getNewStockPrice() {
        return newStockPrice;
    }
    public void setNewStockPrice(int newStockPrice) {
        this.newStockPrice = newStockPrice;
    }
    
}
