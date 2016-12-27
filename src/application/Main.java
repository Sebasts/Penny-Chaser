package application;
	
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
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
		SampleController samp = new SampleController();
		System.out.print(samp.getPrice("amrs"));
	}
}
//Stock class that will hold a List of StockSymbols as well as passing data to the ListView
class Stocks{
    private List<StockSymbols> stockList;
    
    public Stocks() {
	ListView<String> lists = new ListView<>();
	ObservableList<String> items = FXCollections.observableArrayList();
	lists.setItems(items);
    }


    public List<StockSymbols> newSymbol(String symbol){
	this.stockList = new ArrayList<StockSymbols>();
	this.stockList.add(new StockSymbols(symbol));
	return this.stockList;
    }
    
    
}