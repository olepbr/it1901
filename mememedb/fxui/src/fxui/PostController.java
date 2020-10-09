package fxui;

import core.datastructures.Database;
import core.datastructures.Post;
import java.io.File;
import java.net.MalformedURLException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PostController {

  Post post;
  Database database;
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
    File image = database.getImage(this.post.getImage());
    try {
      postImage.setImage(new Image(image.toURI().toURL().toExternalForm()));
    } catch (MalformedURLException e) {
      System.out.println("Could not find image");
      e.printStackTrace();
    }
    postText.setText(this.post.getText());
    postPoster.setText("Made by " + this.post.getOwner());
  }

  /**
   * Changes the Database this post should fetch data from.
   *
   * @param database The database to use.
   */
  public void setDatabase(Database database) {
    this.database = database;
  }
}
