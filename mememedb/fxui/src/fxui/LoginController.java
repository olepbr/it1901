package fxui;

import core.datastructures.DatabaseInterface;
import core.datastructures.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * Controller class for the login- and register panes.
 *
 * @author Hanna Thevik
 */
public class LoginController {

  private DatabaseInterface database;

  private AppController parent;

  // login
  @FXML private AnchorPane loginAnchorPane;
  @FXML private TextField loginUsername;
  @FXML private PasswordField loginPasswordText;
  @FXML private Button loginButton;
  @FXML private Button registerButton;
  @FXML private Label loginWarning;

  // register
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

  public void setParent(AppController parent) {
    this.parent = parent;
  }

  public void setDatabase(DatabaseInterface database) {
    this.database = database;
  }

  /** Initializes the login and register panes. Hides the register pane. */
  @FXML
  private void initialize() {
    loginAnchorPane.setVisible(true);
    registerAnchorPane.setVisible(false);
  }

  /** Logs in a user when the correct email and password combo is used. */
  @FXML
  private void login() {
    String username = loginUsername.getText();
    String password = loginPasswordText.getText();
    if (database.tryLogin(username, password) != null) {
      parent.handleLogin(database.tryLogin(username, password));
    } else {
      loginWarning.setText("Password and email do not match!");
    }
  }

  /** Switches panes if the registerButton is clicked. */
  @FXML
  private void registerClick() {
    loginAnchorPane.setVisible(false);
    registerAnchorPane.setVisible(true);
  }

  /**
   * Checks if all the strings in TextFields are valid. Creates a new User object and saves it in
   * database if all the fields are valid.
   */
  @FXML
  private void createUser() {
    String email = emailTextField.getText();
    String name = nameTextField.getText();
    String username = usernameTextField.getText();
    String password = passwordTextField.getText();
    if (name.isEmpty()) {
      nameWarning.setText("Please put your full name");
    } else if (!EmailValidator.getInstance().isValid(email)) {
      emailWarning.setText("Please put a valid email address");
    } else if (database.usernameExists(username)) {
      usernameWarning.setText("Username is taken or not valid");
    } else if (password.length() < 8) {
      passwordWarning.setText("Password must contain at least 8 characters");
    } else {
      database.newUser(name, username, email, password);
      User user = database.tryLogin(username, password);
      parent.handleLogin(user);
    }
  }
}
