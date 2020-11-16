package restapi;

import static java.net.HttpURLConnection.HTTP_OK;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MainTest {

  private static String responseToString(HttpURLConnection connection) throws IOException {
    InputStream input = connection.getInputStream();
    return new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))
        .lines()
        .collect(Collectors.joining(""));
  }

  private static void stringToRequestBody(HttpURLConnection connection, String body)
      throws IOException {
    try (OutputStream os = connection.getOutputStream()) {
      byte[] input = body.getBytes("utf-8");
      os.write(input, 0, input.length);
    }
  }

  private static HttpURLConnection request(String method, URL url, String body) {
    HttpURLConnection connection = null;
    try {
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod(method);
      connection.setRequestProperty("Content-Type", "application/json; utf-8");
      connection.setRequestProperty("Accept", "application/json");
      connection.setDoOutput(true);
      connection.connect();
      if (body != null) {
        stringToRequestBody(connection, body);
      }
    } catch (IOException e) {
      e.printStackTrace();
      Assertions.fail("Connection failed");
    }
    return connection;
  }

  private static HttpURLConnection request(String method, URL url) {
    return request(method, url, null);
  }

  @BeforeAll
  public static void setup() {
    Main.setup();
  }

  @AfterAll
  public static void shutdown() {
    Main.shutdown();
  }

  @Test
  public void basicTest() throws IOException {
    URL url = new URL("http://localhost:8080/");
    HttpURLConnection connection = request("GET", url);
    int responseCode = connection.getResponseCode();
    Assertions.assertEquals(responseCode, HTTP_OK);
  }

  @Test
  public void createUserTest() throws IOException {
    URL url = new URL("http://localhost:8080/user");
    String userString =
        "{\"nickname\":\"EdgyBoi\", \"name\":\"Ola Nordmann\", \"email\":\"ola@nordmann.no\", \"hashedPassword\":\"lisdvilrhngvliuv\"}";
    HttpURLConnection connection = request("POST", url, userString);
    Assertions.assertNotNull(connection.getResponseCode());
  }

  @Test
  public void getUserTest() throws IOException {
    URL url = new URL("http://localhost:8080/user/EdgyBoi");
    HttpURLConnection connection = request("GET", url);
    int responseCode = connection.getResponseCode();
    Assertions.assertEquals(responseCode, HTTP_OK);
  }
}
