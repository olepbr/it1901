package core.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import core.datastructures.Post;
import core.datastructures.User;
import java.io.File;
import java.io.IOException;
import java.io.Reader;

/**
 * Module collecting individual class serializers.
 *
 * @author Ole Peder Brandtzæg
 */
public class MememeModule extends SimpleModule {

  private static final String NAME = "MememeModule";
  private static final ObjectMapper mapper = new ObjectMapper();

  /** Initializes a MememeModule object with serializers and deserializers. */
  public MememeModule() {
	super(NAME, Version.unknownVersion());
    addSerializer(Post.class, new PostSerializer());
    addSerializer(User.class, new UserSerializer());
    addDeserializer(Post.class, new PostDeserializer());
    addDeserializer(User.class, new UserDeserializer());
  }

  /**
   * [TODO:description]
   *
   * @param user [TODO:description]
   * @return [TODO:description]
   * @throws JsonProcessingException [TODO:description]
   */
  public static String serializeUser(User user) throws JsonProcessingException {
    mapper.registerModule(new MememeModule());
    return (mapper.writeValueAsString(user));
  }

  /**
   * [TODO:description]
   *
   * @param user [TODO:description]
   * @return [TODO:description]
   * @throws JsonProcessingException [TODO:description]
   */
  public static User deserializeUser(String user) throws JsonProcessingException {
    mapper.registerModule(new MememeModule());
    return (mapper.readValue(user, User.class));
  }

  /**
   * [TODO:description]
   *
   * @param file [TODO:description]
   * @return [TODO:description]
   * @throws IOException [TODO:description]
   */
  public static User deserializeUser(File file) throws IOException {
    mapper.registerModule(new MememeModule());
    return (mapper.readValue(file, User.class));
  }

  /**
   * [TODO:description]
   *
   * @param reader [TODO:description]
   * @return [TODO:description]
   * @throws IOException [TODO:description]
   */
  public static User deserializeUser(Reader reader) throws IOException {
    mapper.registerModule(new MememeModule());
    return (mapper.readValue(reader, User.class));
  }

  /**
   * [TODO:description]
   *
   * @param post [TODO:description]
   * @return [TODO:description]
   * @throws JsonProcessingException [TODO:description]
   */
  public static String serializePost(Post post) throws JsonProcessingException {
    mapper.registerModule(new MememeModule());
    return (mapper.writeValueAsString(post));
  }

  /**
   * [TODO:description]
   *
   * @param post [TODO:description]
   * @return [TODO:description]
   * @throws JsonProcessingException [TODO:description]
   */
  public static Post deserializePost(String post) throws JsonProcessingException {
    mapper.registerModule(new MememeModule());
    return (mapper.readValue(post, Post.class));
  }
}
