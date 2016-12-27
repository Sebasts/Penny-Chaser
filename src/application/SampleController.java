package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SampleController {
    @FXML
    private TextField textBox;
    @FXML
    private Button hiButton;
    @FXML
    private Button byeButton;
    
    
    public void onButtonClick(ActionEvent e){
	if(e.getSource().equals(hiButton)){
	    System.out.println("Hello, " + textBox.getText());
	}
	else{
	    System.out.print("Bye, " + textBox.getText());
	}
    }
    public void onButtonRelease(){
	
    }
}
