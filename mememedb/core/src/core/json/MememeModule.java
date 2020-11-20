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
}
