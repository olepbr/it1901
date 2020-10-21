package fxui;

import core.datastructures.Post;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;

public class PostController {

  Post post;
  @FXML ImageView postImage;
  @FXML Label postText;
  @FXML Label postPoster;

  /**
   * Sets and displays the post this controller should correspond to.
   *
   * @param post The post to display.
   */
  public void setPost(Post post) {
    this.post = post;
    try {
      byte[] imageData = Base64.getDecoder().decode(post.getImage());
      postImage.setImage(SwingFXUtils.toFXImage(
          ImageIO.read(new ByteArrayInputStream(imageData)), null));
    } catch (IOException e) {
      System.out.println("Could not decode image");
      e.printStackTrace();
    }
    postText.setText(this.post.getText());
    postPoster.setText("Made by " + this.post.getOwner());
  }
}
