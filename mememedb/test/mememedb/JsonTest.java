package mememedb;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import mememedb.json.MememeModule;

public class JsonTest extends ApplicationTest {

  @Test
  public void testUserSerializer() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new MememeModule());
    User user = new User(1, "Ola Nordman", "XxX_SpicyBoi69_XxX", "spice@memes.com");
    Post post = new Post(user.getNickname(), "haha", "files/spice.png");
    user.addPost(post);

    try {
      String json = mapper.writeValueAsString(user);
      User user2 = mapper.readValue(json, User.class);
      Assertions.assertTrue(user2 instanceof User);
      Assertions.assertEquals(user.toString(), user2.toString());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testPostSerializer() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new MememeModule());
    Post post = new Post("XxX_SpicyBoi69[]_XxX", "()Spicy meme{}", "files/spice.png");

    try {
      String json = mapper.writeValueAsString(post);
      Post post2 = mapper.readValue(json, Post.class);
      Assertions.assertTrue(post2 instanceof Post);
      Assertions.assertEquals(post.toString(), post2.toString());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

}
