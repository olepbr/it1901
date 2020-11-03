package core.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import core.datastructures.Post;
import java.io.IOException;

/**
 * Class for deserializing Post objects.
 * Format: { "owner": "...", "caption": "...", "image": "..." }
 *
 * @author Ole Peder Brandtzæg
 */
class PostDeserializer extends JsonDeserializer<Post> {

  @Override
  public Post deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    return deserialize((JsonNode) treeNode);
  }

  /**
   * Reads a JsonNode and converts it to a Post object.
   *
   * @param jsonNode The node to be read.
   * @return A new Post object containing the information from the JsonNode.
   */
  public Post deserialize(JsonNode jsonNode) {
    if (jsonNode instanceof ObjectNode) {
      ObjectNode objectNode = (ObjectNode) jsonNode;
      Post post = new Post();
      JsonNode uuidNode = objectNode.get("uuid");
      if (uuidNode instanceof TextNode) {
        post.setUUID(((TextNode) uuidNode).asText());
      }
      JsonNode ownerNode = objectNode.get("owner");
      if (ownerNode instanceof TextNode) {
        post.setOwner(((TextNode) ownerNode).asText());
      }
      JsonNode captionNode = objectNode.get("caption");
      if (captionNode instanceof TextNode) {
        post.setText(((TextNode) captionNode).asText());
      }
      JsonNode imageNode = objectNode.get("image");
      if (imageNode instanceof TextNode) {
        post.setImageData(((TextNode) imageNode).asText());
      }
      return post;
    }
    return null;
  }
}
