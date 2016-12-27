package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Penny Chaser");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

class Stocks{
    private List<StockSymbols> stockList;
    
    public Stocks(List<StockSymbols> stockList) {
	this.stockList = stockList;
    }


    public List<StockSymbols> newSymbol(String symbol){
	this.stockList = new ArrayList<StockSymbols>();
	this.stockList.add(new StockSymbols(symbol));
	return this.stockList;
    }
    
    
}