package mememedb.ui;

//Import javafx stuff
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

//Java utils:
import java.util.List;

//File utilities
import java.io.File;
import java.io.IOException;

//Mememe
import mememedb.io.IO;
import mememedb.io.LocalIO;

import mememedb.datastructures.Post;
import mememedb.datastructures.User;

public class AppController {

	// Storage interface
	private IO memeIO;
	private User activeUser;
	
	@FXML AnchorPane window;
	
	
	@FXML
	public void initialize() {
		memeIO = new LocalIO();
		// Set up Browser window, and add it to the scene'
		AnchorPane browser = new AnchorPane();
		FXMLLoader subContentLoader = new FXMLLoader(getClass().getResource("Browser.fxml"));
		subContentLoader.setController(getClass().getResource("BrowserController.java"));
		subContentLoader.setRoot(browser);
		window.getChildren().add(browser);
		try {
			subContentLoader.load();
			((BrowserController) subContentLoader.getController()).setIO(memeIO);
			((BrowserController) subContentLoader.getController()).setActiveUser(activeUser);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error loading content browser");
		}
	}
	
	
	
	
}
