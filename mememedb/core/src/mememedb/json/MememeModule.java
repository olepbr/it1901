package mememedb.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import mememedb.datastructures.Post;
import mememedb.datastructures.User;

public class MememeModule extends SimpleModule {
  /**
   * Module collecting individual class serializers.
   * 
   * @author Ole Peder Brandtzæg
   * 
   */

  private static final String NAME = "MememeModule";
  private static final VersionUtil VERSION_UTIL = new VersionUtil() {
  };
  private static final ObjectMapper mapper = new ObjectMapper();

  /**
   * Initializes a MememeModule object with serializers and deserializers.
   */
  public MememeModule() {
    super(NAME, VERSION_UTIL.version());
    addSerializer(Post.class, new PostSerializer());
    addSerializer(User.class, new UserSerializer());
    addDeserializer(Post.class, new PostDeserializer());
    addDeserializer(User.class, new UserDeserializer());
  }

  public static String serializeUser(User user) throws JsonProcessingException {
    mapper.registerModule(new MememeModule());
    return (mapper.writeValueAsString(user));
  }

  public static User deserializeUser(String user) throws JsonProcessingException {
    mapper.registerModule(new MememeModule());
    return (mapper.readValue(user, User.class));
  }

  public static String serializePost(Post post) throws JsonProcessingException {
    mapper.registerModule(new MememeModule());
    return (mapper.writeValueAsString(post));
  }

  public static Post deserializePost(String post) throws JsonProcessingException {
    mapper.registerModule(new MememeModule());
    return (mapper.readValue(post, Post.class));
  }
}
