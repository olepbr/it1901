package fxui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;



public class LoginTest extends ApplicationTest {

  private LoginController controller;

    @Override
    public void start(final Stage stage) throws Exception {
      final FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Login.fxml"));
      final AnchorPane root = new AnchorPane();
      loader.setRoot(root);
      final Parent parent = loader.load();
      this.controller = loader.getController();
      stage.setScene(new Scene(parent));
      stage.show();
    }
   /*
    private LoginController controller;

    @Override
    public void start(Stage stage) throws Exception {
      System.out.println("hello!");
      final FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Login.fxml"));
      final Parent root = loader.load();
      this.controller = loader.getController();
      stage.setScene(new Scene(root));
      stage.show();
    }

        /*
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Login.fxml"));
        parent = fxmlLoader.load();
        controller = fxmlLoader.getController();
        stage.setScene(new Scene(parent));
        stage.show();*/
  

    @Test
    public void registerTest(){
      clickOn("#registerButton");
      clickOn("#nameTextField").write("John Doe");
      clickOn("#emailTextField").write("johndoe@example.com");
      clickOn("#usernameTextField").write("DoeMan");
      clickOn("#passwordTextField").write("ILoveMyMom123");
     // clickOn("#createUserButton");
  
    }
    
}