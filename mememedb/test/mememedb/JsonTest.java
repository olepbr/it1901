package mememedb;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import mememedb.json.MememeModule;

public class JsonTest extends ApplicationTest {

  @Test
  public void testUserSerializer()
  {
    User user = new User(1, "Ola Nordman", "XxX_SpicyBoi69_XxX", "spice@memes.com");
    Post post = new Post(user.getNickname(), "haha", "files/spice.png");
    user.addPost(post);

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
  public void testPostSerializer()
  {
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
