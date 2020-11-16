package restapi;

import static spark.Spark.awaitInitialization;
import static spark.Spark.port;
import static spark.Spark.stop;

import core.datastructures.LocalDatabase;
import core.io.LocalIO;

/** Main class for rest api server. */
public class Main {

  private static LocalIO io;
  protected static final LocalDatabase database;

  // Set up database
  static {
    io = new LocalIO();
    database = new LocalDatabase(io);
  }

  // Defeat instantiation
  protected Main() {}

  protected static void setup() {
    // Configure server
    port(8080);

    // Routes
    Routes.configureRoutes();

    // Wait for the server to be ready
    awaitInitialization();
  }

  protected static void shutdown() {
    stop();
  }

  /** [TODO:description] */
  public static void main(String[] args) {
    setup();
  }
}
