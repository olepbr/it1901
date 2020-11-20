package core.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import core.datastructures.Comment;
import java.io.IOException;

/**
 * Class for deserializing Comment objects. Format: 
 * { "uuid": "...", "author": "...", "text": "...", "date": "..."}
 *
 * @author Jostein Bakkevig
 */
class CommentDeserializer extends JsonDeserializer<Comment> {

  @Override
  public Comment deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    return deserialize((JsonNode) treeNode);
  }

  /**
   * Reads a JsonNode and converts it to a Comment object.
   *
   * @param jsonNode The node to be read.
   * @return A new Comment object containing the information from the JsonNode.
   */
  public Comment deserialize(JsonNode jsonNode) {
    if (jsonNode instanceof ObjectNode) {
      ObjectNode objectNode = (ObjectNode) jsonNode;
      Comment comment = new Comment();
      JsonNode uuidNode = objectNode.get("uuid");
      if (uuidNode instanceof TextNode) {
        comment.setUUID(((TextNode) uuidNode).asText());
      }
      JsonNode ownerNode = objectNode.get("author");
      if (ownerNode instanceof TextNode) {
        comment.setAuthor(((TextNode) ownerNode).asText());
      }
      JsonNode captionNode = objectNode.get("text");
      if (captionNode instanceof TextNode) {
        comment.setText(((TextNode) captionNode).asText());
      }
      JsonNode dateNode = objectNode.get("date");
      if (dateNode instanceof TextNode) {
        comment.setDate(((TextNode) dateNode).asText());
      }
      return comment;
    }
    return null;
  }
}
