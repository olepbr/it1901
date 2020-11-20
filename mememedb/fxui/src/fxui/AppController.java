package fxui;

import core.databases.DatabaseFactory;
import core.databases.DatabaseInterface;
import core.datastructures.User;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class AppController {

  // Storage interface
  private DatabaseInterface database;
  private User activeUser;
  private Object child;

  @FXML AnchorPane window;

  /** Initializes the application, and loads the starter login interface. */
  @FXML
  public void initialize() {
    database = new DatabaseFactory().getDatabase("local");
    // Set up Browser window, and add it to the scene
    handleLogOut();
  }

  /**
   * Sets the database to use in the app by passing the argument to a DatabaseFactory.
   *
   * @param databaseType The type of database, e.g. "rest" for a RestDatabase
   */
  public void setDatabase(String databaseType) {
    database = new DatabaseFactory().getDatabase(databaseType);
    if (child instanceof LoginController) {
      ((LoginController) child).setDatabase(database);
    }
  }

  /** Help method for the AppTest.
   *  Sets the specific databaseInterface for the ui.
   *
   * @param database The DatabaseInterface to use
   */
  public void setDatabase(DatabaseInterface database) {
    this.database = database;
    if (child instanceof LoginController) {
      ((LoginController) child).setDatabase(database);
    }
  }

  public Object getChild() {
    return child;
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
      BrowserController browserController = ((BrowserController) subContentLoader.getController());
      browserController.setActiveUser(activeUser);
      browserController.setDatabase(database);
      browserController.setParent(this);
      browserController.updatePosts();
      child = subContentLoader.getController();
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
      LoginController loginController = ((LoginController) subContentLoader.getController());
      loginController.setDatabase(database);
      loginController.setParent(this);
      child = subContentLoader.getController();
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Error loading content browser");
    }
  }

  
}
