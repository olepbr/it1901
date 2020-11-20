package core.json;

import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import core.datastructures.Comment;
import core.databases.LocalDatabase;
import core.datastructures.Post;
import core.datastructures.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JsonTest {

  static ObjectMapper mapper = new ObjectMapper();

  @BeforeAll
  static public void setUp() {
      mapper.registerModule(new MememeModule());
  }


  @Test
  public void testUserSerializer() {
    User user = new User("Ola Nordman", "XxX_SpicyBoi69_XxX", "spice@memes.com", "apassword");
    Post post = new Post(user.getNickname(), "haha", "files/spice.png");
    user.addPost(post.getUUID());

    try {
      String json = mapper.writeValueAsString(user);
      System.out.println(json);
      User user2 = mapper.readValue(json, User.class);
      Assertions.assertTrue(user2 instanceof User);
      Assertions.assertEquals(user.toString(), user2.toString());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      fail("Error in json processing");
    }
  }

  @Test
  public void testPostSerializer() {
    Post post = new Post("XxX_SpicyBoi69[]_XxX", "()Spicy meme{}", "files/spice.png");

    try {
      String json = mapper.writeValueAsString(post);
      System.out.println(json);
      Post post2 = mapper.readValue(json, Post.class);
      Assertions.assertTrue(post2 instanceof Post);
      Assertions.assertEquals(post.toString(), post2.toString());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      fail("Error in json processing");
    }
  }

  @Test
  public void testCommentSerializer() {
    Comment comment = new Comment("xXx_gertrude_xXx", "haha, i like this!");

    try {
      String json = mapper.writeValueAsString(comment);
      System.out.println(json);
      Comment comment2 = mapper.readValue(json, Comment.class);
      Assertions.assertTrue(comment2 instanceof Comment);
      Assertions.assertEquals(comment.toString(), comment2.toString());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      fail("Error in json processing");
    }
  }

  @Test
  public void testDatabaseSerializer() {
    LocalDatabase database = new LocalDatabase();
    User user1 = new User("Bert johnsos", "berjon67", "ber@john.com", "somepassword");
    User user2 = new User("Jahn jahnman", "imthejahnman", "jahn@plan.can", "skibbedibibop");
    database.addUser(user1);
    database.addUser(user2);
    Post post1 = new Post("berjon67", "This is an image", "someimagedata");
    Post post2 = new Post("berjon67", "This is also an image", "someotherimage");
    database.addPost(post1);
    database.addPost(post2);
    post1.addComment(new Comment("imthejahnman", "haha, i like this"));
    
    try {
      String json = mapper.writeValueAsString(database);
      System.out.println(json);
      LocalDatabase database2 = mapper.readValue(json, LocalDatabase.class);
      Assertions.assertTrue(database2 instanceof LocalDatabase);
      Assertions.assertEquals(database.toString(), database2.toString());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      fail("Error in json processing");
    }
  }
}