package mememedb.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class LoginController{

    @FXML
    AnchorPane loginAnchorPane;
    @FXML
    TextField loginEmailText;
    @FXML
    TextField loginPasswordText;
    @FXML
    Button loginButton;
    @FXML
    Button registerButton;

    @FXML
    private void initialize(){
   //     loginAnchorPane.setVisible(false);
    }

    @FXML
    private void login(ActionEvent e){
        System.out.println("Testing");
    }

    @FXML
    private void registerClick(ActionEvent e){
        System.out.println("Testing");
    }
    //sendToApp(){

   // }


/*


    public void loginPopup(){
        Stage stage = new Stage();

        FlowPane choiceFlowPane = new FlowPane();
        FlowPane loginFlowPane = new FlowPane();
        FlowPane registerFlowPane = new FlowPane();

        Scene loginScene = new Scene(loginFlowPane, 300, 200);
        Scene registerScene = new Scene(registerFlowPane, 300, 200);

        //Choice scene
        Button loginButton = new Button("Click here to log in");
        Button registerButton = new Button("Click here to register");
        loginButton.setOnAction(e -> stage.setScene(loginScene));
        registerButton.setOnAction(e -> stage.setScene(registerScene));
        choiceFlowPane.getChildren().addAll(loginButton, registerButton);
        choiceFlowPane.setPadding(new Insets(40));
        choiceFlowPane.setVgap(40);
        Scene popupScene = new Scene(choiceFlowPane, 300, 200);


        //Login scene
        TextField emaiTextField = new TextField();
        TextField passwordTextField = new TextField();
        Button enterButton = new Button("Click here to log in");
        //Scene loginScene = new Scene(choiceFlowPane, 300, 200);

        //Register scene

        stage.setTitle("Log in or register");
        stage.setScene(popupScene);
        stage.show();

    }

 */
}
