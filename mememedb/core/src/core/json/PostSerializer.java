package core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.datastructures.Comment;
import core.datastructures.Post;
import java.io.IOException;

/**
 * Class for serializing Post objects. 
 * Format: { uuid: "..." "owner": "...", "caption": "...", "image": "..." }
 *
 * @author Ole Peder Brandtzæg
 */
class PostSerializer extends JsonSerializer<Post> {

  @Override
  public void serialize(Post post, JsonGenerator jsonGen, SerializerProvider serializerProvider)
      throws IOException {
    jsonGen.writeStartObject();
    jsonGen.writeStringField("uuid", post.getUUID());
    jsonGen.writeStringField("owner", post.getOwner());
    jsonGen.writeStringField("caption", post.getText());
    jsonGen.writeStringField("image", post.getImage());
    jsonGen.writeArrayFieldStart("comments");
    for (Comment comment : post.getComments()) {
      jsonGen.writeObject(comment);
    }
    jsonGen.writeEndArray();
    jsonGen.writeEndObject();
  }
}
