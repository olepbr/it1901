package mememedb;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.Node;

import javafx.collections.ObservableList;

public class AppTest extends ApplicationTest {
  private Parent parent;
  private AppController controller;

  @Override
  public void start(final Stage stage) throws Exception {
    final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("App.fxml"));
    parent = fxmlLoader.load();
    controller = fxmlLoader.getController();
    stage.setScene(new Scene(parent));
    stage.show();
  }

  @Test
  public void testButtons() {
    final Button logoutButton = (Button) parent.lookup("#logout");
    final Button postButton = (Button) parent.lookup("#postButton");
    final Button searchButton = (Button) parent.lookup("#searchButton");
    clickOn(logoutButton);
    clickOn(postButton);
    clickOn(searchButton);
  }

  @Test
  public void testContentButtons() {
    final Button postButton = (Button) parent.lookup("#postButton");
    final VBox content = (VBox) parent.lookup("#content");
    ObservableList<Node> children = content.getChildren();

    clickOn(postButton);

    for(int i = 0; i<children.size(); i++){
      Node nextChild = children.get(i);
      if(nextChild instanceof HBox){
        HBox nextHBox = (HBox) nextChild;
        ObservableList<Node> subChildren = nextHBox.getChildren();
        for(int j = 0; j<subChildren.size(); j++){
          Node nextSubChild = subChildren.get(j);
          if(nextSubChild instanceof Button){
            clickOn(nextSubChild);
          }
        }
      }
    }
  }
}
