package integration;

import core.datastructures.LocalDatabase;
import core.datastructures.Post;
import core.datastructures.RestDatabase;
import core.datastructures.User;
import java.util.Collection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import restapi.Server;

public class TestIT {

  // Set up RestDatabase
  private static int port = 8082;
  private static String baseURL = "http://localhost:" + port;
  private static RestDatabase database = new RestDatabase(baseURL);

  @BeforeAll
  public static void before() {
    // Initialize with empty database
    // Because we do not provide the local database with an IO
    // File storage is not included in this test
    Server.setDatabase(new LocalDatabase());
    Server.setupServer(port);
  }

  @AfterAll
  public static void after() {
    Server.shutdownServer();
  }

  // Test communication LocalDatabase <-> REST API <-> RestDatabase

  // Test creation of user
  // and verify that user was created
  @Test
  public void createUserTest() {
    // Details of user
    String newName = "Ola Nordmann";
    String newNickname = "OleBoi";
    String newEmail = "ola@nordmann.no";
    String newPassword = "strongpassword123";

    // Attempt to create a user
    database.newUser(newName, newNickname, newEmail, newPassword);

    // Retreive the new user from the database
    User user = database.getUser(newNickname);

    // Assert that the user is the same
    Assertions.assertNotNull(user);
    Assertions.assertEquals(newName, user.getName());
    Assertions.assertEquals(newNickname, user.getNickname());
    Assertions.assertEquals(newEmail, user.getEmail());
    Assertions.assertEquals(User.hashPassword(newPassword), user.getPassword());
  }

  // Test creation of posts
  // and verify that posts were created
  @Test
  public void createPostTest() {
    // Details of post
    String nickname = "OleBoi";
    String newCaption1 = "Very funny picture";
    String newCaption2 = "The same funny picture";
    String newCaption3 = "Yet another picture";
    String newImageData = "base64encodedImage";

    // Attempt to create the post
    database.newPost(nickname, newCaption1, newImageData);
    database.newPost(nickname, newCaption2, newImageData);
    database.newPost(nickname, newCaption3, newImageData);

    // Retreive the newly created posts from the database
    Collection<Post> posts = database.getPosts();

    Assertions.assertNotNull(posts);
  }

  // Test creation of comment
  @Test
  public void createCommentsTest() {

    Assertions.assertTrue(true);
  }
}
