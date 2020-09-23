package mememedb.ui;

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

import mememedb.datastructures.Post;
import mememedb.datastructures.User;
import mememedb.io.LocalIO;
import mememedb.io.IO;

public class PostController {
	
	Post post;
	IO io;
	User activeUser;
	@FXML ImageView postImage;
	@FXML Label postText;
	
	public void setUser(User user) {
		activeUser = user;
	}
	
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
	
	public void setIO(IO io) {
		this.io = io;
	}
	
	
	
	
}
