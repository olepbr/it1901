package mememedb.ui;

//File utilities
import java.io.IOException;
//Import javafx stuff
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
//Mememedb
import mememedb.datastructures.User;
import mememedb.io.IO;
import mememedb.io.LocalIO;


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
    // Set up Browser window, and add it to the scene'
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
