package core.databases;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import core.datastructures.Comment;
import core.datastructures.Post;
import core.datastructures.User;

public class RestDatabaseTest {

  private static Collection<Post> testPosts;
  private static Collection<User> testUsers;
  private static Comment testComment;
  private static List<Comment> testComments;
  private static Post testPost;
  private static RestDatabase restDatabase;
  private static User testUser;
  private WireMockConfiguration config;
  private WireMockServer wireMockServer;

  @BeforeAll
  public static void dataSetup() {
    testUser = new User("Yolo", "yolo", "yolo@lol.com", "verygood");
    testUsers = new ArrayList<User>();
    testUsers.add(testUser);
    testPost = new Post("yolo", "heyhey", "mordi");
    testPost.setUUID("12345");
    testUser.addPost(testPost.getUUID());
    testComment = new Comment("yolo", "høhø");
    testPost.addComment(testComment);
    testComments = new ArrayList<Comment>();
    testComments.add(testComment);
    testPosts = new ArrayList<Post>();
    testPosts.add(testPost);
  }
  
  @BeforeEach
  public void restDatabaseTestSetup() {
    config = WireMockConfiguration.wireMockConfig().port(8087);
    wireMockServer = new WireMockServer(config.portNumber());
    wireMockServer.start();
    WireMock.configureFor("localhost", config.portNumber());
    restDatabase = new RestDatabase("http://localhost:" + wireMockServer.port());
  }
  
