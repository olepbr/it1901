package mememedb.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;
import mememedb.datastructures.Post;
import mememedb.datastructures.User;

public class UserDeserializer extends JsonDeserializer<User> {

  /**
   * Class for deserializing JSON into User objects 
   * format: { "id": "...", "name": "...", "nickname": "...", 
   * "email": "...", "posts": [ ... ] }
   * 
   * @author Ole Peder Brandtzæg
   */

  private PostDeserializer postDeserializer = new PostDeserializer();

  @Override
  public User deserialize(JsonParser parser, DeserializationContext ctxt) 
      throws IOException, JsonProcessingException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    if (treeNode instanceof ObjectNode) {
      ObjectNode objectNode = (ObjectNode) treeNode;
      User user = new User();
      JsonNode idNode = objectNode.get("id");
      if (idNode instanceof IntNode) {
        user.setId(((IntNode) idNode).asInt());
      }
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
      JsonNode postsNode = objectNode.get("posts");
      if (postsNode instanceof ArrayNode) {
        for (JsonNode elementNode : ((ArrayNode) postsNode)) {
          Post post = postDeserializer.deserialize(elementNode);
          if (post != null) {
            user.addPost(post);
          }
        }
      }
      return user;
    }
    return null;
  }

}
