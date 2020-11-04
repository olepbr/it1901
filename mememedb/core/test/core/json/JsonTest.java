package core.json;

import com.fasterxml.jackson.core.JsonProcessingException;

import core.datastructures.Comment;
import core.datastructures.Database;
import core.datastructures.Post;
import core.datastructures.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JsonTest {

  @Test
  public void testUserSerializer() {
    User user = new User("Ola Nordman", "XxX_SpicyBoi69_XxX", "spice@memes.com");
    Post post = new Post(user.getNickname(), "haha", "files/spice.png");
    user.addPost(post.getUUID());

    try {
      String json = MememeModule.serializeUser(user);
      User user2 = MememeModule.deserializeUser(json);
      Assertions.assertTrue(user2 instanceof User);
      Assertions.assertEquals(user.toString(), user2.toString());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testPostSerializer() {
    Post post = new Post("XxX_SpicyBoi69[]_XxX", "()Spicy meme{}", "files/spice.png");

    try {
      String json = MememeModule.serializePost(post);
      Post post2 = MememeModule.deserializePost(json);
      Assertions.assertTrue(post2 instanceof Post);
      Assertions.assertEquals(post.toString(), post2.toString());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testCommentSerializer() {
    Comment comment = new Comment("xXx_gertrude_xXx", "haha, i like this!");

    try {
      String json = MememeModule.serializeComment(comment);
      Comment comment2 = MememeModule.deserializeComment(json);
      Assertions.assertTrue(comment2 instanceof Comment);
      Assertions.assertEquals(comment.toString(), comment2.toString());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testDatabaseSerializer() {
    Database database = new Database();
    User user1 = new User("Bert johnsos", "berjon67", "ber@john.com");
    User user2 = new User("Jahn jahnman", "imthejahnman", "jahn@plan.can");
    database.addUser(user1);
    database.addUser(user2);
    Post post1 = new Post("berjon67", "This is an image", "someimagedata");
    Post post2 = new Post("berjon67", "This is also an image", "someotherimage");
    database.addPost(post1);
    database.addPost(post2);
    post1.addComment(new Comment("imthejahnman", "haha, i like this"));
    
    try {
      String json = MememeModule.serializeDatabase(database);
      Database database2 = MememeModule.deserializeDatabase(json);
      Assertions.assertTrue(database2 instanceof Database);
      Assertions.assertEquals(database.toString(), database2.toString());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }
}
