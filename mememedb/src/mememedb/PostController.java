package mememedb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Paths;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PostController {
	
	Post post;
	
	
	@FXML Button testButton;
	@FXML ImageView postImage;
	@FXML Label postText;
	
	
	
	@FXML
	public void handleTestClicked(){
		System.out.println("Post made by " + post.getOwner() + ". DO NOT STEAL");
	}
	
	public void setPost(Post post) {
		this.post = post;
		File image = AppController.getImageFromName(post.getImage());
		try {
			postImage.setImage(new Image(image.toURI().toURL().toExternalForm()));
		} catch (MalformedURLException e) {
			System.out.println("Could not find image");
			e.printStackTrace();
		}
		postText.setText(post.getText());
	}
	
	
	
	
}
