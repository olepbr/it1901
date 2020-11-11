package restapi;

import static spark.Spark.*;

import core.datastructures.Database;
import core.io.LocalIO;

/** Main class for rest api server */
public class Main {

  private static LocalIO io;
  protected static Database database;

  // Set up database
  static {
    io = new LocalIO();
    database = new Database(io);
  }

  // Defeat instantiation
  // protected Main() {}

  public static void main() {
    // Configure server
    port(8080);

    // Routes
    Routes.configureRoutes();
  }
}
