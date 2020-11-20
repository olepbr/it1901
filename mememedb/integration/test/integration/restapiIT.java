package integration;

import core.datastructures.Comment;
import core.databases.LocalDatabase;
import core.datastructures.Post;
import core.databases.RestDatabase;
import core.datastructures.User;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import restapi.Server;

public class restapiIT {

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
  public void integrationTest() {
    // Details of user
    String nickname = "OleBoi";
    String newName = "Ola Nordmann";
    String newEmail = "ola@nordmann.no";
    String newPassword = "strongpassword123";

    // Attempt to create a user
    database.newUser(newName, nickname, newEmail, newPassword);

    // Retreive the new user from the database
    User user = database.getUser(nickname);

    // Assert that the user is the same
    Assertions.assertNotNull(user);
    Assertions.assertEquals(newName, user.getName());
    Assertions.assertEquals(nickname, user.getNickname());
    Assertions.assertEquals(newEmail, user.getEmail());
    Assertions.assertEquals(User.hashPassword(newPassword), user.getPassword());

    // Test creation of posts
    // and verify that posts were created

    // Details of post
    String newCaption1 = "Very funny picture";
    String newCaption2 = "The same funny picture";
    String newCaption3 = "Yet another picture";
    String newImageData = "base64encodedImage";
    List<String> captions = Arrays.asList(newCaption1, newCaption2, newCaption3);

    // Attempt to create the post
    database.newPost(nickname, newCaption1, newImageData);
    database.newPost(nickname, newCaption2, newImageData);
    database.newPost(nickname, newCaption3, newImageData);

    // Retreive the newly created posts from the database
    Collection<Post> posts = database.getPosts();

    Assertions.assertNotNull(posts);
    Assertions.assertTrue(posts.size() == 3);
    for (Post post : posts) {
      Assertions.assertTrue(captions.contains(post.getText()));
    }

    // Comment to post
    String newCommentText = "NO";

    // Retreive the UUID of one of the posts
    Post commentPost = (Post) database.getPosts().toArray()[0];
    String postUUID = commentPost.getUUID();

    // Attempt to create the comment
    database.newComment(newCommentText, nickname, postUUID);

    // Get comment for post
    Comment comment = (Comment) database.getComments(postUUID).toArray()[0];

    Assertions.assertEquals(newCommentText, comment.getText());
  }
}
