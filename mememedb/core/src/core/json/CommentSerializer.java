package core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.datastructures.Comment;
import java.io.IOException;

/**
 * Class for serializing Comment objects.
 * Format: { "uuid": "...", "author": "...", "text": "..." }
 *
 * @author Jostein Bakkevig
 */
class CommentSerializer extends JsonSerializer<Comment> {

  @Override
  public void serialize(Comment comment, JsonGenerator jsonGen, SerializerProvider serializerProvider)
      throws IOException {
    jsonGen.writeStartObject();
    jsonGen.writeStringField("uuid", comment.getUUID());
    jsonGen.writeStringField("author", comment.getAuthor());
    jsonGen.writeStringField("text", comment.getText());
    jsonGen.writeEndObject();
  }
}
