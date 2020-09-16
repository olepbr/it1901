package mememedb;

//Import javafx stuff
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
//Java Utils
import java.util.HashMap;

import java.util.regex.Pattern;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

public class AppController
{	
	
	
	
	
	@FXML
	VBox content;
	Button addContent;



	//Initialize
	@FXML
	public void initialize(){
		updatePosts();
	}
	
	public void updatePosts() {
		//remove old posts
		content.getChildren().clear();
		//get collection of posts from I/O
		Collection<Post> postList = new ArrayList<Post>();
		postList.add(new Post("xXx_Gertrude_xXx", "my beautiful face", "Grandma.png"));
		for (Post post : postList) {
			HBox subContent = new HBox();
			FXMLLoader subContentLoader = new FXMLLoader(getClass().getResource("Post.fxml"));
			subContentLoader.setRoot(subContent);
			subContentLoader.setController(getClass().getResource("PostController.java"));
			content.getChildren().add(subContent);
			try {
				subContentLoader.load();
				//load post content
				((PostController) subContentLoader.getController()).setPost(post);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
	
	public static File getImageFromName(String name) {
		//To be moved into I/O
		//gets image given name, assumes images are stored in img under resources.
		System.out.println(Paths.get("").toUri().getPath());
		String absPath = Paths.get("").toUri().getPath() + "/src/resources/img/Grandma.png";
		File image = new File(absPath);
		return image;
		
	}
	
	
	@FXML
	public void handleAddContent(){	
		HBox subContent = new HBox();
		FXMLLoader subContentLoader = new FXMLLoader(getClass().getResource("Post.fxml"));
		subContentLoader.setRoot(subContent);
		subContentLoader.setController(getClass().getResource("PostController.java"));
		content.getChildren().add(subContent);
		try {
			subContentLoader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}


