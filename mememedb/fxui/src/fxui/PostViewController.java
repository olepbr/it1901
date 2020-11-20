package fxui;

import core.databases.DatabaseInterface;
import core.datastructures.Post;
import core.datastructures.User;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javax.imageio.ImageIO;

/**
 * Controller class for PostView.fxml. Controller opens a new window which views
 * a specific post and comments that are added to it.
 */

public class PostViewController {

  private Post post;
  private User activeUser;
  private DatabaseInterface database;
  @FXML
  private Text caption;
  @FXML
  private ImageView imageView;
  @FXML
  private TextField commentInput;
  @FXML
  private Button commentButton;
  @FXML
  private ListView<String> commentListView;
  @FXML
  private Text errorLabel;
  @FXML
  private Text posterLabel;

  public void setActiveUser(User activeUser) {
    this.activeUser = activeUser;
  }

  public void setDatabase(DatabaseInterface database) {
    this.database = database;
  }

  
  /** 
  * The user adds a comment via a TextField. Controller sets the comment via database. 
  * Comments are shown in a ListView 
  */
  @FXML
  public void addComment() {
    String commentText = commentInput.getText();
    if (!(commentText == null)) {
      database.newComment(commentText, activeUser.getNickname(), post.getUUID());
      fetchComments();
    } else {
      errorLabel.setText("Cannot post an empty comment");
      System.out.println("empty comment");
    }
  }

  /**
   * Fetches comments from database and adds them to a database.
   */

  public void fetchComments() {
    ObservableList<String> observableCommentList = FXCollections.observableArrayList(
        database.getComments(post.getUUID()).toString().replace("[", "").replace("]", ""));
    commentListView.setItems(observableCommentList);
    commentInput.setText(null);
  }


  /**
   * Sets and displays the post this controller should correspond to.
   *
   * @param post The post to display.
   */
  
  public void setPost(Post post) {
    this.post = post;
    try {
      byte[] imageData = Base64.getDecoder().decode(post.getImage());
      imageView.setImage(SwingFXUtils.toFXImage(
          ImageIO.read(new ByteArrayInputStream(imageData)), null));
    } catch (IOException e) {
      System.out.println("Could not decode image");
      e.printStackTrace();
    }
    caption.setText(post.getText());
    posterLabel.setText("Made by " + post.getOwner().toString());
    fetchComments();
  }
}
 
  