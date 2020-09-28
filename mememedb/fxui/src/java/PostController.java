package mememedb.ui;

import java.io.File;
import java.net.MalformedURLException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mememedb.datastructures.Post;
import mememedb.datastructures.User;
import mememedb.io.IO;

public class PostController {

  Post post;
  IO io;
  User activeUser;
  @FXML ImageView postImage;
  @FXML Label postText;
    
  /**
   * Sets current active User.
   * 
   * @param user The new User.
   */
  public void setUser(User user) {
    activeUser = user;
  }
  
  
  /**
   * Sets and displays the post this controller should correspond to.
   * 
   * @param post The post to display.
   */
  public void setPost(Post post) {
    this.post = post;
    File image = io.getImageFromName(post.getImage());
    try {
      postImage.setImage(new Image(image.toURI().toURL().toExternalForm()));
    } catch (MalformedURLException e) {
      System.out.println("Could not find image");
      e.printStackTrace();
    }
    postText.setText(post.getText());
  }

  
  /**
   * Changes the IO this post should use for reading data.
   * 
   * @param io The IO to use.
   */
  public void setIO(IO io) {
    this.io = io;
  }




}
