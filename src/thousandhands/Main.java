package thousandhands;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	private Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
			loader.setController(new MainController());
			AnchorPane root = (AnchorPane)loader.load();
			Scene scene = new Scene(root,640,480);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			this.primaryStage.setScene(scene);
			this.primaryStage.setResizable(false);
			this.primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
