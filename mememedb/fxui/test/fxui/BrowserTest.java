package fxui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.File;


public class BrowserTest extends ApplicationTest {

  private BrowserController controller;

     @Override
    public void start(final Stage stage) throws Exception {
      final FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Browser.fxml"));
      final AnchorPane root = new AnchorPane();
      loader.setRoot(root);
      final Parent parent = loader.load();
      this.controller = loader.getController();
      stage.setScene(new Scene(parent));
      stage.show();
    }

    @Test
    public void testAddFile(){
      File inputFile = new File(getClass().getClassLoader().getResource("pangolin.jpg").getPath());
      controller.setSelectedImage(inputFile);
      clickOn("#inputTextField");
      write("This is a very nice animal");
      //clickOn("#addContent");

      /*
        final Button browseButton = (Button) parent.lookup("#browseButton");
      
        final TextField inputTextField = (TextField) parent.lookup("#inputTextField");
        final Button addContent = (Button) parent.lookup("#addContent");

        File inputFile = new File(getClass().getClassLoader().getResource("pangolin.jpg").getPath());
        controller.setSelectedImage(inputFile);

        
        */
          
    }
    
}