package application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Callback;

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
    @FXML
    ListView<String> listsColor;

    private List<String> upOrDown = new ArrayList<>();;
    private List<String> loopier = new ArrayList<>();
    private HashMap<String, String> sMap = new HashMap<>();
    private HashMap<String, Double> sMap2 = new HashMap<>();

    //This method should take the symbol entered by the user and return
    //stock price from yahoo finance. Contains the code to push the data
    //to the list.
    public String getPrice(String sym){
	try {

	    String holder = null;
	    double holder2;
	    if(loopier.contains(sym)){
	    }
	    else{
		loopier.add(sym);
	    }
	    Document doc = Jsoup.connect("http://finance.yahoo.com/webservice/v1/symbols/" + sym + "/quote").userAgent("Mozilla/5.0 (Linux; <Android Version>; <Build Tag etc.>) AppleWebKit/<WebKit Rev> (KHTML, like Gecko) Chrome/<Chrome Rev> Mobile Safari/<WebKit Rev>").get();
	    Elements element = doc.select("field[name=\"price\"");
	    String elements = element.toString().replaceAll("<field name=\"price\">", "");
	    elements = elements.replaceAll("</field>", "");
	    String element2 = elements.replace("\n", "");
	    if(!sMap.containsKey(sym.toUpperCase().replace("\n", ""))){
		sMap.put(sym.toUpperCase().replace("\n", "") +": ", elements.replace("\n", ""));
	    }
	    else{
		holder = sMap.get(sym);
		holder2 = Double.parseDouble(holder);
		for(int i=0; i < items.size(); i++){
		    if(loopier.contains(sym)){
			if(holder2 < Double.parseDouble(element2)){
			    upOrDown.add(loopier.indexOf(sym), "down");
			}
			else if(holder2 > Double.parseDouble(element2)){
			    upOrDown.add(loopier.indexOf(sym), "up");
			}
			else{
			    upOrDown.add(loopier.indexOf(sym), "neither");
			}
		    }
		}
	    }
		sMap.replace(sym.toUpperCase().replace("\n", ""), elements.replace("\n", ""));

		sMap2.put(sym.toUpperCase().replace("\n", "") +": " , Double.parseDouble(element2));

//		if(Double.parseDouble(element2) == Double.parseDouble(holder)){
//
//		}
		String finalPrice = sym.toUpperCase().replace("\n", "") + element2;
		System.out.print(sMap.keySet() + elements);
		return finalPrice;
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    return null;
	}
	@FXML //This method runs automatically once the app launches
	//starts the loop to start the list refreshes
	//Also creates a thread that runs the code to update the stock
	//prices every 5 seconds
	public void initialize() throws InterruptedException{
	    upOrDown.add("neither");
	    upOrDown.add("neither");
	    upOrDown.add("neither");
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
	//This should allow for stock prices to be updated in the ListView
	//every 5 seconds.
	public void refresher(){	    			
	    
	    while(true){
//		for(int i = 0; i < items.size(); i++){
//		    if(upOrDown.get(i).equals("neither")){
//			scene.getStyleSheets();
//			    };
//		    }
//		}
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
		    lists.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
		            @Override
		            public ListCell<String> call(ListView<String> lists) {
		                return new ListCell<String>(){
		                    @Override
		                    protected void updateItem(String itemss, boolean b) {
		                        super.updateItem(itemss, b);    //To change body of overridden methods use File | Settings | File Templates.
		                        
		                        if (items.contains(itemss)) {
		                            if(upOrDown.get(items.indexOf(itemss)).equals("neither")){		                        	               
		                          setTextFill(Color.GREY);
		                          setText(itemss);
		                          
		                            }
		                            else if(upOrDown.get(items.indexOf(itemss)).equals("up")){
		                        	setTextFill(Color.GREEN);
			                          setText(itemss);
		                            }
		                            else if(upOrDown.get(items.indexOf(itemss)).equals("down")){
		                        	setTextFill(Color.RED);
			                          setText(itemss);
		                            }
		                         //   setGraphic(null);
		                        }
//		                        else if (itemss.contains("up")){
//		                            
//		                       //             setGraphic(your graphics);
//		                        //    setText(your text);
//		                        }
//		                        else if (itemss.contains("down")){
//		                            setStyle("-fx-text-fill: red;");
//		                        //    setGraphic(your graphics);
//		                       //     setText(your text);
//		                        }
		                    }
		                };
		            }
		            
		        });
		    
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

 
