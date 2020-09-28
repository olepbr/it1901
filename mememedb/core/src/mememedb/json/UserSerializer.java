package mememedb.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import mememedb.datastructures.Post;
import mememedb.datastructures.User;

public class UserSerializer extends JsonSerializer<User> {

  /**
   * Class for serializing User objects format: { "id": "...", "name": "...", "nickname": "...",
   * "email": "...", "posts": [ ... ] }
   * 
   * @author Ole Peder Brandtzæg
   * 
   */

  @Override
  public void serialize(User user, JsonGenerator jsonGen, SerializerProvider serializerProvider) 
      throws IOException {
    jsonGen.writeStartObject();
    jsonGen.writeNumberField("id", user.getId());
    jsonGen.writeStringField("name", user.getName());
    jsonGen.writeStringField("nickname", user.getNickname());
    jsonGen.writeStringField("email", user.getEmail());
    jsonGen.writeArrayFieldStart("posts");
    for (Post post : user.getPosts()) {
      jsonGen.writeObject(post);
    }
    jsonGen.writeEndArray();
    jsonGen.writeEndObject();
  }
}
