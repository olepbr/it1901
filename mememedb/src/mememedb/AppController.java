package mememedb;

//Import javafx stuff
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
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
	private File selectedImage;
	
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
		memeIO = new LocalIO();
		updatePosts();
	}

	public void updatePosts() {
		//remove old posts
		content.getChildren().clear();
		inputTextField.setText(null);
		imgSelectorLabel.setText("Choose an image");
		//get collection of posts from I/O
		List<Post> postList =memeIO.getPostList();
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
		File image = selectedImage;
		
		if(caption == null) {
			Alert a = new Alert(Alert.AlertType.ERROR);
			a.setContentText("Please add a caption");
			a.show();
		} else if(image == null){
			Alert a = new Alert(Alert.AlertType.ERROR);
			a.setContentText("Please add an image");     
			a.show();
		}
		else{
			Post post = new Post("Edgy Grandma", caption, image.getName());
			try {
				memeIO.savePost(post);
				memeIO.saveImage(image);
			} catch (IOException e) {
				System.out.println("could not save post");
				e.printStackTrace();
			}
			updatePosts();                                                     
		}
	}

	public void imageFileChooser() {
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
		if(file != null){
			System.out.println(file.getAbsolutePath());
			selectedImage = file;
			imgSelectorLabel.setText(selectedImage.getName());
		}else{
			Alert a = new Alert(Alert.AlertType.ERROR);
			a.setContentText("Must choose an image!");
			a.show();
		}

	}





}