  @Test
  public void testGetRequest() {
    stubFor(get(urlEqualTo("/")).withHeader("Accept", equalTo("application/json"))
        .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
        .withBody("Welcome to mememedb!")));
    assertEquals("Welcome to mememedb!", restDatabase.requestHandler("/", null, "GET").body());
  }

  @Test
  public void testPostRequest() {
    stubFor(post(urlEqualTo("/user")).withHeader("Accept", equalTo("application/json"))
        .withRequestBody(equalTo("{\"name\":\"" + testUser.getName() + "\",\"nickname\""
            + ":\"" + testUser.getNickname() + "\",\"email\":\"" + testUser.getEmail()
                    + "\",\"password\":\"" + testUser.getPassword() + "\""
                  + ",\"posts\":[\"12345\"]}"))
            .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
                .withBody("Welcome to mememedb!")));
    assertEquals("Welcome to mememedb!", restDatabase.requestHandler("/user", "{\"name\":\"" + testUser.getName() + "\",\"nickname\""
            + ":\"" + testUser.getNickname() + "\",\"email\":\"" + testUser.getEmail()
                    + "\",\"password\":\"" + testUser.getPassword() + "\""
                  + ",\"posts\":[\"12345\"]}", "POST").body());
  }

  @Test
  public void testMalformedURI() {
    assertThrows(IllegalArgumentException.class, () -> {restDatabase.requestHandler("æ", "", "GET");});
  }

  @Test
  public void testGetUsers() {
    stubFor(get(urlEqualTo("/user")).withHeader("Accept", equalTo("application/json"))
        .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
        .withBody("[ {\"nickname\": \"" + testUser.getNickname() + "\", \"name\""
            + ": \"" + testUser.getName() + "\", \"email\": \"" + testUser.getEmail()
                + "\", \"posts\": [\"12345\"],"
                    + "\"password\": \"" + testUser.getPassword() + "\"} ]")));
    Collection<User> receivedUsers = restDatabase.getUsers();
    assertEquals(testUsers.toString(), receivedUsers.toString());
  }

  @Test
  public void testGetUser() {
    stubFor(get(urlEqualTo("/user/" + testUser.getNickname())).withHeader("Accept", equalTo("application/json"))
        .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
        .withBody("{\"nickname\": \"" + testUser.getNickname() + "\", \"name\""
            + ": \"" + testUser.getName() + "\", \"email\": \"" + testUser.getEmail()
                + "\", \"posts\": [\"12345\"],"
                    + "\"password\": \"" + testUser.getPassword() + "\"}")));
    User receivedUser = restDatabase.getUser("yolo");
    assertEquals(testUser.getPassword(), receivedUser.getPassword());
  }

  @Test
  public void testGetComment() {
    stubFor(get(urlEqualTo("/post/" + testPost.getUUID() + "/comment/" + testComment.getUUID()))
        .withHeader("Accept", equalTo("application/json"))
            .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
                .withBody("{\"UUID\": \"" + testComment.getUUID() + "\", \"author\": \""
                    + testComment.getAuthor() + "\", \"text\": \"" + testComment.getText() + "\"}")));
    Comment receivedComment = restDatabase.getComment(testComment.getUUID(), testPost.getUUID());
    assertEquals(testComment.getText(), receivedComment.getText());
  }

  @Test
  public void testGetComments() {
    stubFor(get(urlEqualTo("/post/" + testPost.getUUID() + "/comment"))
        .withHeader("Accept", equalTo("application/json"))
            .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
                .withBody("[ {\"UUID\": \"" + testComment.getUUID() + "\", \"author\": \""
                    + testComment.getAuthor() + "\", \"text\": \"" + testComment.getText() + "\"} ]")));
    Collection<Comment> receivedComments = restDatabase.getComments(testPost.getUUID());
    assertEquals(testComments.toString(), receivedComments.toString());
  }

  @Test
  public void testGetPost() {
    stubFor(get(urlEqualTo("/post/" + testPost.getUUID()))
        .withHeader("Accept", equalTo("application/json"))
            .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
                .withBody("{\"uuid\": \"" + testPost.getUUID() + "\", \"owner\": \""
                    + testPost.getOwner() + "\", \"caption\": \"" + testPost.getText() + "\", "
                        + "\"image\": \"" + testPost.getImage() + "\", \"comments\": "
                            + "[ {\"uuid\": \"" + testComment.getUUID() + "\", \"author\": \""
                                + testComment.getAuthor() + "\", \"text\": \"" 
                                    + testComment.getText() + "\"} ] }")));
    Post receivedPost = restDatabase.getPost(testPost.getUUID());
    assertEquals(testPost.toString(), receivedPost.toString());
  }

  @Test
  public void testGetPosts() {
    stubFor(get(urlEqualTo("/post"))
        .withHeader("Accept", equalTo("application/json"))
            .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
                .withBody("[ {\"uuid\": \"" + testPost.getUUID() + "\", \"owner\": \""
                    + testPost.getOwner() + "\", \"caption\": \"" + testPost.getText() + "\", "
                        + "\"image\": \"" + testPost.getImage() + "\", \"comments\": "
                            + "[ {\"uuid\": \"" + testComment.getUUID() + "\", \"author\": \""
                                + testComment.getAuthor() + "\", \"text\": \"" 
                                    + testComment.getText() + "\"} ] } ]")));
    Collection<Post> receivedPosts = restDatabase.getPosts();
    assertEquals(testPosts.toString(), receivedPosts.toString());
  }

  @Test
  public void testTryLoginSuccess() {
    stubFor(get(urlEqualTo("/user/" + testUser.getNickname())).withHeader("Accept", equalTo("application/json"))
        .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
        .withBody("{\"nickname\": \"" + testUser.getNickname() + "\", \"name\""
            + ": \"" + testUser.getName() + "\", \"email\": \"" + testUser.getEmail()
                + "\", \"posts\": [\"12345\"],"
                    + "\"password\": \"" + testUser.getPassword() + "\"}")));
    User receivedUser = restDatabase.tryLogin(testUser.getNickname(), "verygood");
    assertNotNull(receivedUser);
    assertEquals(testUser.getPassword(), receivedUser.getPassword());
  }

  @Test
  public void testTryLoginFailure() {
    assertNull(restDatabase.tryLogin("jegharikke", "enbruker"));
  }

  @Test
  public void testNicknameExists() {
    stubFor(get(urlEqualTo("/user/" + testUser.getNickname())).withHeader("Accept", equalTo("application/json"))
        .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
        .withBody("{\"nickname\": \"" + testUser.getNickname() + "\", \"name\""
            + ": \"" + testUser.getName() + "\", \"email\": \"" + testUser.getEmail()
                + "\", \"posts\": [\"12345\"],"
                    + "\"password\": \"" + testUser.getPassword() + "\"}")));
    assertTrue(restDatabase.nicknameExists(testUser.getNickname()));
  }

  @Test
  public void testNicknameDoesNotExist() {
    assertFalse(restDatabase.nicknameExists(testUser.getNickname()));
  }

  @AfterEach
  public void stopWireMockServer() {
    wireMockServer.stop();
  }
}