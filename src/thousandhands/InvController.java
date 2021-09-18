/* Controller for Inventory screen*/

package thousandhands;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import model.THAppModel;

public class InvController {
	
	HashMap<String, Integer> hashmapdonations = new HashMap<String, Integer>();
	THAppModel model = new THAppModel();
	
    @FXML
    private Button ButtonHome;
    
	// Inventory.fxml elements
    
    @FXML
    private ListView<String> ListViewInventory;
    
    @FXML
    private Button ButtonSearch;
    
    @FXML
    private TextField TextSearch;
    
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
	
	/* INVENTORY METHODS */
	
	@FXML
	private void inventorySearch(ActionEvent event){
		
		String searchString = TextSearch.getText();
		model.getNumberOfItemsInInventory(searchString, hashmapdonations);
	}
	
	// Populates the TextField with the item clicked on in the ListView
	@FXML
	private void populateSearch(MouseEvent event){
		if(!hashmapdonations.isEmpty()){
			TextSearch.setText(ListViewInventory.getSelectionModel().getSelectedItem());
		}
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
			
			// Load table
			populateTable();
			
		} catch(FileNotFoundException e){
			System.out.println("Could not find properties file (it probably does not exist)");
		} catch(IOException ioE){
			System.out.println("IOException with loading properties file");
			ioE.printStackTrace();
		}
	}
	
	private void populateTable(){
		for(Map.Entry<String,Integer> entry : hashmapdonations.entrySet()){
			ListViewInventory.getItems().add(entry.getKey());
		}
	}
}