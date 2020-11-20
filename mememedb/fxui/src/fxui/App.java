package fxui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import restapi.Server;

public class App extends Application {

  @Override
  public void start(final Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("App.fxml"));
    Parent root = loader.load();
    AppController appController = loader.getController();
    String arg = null;
    if (this.getParameters().getRaw().size() > 0) {
      arg = this.getParameters().getRaw().get(0);
    }
    appController.setDatabase(arg);
    primaryStage.setScene(new Scene(root));
    primaryStage.setTitle("mememedb");
    primaryStage.show();
  }

  @Override
  public void stop() {
    /*Shuts down the REST-server, if it is running*/
    Server.shutdownServer();
  }

  public static void main(final String[] args) {
    launch(args);
  }
}
