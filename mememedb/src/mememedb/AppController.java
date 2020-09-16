package mememedb;

//Import javafx stuff
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.util.*;
import java.util.regex.Pattern;
//Java Utils
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class AppController
{



	private IO memeIO;
	@FXML
	private VBox content;
	@FXML
	private Button addContent;
	@FXML
	private Button browseButton;
	@FXML
	private TextField inputTextField;
	@FXML
	private BorderPane borderPane;
	@FXML
	private Label imgSelectorLabel;

	public AppController() {
	}

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

		String absPath = Paths.get("").toUri().getPath();
		System.out.println(absPath);
		if(!Pattern.matches(".*mememedb[/\\\\]*$", absPath)) {
			absPath+="mememedb/";
		}
		absPath=absPath + "src/resources/img/" + name;
		System.out.println(absPath);
		File image = new File(absPath);
		return image;
	}


	@FXML
	public void handleAddContent(){
		String caption = inputTextField.getText();
		File image = imageFileChooser();
		Post post = new Post("Edgy Grandma", caption, image.getName());
		savePost(post);
		SaveImage(image);
		updatePosts();
	}

	public File imageFileChooser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Pick a meme");

		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("All image files", "*.jpeg", "*.png", "*.jpg", "*.gif"),
				new FileChooser.ExtensionFilter("JPG files", "*.jpg"),
				new FileChooser.ExtensionFilter("PNG files", "*.png"),
				new FileChooser.ExtensionFilter("JPEG files", "*.jpeg"),
				new FileChooser.ExtensionFilter("GIF files", "*.gif")

		);
	//	Stage stage = (Stage)borderPane.getScene().getWindow();
		File file = fileChooser.showOpenDialog(null);
		System.out.println(file.getAbsolutePath());
		return file;
	}





}
