package application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class SampleController {
    @FXML
    private Button addSymbol;
    @FXML
    private TextField textBox;
    @FXML
    private ObservableList<String> items = FXCollections.observableArrayList();
    @FXML
    ListView<String> lists = new ListView<>(items);

    private List<String> looper = new ArrayList<>();;
    private List<String> loopier = new ArrayList<>();
    private HashMap<String, String> sMap = new HashMap<>();

    //This method should take the symbol entered by the user and return
    //stock price from yahoo financials. Contains the code to push the data
    //to the list.
    public String getPrice(String sym){
	try {
	    if(loopier.contains(sym)){
	    }
	    else{
	    loopier.add(sym);
	    }
	    Document doc = Jsoup.connect("http://finance.yahoo.com/webservice/v1/symbols/" + sym + "/quote").userAgent("Mozilla/5.0 (Linux; <Android Version>; <Build Tag etc.>) AppleWebKit/<WebKit Rev> (KHTML, like Gecko) Chrome/<Chrome Rev> Mobile Safari/<WebKit Rev>").get();
	    StringBuilder sb = new StringBuilder(doc.toString());
	    List<String> format = new ArrayList<>();
	    Elements element = doc.select("field[name=\"price\"");
	    String elements = element.toString().replaceAll("<field name=\"price\">", "");
	    elements = elements.replaceAll("</field>", "");
	    String element2 = elements.replace("\n", "");
	    if(!sMap.containsKey(sym.toUpperCase().replace("\n", ""))){
		sMap.put(sym.toUpperCase().replace("\n", "") +": ", elements.replace("\n", ""));
	    }
	    else{
		sMap.replace(sym.toUpperCase().replace("\n", ""), elements.replace("\n", ""));
	    }
	    sMap.put(sym.toUpperCase().replace("\n", "") +": " , element2);
	    String finalPrice = sym.toUpperCase().replace("\n", "") + elements.replace("\n", "");
	    System.out.print(sMap.keySet() + elements);
	    return finalPrice;
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return null;
    }
    @FXML //This method runs automatically once the app launches
    //starts the loop to start the list refresh
    public void initialize() throws InterruptedException{
	addSymbol.setDisable(true);
	lists.setItems(items);
	Thread thread = new Thread(new Runnable() {
		public void run(){
		    refresher();
		}
		});
	thread.start();
	//StockLoop loopy = new StockLoop(looper, loopier, lists, items);
	
    }
    
    public void refresher(){	    			
		    while(true){
			Platform.runLater(() ->{
			    this.items.clear();
			});
			    for(String s: this.loopier){
				//if(this.loopier.contains(s)){
				    //continue;
				//}
				Platform.runLater(() ->{
				this.items.add(getPrice(s));
				});

			    }
			    Platform.runLater(() ->{
			    this.lists.setItems(items);
			    
			    });
			    try {
				Thread.sleep(5000);
			    } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			    }
			    
		    
		}
    }
    @FXML //button to add stock symbol in the textbox to the list
    public void onButtonClick(){
	items.add(getPrice(textBox.getText()));
	textBox.clear();
	lists.setItems(items);

    }
    @FXML 
    //Checks to make sure there is text to add to the list, button will be disabled otherwise.
    //TODO add more safety checks so that user cannot screw themselves.
    public void handleKeyReleased(){
	String text = textBox.getText();
	boolean disableButtons = text.isEmpty() || text.trim().isEmpty();
	addSymbol.setDisable(disableButtons);
	
    }
    @FXML //Allows stocks to be added when enter is pressed on the textbox.
    public void onEnter(ActionEvent e){
	onButtonClick();
    }
}

//Class to create another thread to refresh the stock list.
//class StockLoop extends SampleController implements Runnable{
//    List<String> looper;
//    List<String> loopier;
//    ListView<String> lists;
//    ObservableList<String> items;


//    public StockLoop(List<String> looper, List<String> loopier, ListView<String> lists, ObservableList<String> items) {
//	this.looper = looper;
//	this.loopier = loopier;
//	this.lists = lists;
//	this.items = items;
//    
//
//
//	public void run(){
//	    
//	Platform.runLater(() -> {
//	    public void  run(){
//	try{ while(true){
//	    Thread.sleep(1000);
//	    items.clear();
//	    looper.clear();
//	    lists.setItems(items);
//	    for(String s: loopier){
//		if(loopier.contains(s)){
//		    continue;
//		}
//		items.add(getPrice(s));
//
//	    }
//	    
//	    lists.setItems(items);
//	}
//	} catch(Exception e){
//	    System.err.print(e); 
//	    }
//	    }});
//
//    }
//    
//}
