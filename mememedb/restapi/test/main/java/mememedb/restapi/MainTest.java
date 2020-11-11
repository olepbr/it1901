package restapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MainTest {

  private static String responseToString(InputStream input) {
    return new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))
        .lines()
        .collect(Collectors.joining(""));
  }

  private static String request(String method, URL url, String body) {
    String responseBody = null;
    try {
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod(method);
      if (body != null) {
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
      }
      connection.setRequestProperty("Accept", "application/json");
      connection.setDoOutput(true);
      connection.connect();
      if (body != null) {
        try (OutputStream os = connection.getOutputStream()) {
          byte[] input = body.getBytes("utf-8");
          os.write(input, 0, input.length);
        }
      }
      System.out.println(connection.getResponseCode());
      responseBody = responseToString(connection.getInputStream());
    } catch (IOException e) {
      e.printStackTrace();
      Assertions.fail("IO Fail");
    }
    return responseBody;
  }

  private static String request(String method, URL url) {
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
  public void basicTest() throws MalformedURLException {
    URL url = new URL("http://localhost:8080/");
    Assertions.assertNotNull(request("GET", url));
  }

  @Test
  public void createUserTest() throws MalformedURLException {
    URL url = new URL("http://localhost:8080/user");
    String userString =
        "{\"nickname\":\"EdgyBoi\", \"name\":\"Ola Nordmann\", \"email\":\"ola@nordmann.no\", \"hashedPassword\":\"lisdvilrhngvliuv\"}";
    String response = request("POST", url, userString);
    System.out.println(response);
    Assertions.assertNotNull(response);
  }

  @Test
  public void getUserTest() throws MalformedURLException {
    URL url = new URL("http://localhost:8080/user/EdgyBoi");
    String response = request("GET", url);
    System.out.println(response);
    Assertions.assertNotNull(response);
  }

  @Test
  public void deleteUserTest() throws MalformedURLException {
    URL url = new URL("http://localhost:8080/user/EdgyBoi");
    String response = request("DELETE", url);
    System.out.println(response);
    Assertions.assertNotNull(response);
  }
}
