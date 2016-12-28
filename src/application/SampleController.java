package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
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
    private ListView<String> lists = new ListView<>(items);
    
    private List<String> looper;
    private List<String> loopier = new ArrayList<>();
    
    
    public String getPrice(String sym){
	try {
	    if(!loopier.contains(sym)){
		loopier.add(sym);
	    }
	    Document doc = Jsoup.connect("http://finance.yahoo.com/webservice/v1/symbols/" + sym + "/quote").userAgent("Mozilla/5.0 (Linux; <Android Version>; <Build Tag etc.>) AppleWebKit/<WebKit Rev> (KHTML, like Gecko) Chrome/<Chrome Rev> Mobile Safari/<WebKit Rev>").get();
	    StringBuilder sb = new StringBuilder(doc.toString());
	    List<String> format = new ArrayList<>();
	    Elements element = doc.select("field[name=\"price\"");
	    String elements = element.toString().replaceAll("<field name=\"price\">", "");
	    elements = elements.replaceAll("</field>", "");
	    String finalPrice = sym.toUpperCase().replace("\n", "") +": "+elements.replace("\n", "");
	    return finalPrice;
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return null;
    }
    @FXML
    public void initialize(){
	this.addSymbol.setDisable(true);
	this.lists.setItems(items);
	StockLoop loopy = new StockLoop();
	loopy.run();
	
    }
    
    
    
    
    @FXML
    public void onButtonClick(){
	this.items.add(getPrice(textBox.getText()));
	textBox.clear();
	lists.setItems(items);
    }
    @FXML
    public void handleKeyReleased(){
	String text = textBox.getText();
	boolean disableButtons = text.isEmpty() || text.trim().isEmpty();
	addSymbol.setDisable(disableButtons);	
    }
    @FXML
    public void onEnter(ActionEvent e){
	onButtonClick();
    }
}
class StockLoop extends SampleController implements Runnable{
    List<String> looper;
    List<String> loopier;
    ListView<String> lists;
    ObservableList<String> items;
    
    public void  run(){
	try{ while(true){
	    Thread.sleep(1000);
	    items.clear();
	    looper.clear();
	    lists.setItems(items);
	    for(String s: loopier){
		if(loopier.contains(s)){
		    continue;
		}
		items.add(getPrice(s));
		
	    }
	}
	} catch(Exception e){
	    System.err.print(e);
	}
	
    }
}
