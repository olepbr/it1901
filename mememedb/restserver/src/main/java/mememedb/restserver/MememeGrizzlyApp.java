package mememedb.restserver;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class MememeGrizzlyApp {

  private static URI BASE_URI = URI.create("http://localhost:8080/");

  /**
   * Starts the server with commmand line arguments and timeout.
   *
   * @param args The command line arguments.
   * @param waitSecondsForServer The timeout limit.
   * @return The instance of the server.
   * @throws IOException When the server cannot be started.
   */
  public static HttpServer startServer(final String[] args, int waitSecondsForServer)
      throws IOException {
    URI baseUri = (args.length >= 1 ? URI.create(args[0]) : BASE_URI);
    ResourceConfig resourceConfig = 
        (args.length >= 2 ? new MememeConfig(args[1]) : new MememeConfig());
    HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(baseUri, resourceConfig);
    if (waitSecondsForServer < 0) {
      return httpServer;
    }
    while (waitSecondsForServer > 0) {
      try {
        URL clientUrl = new URL(baseUri + MememeService.MEMEME_SERVICE_PATH);
        HttpURLConnection connection = (HttpURLConnection) clientUrl.openConnection();
        int responseCode = connection.getResponseCode();
        System.out.println("Trying " + clientUrl + ": " + responseCode);
        connection.disconnect();
        if (responseCode == 200) {
          return httpServer;
        }
      } catch (final RuntimeException e) {
        // ignore
      }
      try {
        Thread.sleep(1000);
        waitSecondsForServer -= 1;
      } catch (final InterruptedException e) {
        return null;
      }
    }
    return null;
  }

  /**
   * Stops the server.
   *
   * @param server The server that should be stopped.
   * @throws IOException When the server cannot be shut down.
   */
  public static void stopServer(final HttpServer server) throws IOException {
    server.shutdown();
  }

  /**
   * Starts the server.
   * The command line arguemnts are as follows:
   * - the base URL of the server
   * - the initial Database in JSON format
   *
   * @param args command line arguments
   * @throws IOException when server startup fails
   */
  public static void main(final String[] args) throws IOException {
    try {
      final HttpServer server = startServer(args, -1);
      Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));
      Thread.currentThread().join();
    } catch (final InterruptedException ex) {
      Logger.getLogger(MememeGrizzlyApp.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
