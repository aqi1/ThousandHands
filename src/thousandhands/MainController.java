/* Controller for Home screen */

package thousandhands;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainController {
	
	// Main.fxml elements

    @FXML
    private Button ButtonNeed;

    @FXML
    private Button ButtonGive;

    @FXML
    private Button ButtonInventory;
    
    /* WINDOW CHANGE METHODS */
    
    // Change window to the Donate screen
	@FXML
    private void replaceSceneContentDonate(ActionEvent event){
		Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("NeedGive.fxml"));
			NeedGiveController newControl = new NeedGiveController();
			loader.setController(newControl);
			AnchorPane root = (AnchorPane)loader.load();
			newControl.loadProp();
			newControl.setLabel("Donate");
			primaryStage.getScene().setRoot(root);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
	
	// Change window to the Need screen
	@FXML
    private void replaceSceneContentNeed(ActionEvent event){
		Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("NeedGive.fxml"));
			NeedGiveController newControl = new NeedGiveController();
			loader.setController(newControl);
			AnchorPane root = (AnchorPane)loader.load();
			newControl.loadProp();
			newControl.setLabel("Retrieve");
			primaryStage.getScene().setRoot(root);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

	// Change window to the Inventory screen
	@FXML
    private void replaceSceneContentInventory(ActionEvent event){
		Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Inventory.fxml"));
			InvController newControl = new InvController();
			loader.setController(newControl);
			AnchorPane root = (AnchorPane)loader.load();
			newControl.loadProp();
			primaryStage.getScene().setRoot(root);
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
	
}