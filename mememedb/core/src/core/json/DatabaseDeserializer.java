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

class DatabaseDeserializer extends JsonDeserializer<Database> {

  /**
   * Class for deserializing JSON into Database objects
   * format: { "users": [ ... ] }
   *
   * @author Ole Peder Brandtzæg
   */

  private UserDeserializer userDeserializer = new UserDeserializer();

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