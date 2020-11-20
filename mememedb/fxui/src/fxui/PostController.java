package fxui;

import core.datastructures.DatabaseInterface;
import core.datastructures.Post;
import core.datastructures.User;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class PostController {

  @FXML private ImageView postImage;
  @FXML private Label postText;
  @FXML private Label postPoster;

  private Post post;
  private User activeUser;
  private DatabaseInterface database;

  /**
   * Sets and displays the post this controller should correspond to.
   *
   * @param post The post to display.
   */
  public void setPost(Post post) {
    try {
      byte[] imageData = Base64.getDecoder().decode(post.getImage());
      postImage.setImage(SwingFXUtils.toFXImage(
          ImageIO.read(new ByteArrayInputStream(imageData)), null));
    } catch (IOException e) {
      System.out.println("Could not decode image");
      e.printStackTrace();
    }
    postText.setText(post.getText());
    postPoster.setText("Made by " + post.getOwner());
    this.post = post;
  }

  public void setActiveUser(User activeUser) {
    this.activeUser = activeUser;
  }

  public void setDatabase(DatabaseInterface database) {
    this.database = database;
  }

  /**
   * Displays a new window when image is clicked. Opens PostView.fxml window.
   */
  @FXML
  public void handleEnterPostView(MouseEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader();
      fxmlLoader.setLocation(getClass().getClassLoader().getResource("PostView.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      Stage stage = new Stage();
      stage.setTitle("View post");
      stage.setScene(scene);
      PostViewController postViewController = ((PostViewController) fxmlLoader.getController());
      postViewController.setDatabase(database);
      postViewController.setPost(post);
      postViewController.setActiveUser(activeUser);
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.show();
      
    } catch (IOException e) {
      System.out.println("Could not load new window");
      e.printStackTrace();
    }
  }

}