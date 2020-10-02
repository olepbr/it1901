package it1901.mememedb.fxui;

import java.io.File;
import java.net.MalformedURLException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import it1901.mememedb.core.datastructures.Post;
import it1901.mememedb.core.datastructures.User;
import it1901.mememedb.core.datastructures.Database;

public class PostController {

  Post post;
  Database database;
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
    File image = database.getImage(post.getImage());
    try {
      postImage.setImage(new Image(image.toURI().toURL().toExternalForm()));
    } catch (MalformedURLException e) {
      System.out.println("Could not find image");
      e.printStackTrace();
    }
    postText.setText(post.getText());
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
