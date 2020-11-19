import fxui.App;
import javafx.application.Application;
import restapi.Server;

/**
 * Runner class for the project, allows for single-command execution of 
 * both localDatabase and RestDatabase versions of the app.
 * 
 * @author Jostein bakkevig
 */
public class Runner {
  /**
   * Launches the javafx-app, using the given args to determine which
   * modules to use.
   *
   * @param args Accepts up to one extra argument. If argument is "rest",
   *             launches REST-server. Passes argument to a DatabaseFactory in order to
   *             determine which implementation to use for persistence.
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      Application.launch(App.class);
    } else if (args[0].equals("rest")) {
      System.out.println("hello, running rest");
      Server.main(args);
      Application.launch(App.class, args);
    } else {
      System.out.println("hello, not running rest");
      Application.launch(App.class, args);
    }

  }
}