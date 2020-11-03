package core.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import core.datastructures.User;
import java.io.IOException;

/**
 * Class for deserializing JSON into User objects. Format: { "id": "...", "name": "...", "nickname":
 * "...", "email": "...", "posts": [ ... ] }
 *
 * @author Ole Peder Brandtzæg
 */
class UserDeserializer extends JsonDeserializer<User> {

  @Override
  public User deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    return deserialize((JsonNode) treeNode);
  }

  public User deserialize(JsonNode jsonNode) {
    if (jsonNode instanceof ObjectNode) {
      ObjectNode objectNode = (ObjectNode) jsonNode;
      User user = new User();
      JsonNode nameNode = objectNode.get("name");
      if (nameNode instanceof TextNode) {
        user.setName(((TextNode) nameNode).asText());
      }
      JsonNode nickNode = objectNode.get("nickname");
      if (nickNode instanceof TextNode) {
        user.setNickname(((TextNode) nickNode).asText());
      }
      JsonNode emailNode = objectNode.get("email");
      if (emailNode instanceof TextNode) {
        user.setEmail(((TextNode) emailNode).asText());
      }
      JsonNode passwordNode = objectNode.get("password");
      if (passwordNode instanceof TextNode) {
        user.setHashedPassword(((TextNode) passwordNode).asText());
      }
      JsonNode postsNode = objectNode.get("posts");
      if (postsNode instanceof ArrayNode) {
        for (JsonNode elementNode : ((ArrayNode) postsNode)) {
          if (elementNode instanceof TextNode) {
            user.addPost(elementNode.asText());
          }
        }
      }
      return user;
    }
    return null;
  }
}
