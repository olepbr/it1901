package fxui;

import core.datastructures.User;
import core.io.IO;
import fxui.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import core.datastructures.User;
import core.datastructures.Database;
import core.io.IO;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.ArrayList;


public class LoginController{

    /**
     * Controller class for the login- and register panes
     *
     */

    private EmailValidator emailValidator;
    private Database database;
    private AppController parent;
    private User user;
    private ArrayList<User> users = new ArrayList<>();


    //login
    @FXML private AnchorPane loginAnchorPane;
    @FXML private TextField loginEmailText;
    @FXML private PasswordField loginPasswordText;
    @FXML private Button loginButton;
    @FXML private Button registerButton;
    @FXML private Label loginEmailWarning;
    @FXML private Label loginPassWarning;

    //register
    @FXML private AnchorPane registerAnchorPane;
    @FXML private TextField nameTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordTextField;
    @FXML private Button createUserButton;
    @FXML private Label nameWarning;
    @FXML private Label usernameWarning;
    @FXML private Label emailWarning;
    @FXML private Label passwordWarning;

    public void setParent(AppController parent){
        this.parent = parent;
    }
    
    public void setDatabase(Database database) {
      this.database = database;
    }

    @FXML
    private void initialize(){
        loginAnchorPane.setVisible(true);
        registerAnchorPane.setVisible(false);
    }

    /**
     * Logs in a user when the correct email and password combo is used.
     *
     *
     * @param event
     */

    @FXML
    private void login(ActionEvent event){
        String email = loginEmailText.getText();
        String hashedPassword = user.hashPassword(loginPasswordText.getText());
        if(!EmailValidator.getInstance().isValid(email)){
            loginEmailWarning.setText("Incorrect email");
        } else if(!hashedPassword.matches(user.getPassword())){
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
        String password = passwordTextField.getText();
        if(name.isEmpty()) {
            nameWarning.setText("Please put your full name");
        } else if(!emailValidator.getInstance().isValid(email)) {
            emailWarning.setText("Please put a valid email address");
        } else if(username.isEmpty()){
            usernameWarning.setText("Username is taken or not valid");
        } else if(password.length() < 8){
            passwordWarning.setText("Password must contain at least 8 characters");
        } else{
            int id = users.size(); id++;
            User user = new User(id, name, username, email);
            user.setPassword(password);
            parent.handleLogin(user);
        }
    }

}
