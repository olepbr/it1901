package restapi;

import static java.net.HttpURLConnection.HTTP_CONFLICT;
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

public class ServerTest {

  private static String baseURL = "http://localhost:8080";

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

  // Start the server before all tests.
  @BeforeAll
  public static void setup() {
    Server.setup();
  }

  // Stop the server after the tests.
  @AfterAll
  public static void shutdown() {
    Server.shutdown();
  }

  @Test
  public void basicTest() throws IOException {
    URL url = new URL(baseURL + "/");
    HttpURLConnection connection = request("GET", url);
    int responseCode = connection.getResponseCode();
    Assertions.assertEquals(responseCode, HTTP_OK);
  }

  @Test
  public void createUserTest() throws IOException {
    URL url = new URL(baseURL + "/user");
    String userString =
        "{\"nickname\":\"EdgyBoi\", \"name\":\"Ola Nordmann\", \"email\":\"ola@nordmann.no\", \"hashedPassword\":\"lisdvilrhngvliuv\"}";
    HttpURLConnection connection = request("POST", url, userString);
    int responseCode = connection.getResponseCode();
    Assertions.assertTrue(responseCode == HTTP_OK || responseCode == HTTP_CONFLICT);
  }

  @Test
  public void getUserTest() throws IOException {
    URL url = new URL(baseURL + "/user/EdgyBoi");
    HttpURLConnection connection = request("GET", url);
    int responseCode = connection.getResponseCode();
    Assertions.assertEquals(responseCode, HTTP_OK);
  }

  @Test
  public void getAllUsersTest() throws IOException {
    URL url = new URL(baseURL + "/user");
    HttpURLConnection connection = request("GET", url);
    int responseCode = connection.getResponseCode();
    Assertions.assertEquals(responseCode, HTTP_OK);
  }

  @Test
  public void createPost() throws IOException {
    URL url = new URL(baseURL + "/post");
    String body = "";
    HttpURLConnection connection = request("POST", url, body);
  }
}
