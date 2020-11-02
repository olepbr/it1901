package core.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import core.datastructures.Post;
import core.datastructures.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JsonTest {

  @Test
  public void testUserSerializer() {
    User user = new User(1, "Ola Nordman", "XxX_SpicyBoi69_XxX", "spice@memes.com");
    Post post = new Post(user.getNickname(), "haha", "files/spice.png");
    user.addPost(post.getId());

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
}
