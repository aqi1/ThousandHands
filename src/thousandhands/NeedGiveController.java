/* Controller for Donate and Retrieve screens */

package thousandhands;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import model.THAppModel;

public class NeedGiveController {
	
	HashMap<String, Integer> hashmapdonations = new HashMap<String, Integer>();
	THAppModel model = new THAppModel();
	
    @FXML
    private Button ButtonHome;
	

    // NeedGive.fxml elements
    
    @FXML
    private Label LabelDonateNeed;

    @FXML
    private Button ButtonName;

    @FXML
    private Button ButtonQty;

    @FXML
    private Button ButtonItem;
    
    @FXML
    private Button ButtonAdd;
    
    @FXML
    private TextField TextName;
    
    @FXML
    private TextField TextItem;
    
    @FXML
    private TextField TextQty;
    
    /* WINDOW CHANGE METHODS */
	
	// Change window to the Main (home) screen
	@FXML
    private void replaceSceneContentHome(ActionEvent event){
		Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
			loader.setController(new MainController());
			AnchorPane root = (AnchorPane)loader.load();
			primaryStage.getScene().setRoot(root);
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
	
	/* DONATE AND RETRIEVE */
	
	@FXML
	private void addOrRemoveDonation(ActionEvent event) throws IOException{
		
		// Username-related
		String name = TextName.getText();
		boolean nameIsValid = false;
		
		// Hashmap-related
		String itemName = TextItem.getText();
		int itemQuantity;
		Alert a = new Alert(AlertType.ERROR);
		
		// Validate input on name and itemName -- must not be null
		if(name.isEmpty() || itemName.isEmpty()){
			a.setTitle("Name or Item Name Error");
			a.setContentText("Name or item name must not be blank.");
			a.show();
		}
		
		// Validate name input follows abc123 format
		nameIsValid = model.addUserName(name);
			
		// Get itemQuantity. Validate input is a positive integer
		try{
			itemQuantity = Integer.parseInt(TextQty.getText());
		}
		catch(NumberFormatException e){
			itemQuantity = 0;
			a.setTitle("Quantity Error");
			a.setContentText("Quantity must be an integer.");
			a.show();
		}
		
		if(itemQuantity < 1){
			a.setTitle("Quantity Error");
			a.setContentText("Quantity must be a positive integer.");
			a.show();
		}
		
		// Execute if() block if it's the Donate page; execute the else() block if it's the Retrieve page
		if(LabelDonateNeed.getText().equals("Donate")){ // Donate logic
			// Make sure require fields have been populated appropriately, then modify hashmap
			if(!name.isEmpty() && !itemName.isEmpty() && itemQuantity > 0 && nameIsValid){
				model.addItem(name, itemName,  itemQuantity, hashmapdonations);
			}
		}
		else{ // Retrieve logic
			// Make sure require fields have been populated appropriately, then modify hashmap based on quantity conditions
			if(!name.isEmpty() && !itemName.isEmpty() && itemQuantity > 0 && nameIsValid){
				model.subtractItem(name, itemName, itemQuantity, hashmapdonations);
			}
		}
		
		// Clear the fields as mentioned in the project description
		TextName.clear();
		TextItem.clear();
		TextQty.clear();
		
	}
	
	// Load data from properties file
	protected void loadProp(){
		try{
			File file = new File("data.properties");
			FileInputStream reader = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(reader);
			
			// Store property values into hashmapdonations
			for(String key: properties.stringPropertyNames()){
				int value = Integer.parseInt(properties.get(key).toString());
				if(value != 0){
					hashmapdonations.put(key, value);
				}
			}
		} catch(FileNotFoundException e){
			System.out.println("Could not find properties file (it probably does not exist)");
		} catch(IOException ioE){
			System.out.println("IOException with loading properties file");
			ioE.printStackTrace();
		}
	}
	
	// Set label
	public void setLabel(String s){
		this.LabelDonateNeed.setText(s);
	}
	
}