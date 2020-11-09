package core.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import core.datastructures.LocalDatabase;
import core.datastructures.User;
import core.datastructures.Post;
import java.io.IOException;

/**
 * Class for deserializing JSON into LocalDatabase objects.
 * Format: { "users": [ ... ] }
 *
 * @author Ole Peder Brandtzæg
 */
class DatabaseDeserializer extends JsonDeserializer<LocalDatabase> {

  private UserDeserializer userDeserializer = new UserDeserializer();
  private PostDeserializer postDeserializer = new PostDeserializer();

  /**
   * Class for deserializing JSON into a LocalDatabase object.
   *
   * @param parser JsonParser object used for parsing the JSON (provided by Jackson).
   * @param ctxt DeseralizationContext object used for
   *        aiding the JSON parsing (provided by Jackson).
   * @return The LocalDatabase object resulting from the deserialization
   *        or null if the JSON doesn't have the correct format.
   * @throws IOException If a more general I/O error occurs.
   * @throws JsonProcessingException If an error occurs when processing the JSON.
   */
  @Override
  public LocalDatabase deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    if (treeNode instanceof ObjectNode) {
      ObjectNode objectNode = (ObjectNode) treeNode;
      LocalDatabase database = new LocalDatabase();
      JsonNode usersNode = objectNode.get("users");
      if (usersNode instanceof ArrayNode) {
        for (JsonNode elementNode : ((ArrayNode) usersNode)) {
          User user = userDeserializer.deserialize(elementNode);
          if (user != null) {
            database.addUser(user);
          }
        }
      }
      JsonNode postsNode = objectNode.get("posts");
      if (postsNode instanceof ArrayNode) {
        for (JsonNode elementNode : ((ArrayNode) postsNode)) {
          Post post = postDeserializer.deserialize(elementNode);
          if (post != null) {
            database.addPost(post);
          }
        }
        return database;
      }
    }
    return null;
  }
}
