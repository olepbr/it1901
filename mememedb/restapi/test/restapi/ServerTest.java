package restapi;

import static java.net.HttpURLConnection.HTTP_CONFLICT; // 409
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR; // 500
import static java.net.HttpURLConnection.HTTP_NOT_FOUND; // 404
import static java.net.HttpURLConnection.HTTP_OK; // 200
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.datastructures.Comment;
import core.datastructures.LocalDatabase;
import core.datastructures.Post;
import core.datastructures.User;
import core.json.MememeModule;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ServerTest {

  private static ObjectMapper mapper = new ObjectMapper();
  private static String baseURL = "http://localhost:8080";

  // Test data
  private static User testUser =
      new User("Ola Nordmann", "EdgyBoi", "ola@nordmann.no", "strongpassword123");
  private static List<User> testUsers = new ArrayList<User>();
  private static Post testPost = new Post("EdgyBoi", "This picture is funny", "ASDF");
  private static List<Post> testPosts = new ArrayList<Post>();
  private static Comment testComment = new Comment("EdgyBoi", "Yes, this picture is very funny");
  private static List<Comment> testComments = Arrays.asList(testComment);

  // Some methods to help with HTTP requests
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
    Server.setupServer();
    mapper.registerModule(new MememeModule());
    testUsers.add(testUser);
    testPosts.add(testPost);
  }

  // Stop the server after the tests.
  @AfterAll
  public static void shutdown() {
    Server.shutdownServer();
  }

  /* Test server root */
  // Test if the server responds
  @Test
  public void serverRunsTest() throws IOException {
    LocalDatabase fakeDatabase = mock(LocalDatabase.class);
    Server.setDatabase(fakeDatabase);

    URL url = new URL(baseURL + "/");
    HttpURLConnection connection = request("GET", url);
    int responseCode = connection.getResponseCode();
    Assertions.assertEquals(responseCode, HTTP_OK);
  }

  /* Test User */
  // Test if the server accepts a new user
  @Test
  public void createUserTest() throws IOException {
    LocalDatabase fakeDatabase = mock(LocalDatabase.class);
    when(fakeDatabase.usernameExists("EdgyBoi")).thenReturn(false);
    Server.setDatabase(fakeDatabase);

    URL url = new URL(baseURL + "/user");
    String userString =
        "{\"nickname\":\"EdgyBoi\", \"name\":\"Ola Nordmann\", \"email\":\"ola@nordmann.no\", \"hashedPassword\":\"lisdvilrhngvliuv\"}";
    HttpURLConnection connection = request("POST", url, userString);
    int responseCode = connection.getResponseCode();
    Assertions.assertTrue(responseCode == HTTP_OK);
  }

  // Test if the server responds correctly to a username conflict
  @Test
  public void createConflictUserTest() throws IOException {
    LocalDatabase fakeDatabase = mock(LocalDatabase.class);
    when(fakeDatabase.usernameExists("EdgyBoi")).thenReturn(true);
    Server.setDatabase(fakeDatabase);

    URL url = new URL(baseURL + "/user");
    String userString =
        "{\"nickname\":\"EdgyBoi\", \"name\":\"Ola Nordmann\", \"email\":\"ola@nordmann.no\", \"hashedPassword\":\"lisdvilrhngvliuv\"}";
    HttpURLConnection connection = request("POST", url, userString);
    int responseCode = connection.getResponseCode();
    Assertions.assertTrue(responseCode == HTTP_CONFLICT);
  }

  // Test if the server rejects garbage input
  @Test
  public void createBadUserTest() throws IOException {
    LocalDatabase fakeDatabase = mock(LocalDatabase.class);
    Server.setDatabase(fakeDatabase);

    URL url = new URL(baseURL + "/user");
    String userString = "{ bad json";
    HttpURLConnection connection = request("POST", url, userString);
    int responseCode = connection.getResponseCode();
    Assertions.assertTrue(responseCode == HTTP_INTERNAL_ERROR);
  }

  // Test if the server can retreive a user
  @Test
  public void getUserTest() throws IOException {
    LocalDatabase fakeDatabase = mock(LocalDatabase.class);
    when(fakeDatabase.getUser(testUser.getNickname())).thenReturn(testUser);
    Server.setDatabase(fakeDatabase);

    URL url = new URL(baseURL + "/user/" + testUser.getNickname());
    HttpURLConnection connection = request("GET", url);
    int responseCode = connection.getResponseCode();
    Assertions.assertEquals(responseCode, HTTP_OK);
    Assertions.assertEquals(
        mapper.readValue(responseToString(connection), User.class).toString(), testUser.toString());
  }

  // Test if the server responds correctly to request for non-existing user
  @Test
  public void getNonExistingUserTest() throws IOException {
    LocalDatabase fakeDatabase = mock(LocalDatabase.class);
    Server.setDatabase(fakeDatabase);

    URL url = new URL(baseURL + "/user/" + testUser.getNickname());
    HttpURLConnection connection = request("GET", url);
    int responseCode = connection.getResponseCode();
    Assertions.assertEquals(responseCode, HTTP_NOT_FOUND);
  }

  // Test if the server can retreive all users
  @Test
  public void getAllUsersTest() throws IOException {
    LocalDatabase fakeDatabase = mock(LocalDatabase.class);
    when(fakeDatabase.getUsers()).thenReturn(testUsers);
    Server.setDatabase(fakeDatabase);

    URL url = new URL(baseURL + "/user");
    HttpURLConnection connection = request("GET", url);
    int responseCode = connection.getResponseCode();
    Assertions.assertEquals(responseCode, HTTP_OK);
    List<User> users =
        mapper.readValue(responseToString(connection), new TypeReference<ArrayList<User>>() {});
    Assertions.assertEquals(users.toString(), testUsers.toString());
  }

  /* Test Post */
  /* GET */
  // Test if the server can retreive all posts
  @Test
  public void getAllPostTest() throws IOException {
    LocalDatabase fakeDatabase = mock(LocalDatabase.class);
    when(fakeDatabase.getPosts()).thenReturn(testPosts);
    Server.setDatabase(fakeDatabase);

    URL url = new URL(baseURL + "/post");
    HttpURLConnection connection = request("GET", url);
    int responseCode = connection.getResponseCode();
    Assertions.assertEquals(responseCode, HTTP_OK);
    List<Post> posts =
        mapper.readValue(responseToString(connection), new TypeReference<ArrayList<Post>>() {});
    Assertions.assertEquals(posts.toString(), testPosts.toString());
  }

  // Test if the server can retreive a post
  @Test
  public void getPostTest() throws IOException {
    LocalDatabase fakeDatabase = mock(LocalDatabase.class);
    when(fakeDatabase.getPost(testPost.getUUID())).thenReturn(testPost);
    Server.setDatabase(fakeDatabase);

    URL url = new URL(baseURL + "/post/" + testPost.getUUID());
    HttpURLConnection connection = request("GET", url);
    int responseCode = connection.getResponseCode();
    Assertions.assertEquals(responseCode, HTTP_OK);
    Assertions.assertEquals(
        mapper.readValue(responseToString(connection), Post.class).toString(), testPost.toString());
  }

  // Test if the server responds correctly to request for non-existing post
  @Test
  public void getNonExistingPostTest() throws IOException {
    LocalDatabase fakeDatabase = mock(LocalDatabase.class);
    Server.setDatabase(fakeDatabase);

    URL url = new URL(baseURL + "/post/somenonsense");
    HttpURLConnection connection = request("GET", url);
    int responseCode = connection.getResponseCode();
    Assertions.assertEquals(responseCode, HTTP_NOT_FOUND);
  }

  /* POST */
  // Test if the server accepts a new post
  @Test
  public void createPostTest() throws IOException {
    LocalDatabase fakeDatabase = mock(LocalDatabase.class);
    when(fakeDatabase.usernameExists("EdgyBoi")).thenReturn(true);
    Server.setDatabase(fakeDatabase);

    URL url = new URL(baseURL + "/post");
    String body = "{\"owner\": \"EdgyBoi\", \"caption\": \"Funny Picture\", \"image\": \"ASDF\"}";
    HttpURLConnection connection = request("POST", url, body);
    int responseCode = connection.getResponseCode();
    Assertions.assertEquals(responseCode, HTTP_OK);
  }

  // Test if the server responds correctly to request from non-existing user
  @Test
  public void createPostNonExistingUserTest() throws IOException {
    LocalDatabase fakeDatabase = mock(LocalDatabase.class);
    when(fakeDatabase.usernameExists("EdgyBoi")).thenReturn(false);
    Server.setDatabase(fakeDatabase);

    URL url = new URL(baseURL + "/post");
    String body = "{\"owner\": \"EdgyBoi\", \"caption\": \"Funny Picture\", \"image\": \"ASDF\"}";
    HttpURLConnection connection = request("POST", url, body);
    int responseCode = connection.getResponseCode();
    Assertions.assertEquals(responseCode, HTTP_NOT_FOUND);
  }

  // Test if the server rejects garbage input
  @Test
  public void createBadPostTest() throws IOException {
    LocalDatabase fakeDatabase = mock(LocalDatabase.class);
    Server.setDatabase(fakeDatabase);

    URL url = new URL(baseURL + "/post");
    String body = "{ bad json";
    HttpURLConnection connection = request("POST", url, body);
    int responseCode = connection.getResponseCode();
    Assertions.assertEquals(responseCode, HTTP_INTERNAL_ERROR);
  }

  /* Test Comment */
  /* GET */
  // Test if the server can retreive a comment
  @Test
  public void getCommentTest() throws IOException {
    LocalDatabase fakeDatabase = mock(LocalDatabase.class);
    when(fakeDatabase.getPost(testPost.getUUID())).thenReturn(testPost);
    when(fakeDatabase.getComment(testPost.getUUID(), testComment.getUUID()))
        .thenReturn(testComment);
    Server.setDatabase(fakeDatabase);

    URL url =
        new URL(baseURL + "/post/" + testPost.getUUID() + "/comment/" + testComment.getUUID());
    HttpURLConnection connection = request("GET", url);
    int responseCode = connection.getResponseCode();
    Assertions.assertEquals(responseCode, HTTP_OK);
    Comment comment = mapper.readValue(responseToString(connection), Comment.class);
    Assertions.assertEquals(testComment.toString(), comment.toString());
  }

  // Test if the server responds correctly to request for non-existing post
  @Test
  public void getCommentNonExistingPostTest() throws IOException {
    LocalDatabase fakeDatabase = mock(LocalDatabase.class);
    Server.setDatabase(fakeDatabase);

    URL url = new URL(baseURL + "/post/somenonsense/comment/" + testComment.getUUID());
    HttpURLConnection connection = request("GET", url);
    int responseCode = connection.getResponseCode();
    Assertions.assertEquals(responseCode, HTTP_NOT_FOUND);
  }

  // Test if the server responds correctly to request for non-existing comment
  @Test
  public void getNonExistingCommentTest() throws IOException {
    LocalDatabase fakeDatabase = mock(LocalDatabase.class);
    when(fakeDatabase.getPost(testPost.getUUID())).thenReturn(testPost);
    Server.setDatabase(fakeDatabase);

    URL url = new URL(baseURL + "/post/" + testPost.getUUID() + "/comment/somenonsense");
    HttpURLConnection connection = request("GET", url);
    int responseCode = connection.getResponseCode();
    Assertions.assertEquals(responseCode, HTTP_NOT_FOUND);
  }

  // Test if the server can retreive all comments for a post
  @Test
  public void getAllCommentsTest() throws IOException {
    LocalDatabase fakeDatabase = mock(LocalDatabase.class);
    when(fakeDatabase.getPost(testPost.getUUID())).thenReturn(testPost);
    when(fakeDatabase.getComments(testPost.getUUID())).thenReturn(testComments);
    Server.setDatabase(fakeDatabase);

    URL url = new URL(baseURL + "/post/" + testPost.getUUID() + "/comment");
    HttpURLConnection connection = request("GET", url);
    int responseCode = connection.getResponseCode();
    Assertions.assertEquals(responseCode, HTTP_OK);
    List<Comment> comments =
        mapper.readValue(responseToString(connection), new TypeReference<ArrayList<Comment>>() {});
    Assertions.assertEquals(comments.toString(), testComments.toString());
  }

  // Test if the server responds correctly to request for non-existing post
  @Test
  public void getAllCommentsNonExistingPostTest() throws IOException {
    LocalDatabase fakeDatabase = mock(LocalDatabase.class);
    when(fakeDatabase.getComments(testPost.getUUID())).thenReturn(testComments);
    Server.setDatabase(fakeDatabase);

    URL url = new URL(baseURL + "/post/somenonsense/comment");
    HttpURLConnection connection = request("GET", url);
    int responseCode = connection.getResponseCode();
    Assertions.assertEquals(responseCode, HTTP_NOT_FOUND);
  }

  /* POST */
  // Test if the server accepts new comment
  @Test
  public void createCommentTest() throws IOException {
    LocalDatabase fakeDatabase = mock(LocalDatabase.class);
    when(fakeDatabase.usernameExists("EdgyBoi")).thenReturn(true);
    when(fakeDatabase.getPost(testPost.getUUID())).thenReturn(testPost);
    Server.setDatabase(fakeDatabase);

    URL url = new URL(baseURL + "/post/" + testPost.getUUID() + "/comment");
    String body = "{\"author\": \"EdgyBoi\", \"text\": \"Picture is indeed funny\"}";
    HttpURLConnection connection = request("POST", url, body);
    int responseCode = connection.getResponseCode();
    Assertions.assertEquals(responseCode, HTTP_OK);
  }

  // Test if the server responds correctly to request on non-existing post
  @Test
  public void createCommentNonExistingPostTest() throws IOException {
    LocalDatabase fakeDatabase = mock(LocalDatabase.class);
    when(fakeDatabase.usernameExists("EdgyBoi")).thenReturn(true);
    Server.setDatabase(fakeDatabase);

    URL url = new URL(baseURL + "/post/somenonsense/comment");
    String body = "{\"author\": \"EdgyBoi\", \"text\": \"Picture is indeed funny\"}";
    HttpURLConnection connection = request("POST", url, body);
    int responseCode = connection.getResponseCode();
    Assertions.assertEquals(responseCode, HTTP_NOT_FOUND);
  }

  // Test if the server responds correctly to request from non-existing user
  @Test
  public void createCommentNonExistingUserTest() throws IOException {
    LocalDatabase fakeDatabase = mock(LocalDatabase.class);
    when(fakeDatabase.usernameExists("EdgyBoi")).thenReturn(false);
    when(fakeDatabase.getPost(testPost.getUUID())).thenReturn(testPost);
    Server.setDatabase(fakeDatabase);

    URL url = new URL(baseURL + "/post/" + testPost.getUUID() + "/comment");
    String body = "{\"author\": \"EdgyBoi\", \"text\": \"Picture is indeed funny\"}";
    HttpURLConnection connection = request("POST", url, body);
    int responseCode = connection.getResponseCode();
    Assertions.assertEquals(responseCode, HTTP_NOT_FOUND);
  }

  // Test if the server rejects garbage input
  @Test
  public void createBadCommentTest() throws IOException {
    LocalDatabase fakeDatabase = mock(LocalDatabase.class);
    when(fakeDatabase.getPost(testPost.getUUID())).thenReturn(testPost);
    Server.setDatabase(fakeDatabase);

    URL url = new URL(baseURL + "/post/" + testPost.getUUID() + "/comment");
    String body = "{ bad json";
    HttpURLConnection connection = request("POST", url, body);
    int responseCode = connection.getResponseCode();
    Assertions.assertEquals(responseCode, HTTP_INTERNAL_ERROR);
  }
}
