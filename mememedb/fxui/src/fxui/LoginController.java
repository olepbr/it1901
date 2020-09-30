package mememedb.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import mememedb.datastructures.User;
import mememedb.io.IO;
import org.apache.commons.validator.routines.EmailValidator;

public class LoginController{

    private EmailValidator emailValidator;
    private IO memeIO;
    private AppController parent;


    //login
    @FXML
    private AnchorPane loginAnchorPane;
    @FXML
    private TextField loginEmailText;
    @FXML
    private PasswordField loginPasswordText;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Label loginEmailWarning;
    @FXML
    private Label loginPassWarning;

    //register
    @FXML
    private AnchorPane registerAnchorPane;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button createUserButton;
    @FXML
    private Label nameWarning;
    @FXML
    private Label usernameWarning;
    @FXML
    private Label emailWarning;
    @FXML
    private Label passwordWarning;

    public void setParent(AppController parent){
        this.parent = parent;
    }


    @FXML
    private void initialize(){
        loginAnchorPane.setVisible(true);
        registerAnchorPane.setVisible(false);
    }

    @FXML
    private void login(ActionEvent e){

        String email = loginEmailText.getText();
        if(!EmailValidator.getInstance().isValid(email)){
            loginEmailWarning.setText("Incorrect email");
        } else if(loginPasswordText.getText().isEmpty()){
            loginPassWarning.setText("Incorrect password");
        } else{
            User u1 = new User(1, "Hest Stein", "Heststein420", "heststein@ntnu.no");
            parent.handleLogin(u1);
        }
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
            System.out.println("hello");
        }


    }



    //sendToApp(){
   // }

}
