package it1901.mememedb.fxui;

//File utilities
import java.io.IOException;
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
import it1901.mememedb.core.io.IO;
import it1901.mememedb.core.io.LocalIO;

import it1901.mememedb.core.datastructures.Post;
import it1901.mememedb.core.datastructures.User;

public class AppController {

  // Storage interface
  private IO memeio;
  private User activeUser;

  @FXML AnchorPane window;

  /**
   * Initializes the application, and loads the starter login interface.
   */
  @FXML public void initialize() {
    memeio = new LocalIO();
    // Set up Browser window, and add it to the scene
    handleLogOut();
  }
  
  /**
   * Updates active User of app, and switches over to Browser ui.
   * 
   * @param user The User that logged on.
   */
  public void handleLogin(User user) {
    activeUser = user;
    window.getChildren().clear();
    AnchorPane browser = new AnchorPane();
    FXMLLoader subContentLoader = new FXMLLoader(getClass().getResource("Browser.fxml"));
    subContentLoader.setController(getClass().getResource("BrowserController.java"));
    subContentLoader.setRoot(browser);
    window.getChildren().add(browser);
    try {
      subContentLoader.load();
      ((BrowserController) subContentLoader.getController()).setActiveUser(activeUser);
      ((BrowserController) subContentLoader.getController()).setIO(memeio);
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Error loading content browser");
    }
  }
  
  
  /**
   * Clears active User, and returns to login ui.
   */
  public void handleLogOut() {
    activeUser = null;
    AnchorPane login = new AnchorPane();
    FXMLLoader subContentLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
    subContentLoader.setController(getClass().getResource("LoginController.java"));
    subContentLoader.setRoot(login);
    window.getChildren().add(login);
    try {
      subContentLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Error loading content browser");
    }
  }




}
