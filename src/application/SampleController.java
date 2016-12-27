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
    private ListView<String> lists = new ListView<>();
    @FXML
    ObservableList<String> items = FXCollections.observableArrayList();
    
    
    public String getPrice(String sym){
	try {
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
//    @FXML
//    public void initialize(){
//	addSymbol.setDisable(true);
//	ListView<String> lists = new ListView<>();
//	ObservableList<String> items = FXCollections.observableArrayList();
//	lists.setItems(items);
//    }
    
    @FXML
    public void onButtonClick(ActionEvent e){
	items.add(getPrice(textBox.getText()));
	textBox.clear();
	lists.setItems(this.items);
    }
    @FXML
    public void onButtonRelease(ActionEvent e){
	String text = textBox.getText();
	addSymbol.setDisable(text.isEmpty() || text.trim().isEmpty());
	
    }
}
	
