package core.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import core.datastructures.Comment;
import core.datastructures.LocalDatabase;
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
    addSerializer(LocalDatabase.class, new DatabaseSerializer());
    addSerializer(Comment.class, new CommentSerializer());
    addDeserializer(Post.class, new PostDeserializer());
    addDeserializer(User.class, new UserDeserializer());
    addDeserializer(LocalDatabase.class, new DatabaseDeserializer());
    addDeserializer(Comment.class, new CommentDeserializer());
    mapper.registerModule(this);
  }

  public static String serializeUser(User user) throws JsonProcessingException {
    return (mapper.writeValueAsString(user));
  }

  public static User deserializeUser(String user) throws JsonProcessingException {
    return (mapper.readValue(user, User.class));
  }

  public static User deserializeUser(File file) throws IOException {
    return (mapper.readValue(file, User.class));
  }

  public static User deserializeUser(Reader reader) throws IOException {
    return (mapper.readValue(reader, User.class));
  }

  public static String serializePost(Post post) throws JsonProcessingException {
    return (mapper.writeValueAsString(post));
  }

  public static Post deserializePost(String post) throws JsonProcessingException {
    return (mapper.readValue(post, Post.class));
  }

  public static String serializeDatabase(LocalDatabase database) throws JsonProcessingException {
    return (mapper.writeValueAsString(database));
  }

  public static LocalDatabase deserializeDatabase(String string) throws JsonProcessingException {
    return (mapper.readValue(string, LocalDatabase.class));
  }

  public static LocalDatabase deserializeDatabase(Reader reader) throws IOException {
    return (mapper.readValue(reader, LocalDatabase.class));
  }

  public static String serializeComment(Comment comment) throws JsonProcessingException {
    return (mapper.writeValueAsString(comment));
  }

  public static Comment deserializeComment(String comment) throws JsonProcessingException {
    return (mapper.readValue(comment, Comment.class));
  }

  public static Comment deserializeComment(Reader reader) throws IOException {
    return (mapper.readValue(reader, Comment.class));
  }
}
