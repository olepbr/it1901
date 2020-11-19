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
      //launch app without persistence
      Application.launch(App.class);
    } else if (args[0].equalsIgnoreCase("rest")) {
      //launch app with REST-API
      Server.main(args);
      Application.launch(App.class, args);
    } else {
      //lanch app with local persistence
      Application.launch(App.class, args);
    }

  }
}