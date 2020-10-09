package fxui;

import core.datastructures.Database;
import core.datastructures.User;
import core.io.IO;
import core.io.LocalIO;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class AppController {

  // Storage interface
  private Database database;
  private User activeUser;

  @FXML AnchorPane window;

  /** Initializes the application, and loads the starter login interface. */
  @FXML
  public void initialize() {
    IO io = new LocalIO();
    database = new Database(io);
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
    FXMLLoader subContentLoader =
        new FXMLLoader(getClass().getClassLoader().getResource("Browser.fxml"));
    subContentLoader.setController(getClass().getResource("BrowserController.java"));
    subContentLoader.setRoot(browser);
    window.getChildren().add(browser);
    try {
      subContentLoader.load();
      ((BrowserController) subContentLoader.getController()).setActiveUser(activeUser);
      ((BrowserController) subContentLoader.getController()).setDatabase(database);
      ((BrowserController) subContentLoader.getController()).setParent(this);
      ((BrowserController) subContentLoader.getController()).updatePosts();
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Error loading content browser");
    }
  }

  /** Clears active User, and returns to login ui. */
  public void handleLogOut() {
    activeUser = null;
    window.getChildren().clear();
    AnchorPane login = new AnchorPane();
    FXMLLoader subContentLoader =
        new FXMLLoader(getClass().getClassLoader().getResource("Login.fxml"));
    subContentLoader.setController(getClass().getResource("LoginController.java"));
    subContentLoader.setRoot(login);
    window.getChildren().add(login);
    try {
      subContentLoader.load();
      ((LoginController) subContentLoader.getController()).setDatabase(database);
      ((LoginController) subContentLoader.getController()).setParent(this);
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Error loading content browser");
    }
  }
}
