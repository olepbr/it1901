import fxui.App;
import javafx.application.Application;

public class Runner {
  public static void main(String args[]) {
    if(args.length == 0) {
      Application.launch(App.class);
    }
    else {
      System.out.println("running" + args[0]);
    }

  }
}