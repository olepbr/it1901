package mememedb;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.Node;

import javafx.collections.ObservableList;

import mememedb.ui.AppController;

public class AppTest extends ApplicationTest {
  private Parent parent;
  private AppController controller;

  @Override
  public void start(final Stage stage) throws Exception {
    final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ui/App.fxml"));
    parent = fxmlLoader.load();
    controller = fxmlLoader.getController();
    stage.setScene(new Scene(parent));
    stage.show();
  }

  @Test
  public void testLogin() {
    final TextField loginEmail = (TextField) parent.lookup("#loginEmailText");
    final TextField loginPassword = (TextField) parent.lookup("#loginPassText");
    final Button loginButton = (Button) parent.lookup("#loginButton");
    clickOn(loginEmail);
    write("cmail@gmail.com");
    clickOn(loginPassword);
    write("passw0rd123qwerty");
    clickOn(loginButton);
  }
  
  @Test
  public void testRegister() {
    final Button registerButton = (Button) parent.lookup("#registerButton");
    final TextField regEmail = (TextField) parent.lookup("#emailTextField");
    final TextField regPassword = (TextField) parent.lookup("#passwordTextField");
    final TextField regUser = (TextField) parent.lookup("#usernameTextField");
    final TextField regName = (TextField) parent.lookup("#nameTextField");
    final Button createButton = (Button) parent.lookup("#createUserButton");
    clickOn(registerButton);
    clickOn(regName);
    write("Testman Testson");
    clickOn(regUser);
    write("kewltester123");
    clickOn(regEmail);
    write("cmail@gmail.com");
    clickOn(regPassword);
    write("passw0rd123qwerty");
    clickOn(createButton);
  }

}
