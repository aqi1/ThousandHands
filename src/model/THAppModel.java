package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Pattern;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class THAppModel{

	// Donate items (add to hashmap or add to the value). Used in: Donate screen.
	public void addItem(String username, String key, int value, HashMap<String, Integer> hm) throws IOException{
		
		// Check if items already exist and append quantity if it does
		if(hm.containsKey(key)){
			// Append quantity
			hm.put(key, hm.get(key) + value);
		}
		else{
			hm.put(key, value);
		}
		
		// Show confirmation dialog
		Alert confirm = new Alert(AlertType.CONFIRMATION, "Donated " + value + " " + key + ". Thanks " + username + "!", ButtonType.OK);
		confirm.show();
		
		// Save to file
		Properties properties = new Properties();
		File file = new File("data.properties");
		FileOutputStream writer = new FileOutputStream(file, true);
		properties.setProperty(key, "" + hm.get(key));
		properties.store(writer, null);
		writer.close();
		
	}
	
	// Retrieve items (delete from hashmap or subtract from the value). Used in: Retrieve screen.
	public void subtractItem(String username, String key, int value, HashMap<String, Integer> hm) throws IOException{
		
		Alert a = new Alert(AlertType.ERROR), confirm;
		Properties properties = new Properties();
		File file = new File("data.properties");
		
		if(hm.containsKey(key)){
			int existingQuantity = hm.get(key);
			
			// If asking for more than exists, print error
			if(existingQuantity < value){
				a.setTitle("Requested Quantity Exceeds Existing Quantity");
				a.setContentText("The requested quantity of " + key + " exceeds the amount that we have. Please request a smaller number.");
				a.show();
			}
			// Else if asking for exactly the amount that exists, remove elements from the hashmap
			else if(existingQuantity == value){
				hm.remove(key);
				confirm = new Alert(AlertType.CONFIRMATION, "Retrieved " + value + " " + key + ". Enjoy, " + username + "!", ButtonType.OK);
				confirm.show();
				
				// Save to file
				FileOutputStream writer = new FileOutputStream(file, true);
				properties.setProperty(key, "" + 0);
				properties.store(writer, null);
				writer.close();
			}
			// Else subtract the requested quantity from the existing quantity
			else{
				hm.put(key, existingQuantity - value);
				confirm = new Alert(AlertType.CONFIRMATION, "Retrieved " + value + " " + key + ". Enjoy, " + username + "!", ButtonType.OK);
				confirm.show();
				
				// Save to file
				FileOutputStream writer = new FileOutputStream(file, true);
				properties.setProperty(key, "" + (existingQuantity - value));
				properties.store(writer, null);
				writer.close();
			}
		}
		else{ // The requested item doesn't exist, so print error
			a.setTitle("Cannot Find Item");
			a.setContentText("The donation bank does not have any " + key + ". Sorry!");
			a.show();
		}
	}
	
	// Get count of specified key and show in a dialog. Used in: Inventory screen.
	public void getNumberOfItemsInInventory(String key, HashMap<String, Integer> hm){
		
		// Validate input
		if(key.isEmpty()){
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("Search Criteria");
			a.setContentText("Please enter an item to search for.");
			a.show();
		}
		else{
			Alert confirm;
			if(hm.containsKey(key)){
				confirm = new Alert(AlertType.CONFIRMATION, "The donation bank has " + hm.get(key) + " " + key + ".", ButtonType.OK);
				confirm.setTitle("Match Found!");
			}
			else{
				confirm = new Alert(AlertType.CONFIRMATION, "The donation bank does not have any " + key  + ". Sorry!", ButtonType.OK);
				confirm.setTitle("No Matches");
			}
			confirm.setHeaderText(null);
			confirm.setGraphic(null);;
			confirm.show();
		}
	}
	
	/* Add a username. If the username already exists, then do nothing. If it does not
	 * exist, then add it to the list of names. Also, validate ABC123 format.
	 * Used in: Donate screen.
	 * 
	 * NOTE: username data is not actually used in the UI or logged in relation to donated items
	 * since it was not a part of the lab requirements for lab #4 or #6
	 * so this method will just check that the name is valid and store it in a .properties file
	 * as specified in lab #7 requirements
	 */
	public boolean addUserName(String name) throws IOException{
		
		Alert a = new Alert(AlertType.ERROR);
		boolean nameAlreadyExists = false;
		
		// Validate name input follows abc123 format
		if(!(Pattern.matches("[a-z]{3}[0-9]{3}", name))){
			a.setTitle("Name Format Error");
			a.setContentText("Name must follow the format abc123.");
			a.show();
			return false;
		}
		
		// Load file
		try{
			Properties properties = new Properties();
			File file = new File("users.properties");
			
			FileInputStream reader = new FileInputStream(file);
			FileOutputStream writer = new FileOutputStream(file, true);
			
			properties.load(reader);
			
			// Check if the user name already exists in the file
			for(String key: properties.stringPropertyNames()){
				if(key.equals(name)){
					nameAlreadyExists = true;
				}
			}
			
			// If it does not exist, add it to the file
			if(!nameAlreadyExists){
				properties.setProperty(name, "");
				properties.store(writer, null);
			}
			
			writer.close();
		} catch(FileNotFoundException e){
			System.out.println("Could not find username properties file (it probably does not exist). Creating one.");
			File file = new File("users.properties");
			file.createNewFile();
		} catch(IOException ioE){
			System.out.println("IOException with loading username properties file");
			ioE.printStackTrace();
		}
		
		return true;
	}
}
