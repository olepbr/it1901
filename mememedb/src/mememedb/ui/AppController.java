package mememedb.ui;

//Import javafx stuff
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

//Java utils:
import java.util.List;

//File utilities
import java.io.File;
import java.io.IOException;

//Mememe
import mememedb.io.IO;
import mememedb.io.LocalIO;

import mememedb.datastructures.Post;
import mememedb.datastructures.User;

public class AppController {

	// Storage interface
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

	@FXML
	public void initialize() {
		memeIO = new LocalIO();
		updatePosts();
	}

	public void updatePosts() {
		// remove old posts and reset post selector
		content.getChildren().clear();
		inputTextField.setText(null);
		imgSelectorLabel.setText("Choose an image");
		// get collection of posts from I/O
		List<Post> postList = memeIO.getPostList();
		// create nodes for each post
		for (Post post : postList) {
			HBox subContent = new HBox();
			FXMLLoader subContentLoader = new FXMLLoader(getClass().getResource("Post.fxml"));
			subContentLoader.setRoot(subContent);
			subContentLoader.setController(getClass().getResource("PostController.java"));
			content.getChildren().add(subContent);
			try {
				subContentLoader.load();
				((PostController) subContentLoader.getController()).setIO(memeIO);
				((PostController) subContentLoader.getController()).setPost(post);
			} catch (IOException e) {
				System.out.println("Error loading post");
			}
		}
	}

	@FXML
	public void handleAddContent() {
		String caption = inputTextField.getText();
		File image = selectedImage;

		if (caption == null) {
			Alert a = new Alert(Alert.AlertType.ERROR);
			a.setContentText("Please add a caption");
			a.show();
		} else if (image == null) {
			Alert a = new Alert(Alert.AlertType.ERROR);
			a.setContentText("Please add an image");
			a.show();
		} else {
			Post post = new Post("xXx_gertrude_xXx", caption, image.getName());
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
		File file = fileChooser.showOpenDialog(null);
		if (file != null) {
			selectedImage = file;
			imgSelectorLabel.setText(selectedImage.getName());
		} else {
			Alert a = new Alert(Alert.AlertType.ERROR);
			a.setContentText("Must choose an image!");
			a.show();
		}

	}

}
