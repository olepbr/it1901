package mememedb.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.validator.routines.EmailValidator;

public class LoginController{

    EmailValidator emailValidator;

    //login
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

    //register
    @FXML
    AnchorPane registerAnchorPane;
    @FXML
    TextField nameTextField;
    @FXML
    TextField emailTextField;
    @FXML
    TextField usernameTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    Button createUserButton;
    @FXML
    Label nameWarning;
    @FXML
    Label usernameWarning;
    @FXML
    Label emailWarning;
    @FXML
    Label passwordWarning;






    @FXML
    private void initialize(){
        loginAnchorPane.setVisible(true);
        registerAnchorPane.setVisible(false);
    }

    @FXML
    private void login(ActionEvent e){
        System.out.println("You just clicked a button that doesn't work");
    }

    @FXML
    private void registerClick(ActionEvent e){
        loginAnchorPane.setVisible(false);
        registerAnchorPane.setVisible(true);
    }

    public boolean validateUsername(String username){
        return true;
    }

    @FXML
    private void createUser(ActionEvent e){
        String email = emailTextField.getText();
        String name = nameTextField.getText();
        String username = usernameTextField.getText();
        if(name.isEmpty()) {
            nameWarning.setText("Please put your full name");
        } else if(!emailValidator.getInstance().isValid(email)) {
            emailWarning.setText("Please put a valid email address");
        } else if(username.isEmpty()){
            usernameWarning.setText("Username is taken or not valid");
        } else if(passwordTextField.getText().isEmpty()){
            passwordWarning.setText("Password must contain at least x characters");
        } else{
            System.out.println("Creating user... haha jk");
        }


    }



    //sendToApp(){
   // }

}
