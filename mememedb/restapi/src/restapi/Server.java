package restapi;

import static spark.Spark.awaitInitialization;
import static spark.Spark.port;
import static spark.Spark.stop;

import core.datastructures.LocalDatabase;
import core.io.LocalIO;

/** Main class for rest api server. */
public class Server {

  private static int defaultPort = 8080;

  protected static LocalDatabase database;

  // Defeat instantiation
  protected Server() {}

  // This method exists to inject a fake database in testing
  public static void setDatabase(LocalDatabase newDatabase) {
    database = newDatabase;
  }

  // Set up database
  public static void setupDatabase() {
    LocalIO io = new LocalIO();
    setDatabase(new LocalDatabase(io));
  }

  public static void setupServer(int port) {
    // Configure server
    port(port);

    // Routes
    Routes.configureRoutes();

    // Wait for the server to be ready
    awaitInitialization();
  }

  public static void shutdownServer() {
    stop();
  }

  public static void main(String[] args) {
    setupDatabase();
    setupServer(defaultPort);
  }
}
