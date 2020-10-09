package fxui;

import static org.mockito.Mockito.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class AppTest extends ApplicationTest {

  private Parent parent;
  private AppController controller;
  private LoginController loginController;
  private BrowserController browserController;

  @Override
  public void start(final Stage stage) throws Exception {
    final FXMLLoader fxmlLoader =
        new FXMLLoader(getClass().getClassLoader().getResource("App.fxml"));
    parent = fxmlLoader.load();
    controller = fxmlLoader.getController();
    stage.setScene(new Scene(parent));
    stage.show();
  }

  public void register() {
    final Button registerButton = (Button) parent.lookup("#registerButton");
    clickOn(registerButton);

    final TextField nameTextField = (TextField) parent.lookup("#nameTextField");
    final TextField emailTextField = (TextField) parent.lookup("#emailTextField");
    final TextField usernameTextField = (TextField) parent.lookup("#usernameTextField");
    final PasswordField passwordTextField = (PasswordField) parent.lookup("#passwordTextField");

    final Button createUserButton = (Button) parent.lookup("#createUserButton");

    clickOn(nameTextField);
    write("John Doe");
    clickOn(emailTextField);
    write("johndoe@example.com");
    clickOn(usernameTextField);
    write("DoeMan69");
    clickOn(passwordTextField);
    write("iLoveMyMom123");

    clickOn(createUserButton);
  }

  @Test
  public void registerTest() {
    register();
  }
}
