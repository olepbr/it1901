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
     * Controller class for the login- and register panes.
     *
     * @author Hanna Thevik
     */

    private Database database;
    private AppController parent;
    private User user;
    private ArrayList<User> users = new ArrayList<>();


    //login
    @FXML private AnchorPane loginAnchorPane;
    @FXML private TextField loginUsername;
    @FXML private PasswordField loginPasswordText;
    @FXML private Button loginButton;
    @FXML private Button registerButton;
    @FXML private Label loginWarning;

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


    /**
     * Initializes the login and register panes. Hides the register pane.
     */
    @FXML
    private void initialize(){
        loginAnchorPane.setVisible(true);
        registerAnchorPane.setVisible(false);
    }

    /**
     * Logs in a user when the correct email and password combo is used.
     *
     * @param event
     */
    @FXML
    private void login(ActionEvent event){
        String username = loginUsername.getText();
        String password = loginPasswordText.getText();
        if(database.tryLogin(username, password) != null){
            parent.handleLogin(database.tryLogin(username, password));
            }
        else {
            loginWarning.setText("Password and email do not match!");
        }
    }

    /**
     * Switches panes if the registerButton is clicked.
     * @param e
     */
    @FXML
    private void registerClick(ActionEvent e){
        loginAnchorPane.setVisible(false);
        registerAnchorPane.setVisible(true);
    }


    /**
     * Checks if all the strings in TextFields are valid.
     * @param e
     *
     * Creates a new User object and saves it in database if all the fields are valid.
     */
    @FXML
    private void createUser(ActionEvent e){
        String email = emailTextField.getText();
        String name = nameTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        if(name.isEmpty()) {
            nameWarning.setText("Please put your full name");
        } else if(!EmailValidator.getInstance().isValid(email)) {
            emailWarning.setText("Please put a valid email address");
        } else if(database.usernameExists(username)){
            usernameWarning.setText("Username is taken or not valid");
        } else if(password.length() < 8){
            passwordWarning.setText("Password must contain at least 8 characters");
        } else{
            User user = new User(database.getNewID(), name, username, email);
            user.setPassword(password);
            database.saveUser(user);
            parent.handleLogin(user);
        }
    }

}
