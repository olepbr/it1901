import fxui.App;
import javafx.application.Application;

public class Runner {
  public static void main(String args[]) {
    if(args.length == 0) {
      Application.launch(App.class);
    }
    else if(args[0] == "rest") {
      //TODO: start server
      Application.launch(App.class, args);
    }
    else {
      Application.launch(App.class, args);
    }

  }
}