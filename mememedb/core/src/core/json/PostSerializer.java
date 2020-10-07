package core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.datastructures.Post;
import java.io.IOException;

class PostSerializer extends JsonSerializer<Post> {

  /**
   * Class for serializing Post objects format: { "owner": "...", "caption": "...", "image": "..." }
   * 
   * @author Ole Peder Brandtzæg
   * 
   */

  @Override
  public void serialize(Post post, JsonGenerator jsonGen, SerializerProvider serializerProvider) 
      throws IOException {
    jsonGen.writeStartObject();
    jsonGen.writeStringField("owner", post.getOwner());
    jsonGen.writeStringField("caption", post.getText());
    jsonGen.writeStringField("image", post.getImage());
    jsonGen.writeEndObject();
  }
}
