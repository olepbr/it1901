package core.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import core.datastructures.Database;
import core.datastructures.User;
import java.io.IOException;

/**
 * Class for deserializing JSON into Database objects.
 * Format: { "users": [ ... ] }
 *
 * @author Ole Peder Brandtzæg
 */
class DatabaseDeserializer extends JsonDeserializer<Database> {
  private UserDeserializer userDeserializer = new UserDeserializer();

  /**
   * Class for deserializing JSON into a Database object.
   *
   * @param parser JsonParser object used for parsing the JSON (provided by Jackson).
   * @param ctxt DeseralizationContext object used for aiding the JSON parsing (provided by Jackson).
   * @return The Database object resulting from the deserialization or null if the JSON doesn't have the correct format.
   * @throws IOException If a more general I/O error occurs.
   * @throws JsonProcessingException If an error occurs when processing the JSON.
   */
  @Override
  public Database deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    if (treeNode instanceof ObjectNode) {
      ObjectNode objectNode = (ObjectNode) treeNode;
      Database database = new Database();
      JsonNode usersNode = objectNode.get("users");
      if (usersNode instanceof ArrayNode) {
        for (JsonNode elementNode : ((ArrayNode) usersNode)) {
          User user = userDeserializer.deserialize(elementNode);
          if (user != null) {
            database.saveUser(user);
          }
        }
      }
      return database;
    }
    return null;
  }
}
