package fxui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.File;


public class AppTest extends ApplicationTest {
    private Parent parent;
    private AppController controller;

    @Mock
    FileChooser fileChooser = Mockito.mock(FileChooser.class);

    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ui/App.fxml"));
        parent = fxmlLoader.load();
        controller = fxmlLoader.getController();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Test
    public void testButtons() {
        final Button logoutButton = (Button) parent.lookup("#logout");
        final Button addContent = (Button) parent.lookup("#addContent");
        final Button registerClick = (Button) parent.lookup("#registerClick");
        final Button browseButton = (Button) parent.lookup("browsebutton");
        clickOn(addContent);
        clickOn(logoutButton);
        clickOn(registerClick);
    }


    @Test
    public void testTextFields(){
        //Browser
        final TextField inputField = (TextField) parent.lookup("inputTextField");
        //Register
        final TextField nameTextField = (TextField) parent.lookup("nameTextField");
        final TextField emailTextField = (TextField) parent.lookup("emailTextField");
        final TextField usernameTextField = (TextField) parent.lookup("usernameTextField");
        final PasswordField passwordTextField = (PasswordField) parent.lookup("passwordTextField");
        //Login
        final TextField loginUsername = (TextField) parent.lookup("loginUsername");
        final PasswordField loginPasswordText = (PasswordField) parent.lookup("loginPassWordText");
        clickOn(inputField);
        write("This is a caption");


    }





    @Test
    public void testAddContent(){
        final TextField inputField = (TextField) parent.lookup("inputTextField");
        final Button browseButton = (Button) parent.lookup("browsebutton");

        clickOn(browseButton);
        File inputFile = new File(getClass().getClassLoader().getResource("pangolin.png").getPath());

    }


    @Test
    public void testSwitchLoginScenes(){
        final Button registerClick = (Button) parent.lookup("#registerClick");
        clickOn(registerClick);

    }



}
