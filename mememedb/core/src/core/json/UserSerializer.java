package core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.datastructures.User;
import java.io.IOException;

/**
 * Class for serializing User objects format: { "id": "...", "name": "...", "nickname": "...",
 * "email": "...", "password": "...", "posts": [ ... ] }
 *
 * @author Ole Peder Brandtzæg
 */
class UserSerializer extends JsonSerializer<User> {

  @Override
  public void serialize(User user, JsonGenerator jsonGen, SerializerProvider serializerProvider)
      throws IOException {
    jsonGen.writeStartObject();
    jsonGen.writeStringField("name", user.getName());
    jsonGen.writeStringField("nickname", user.getNickname());
    jsonGen.writeStringField("email", user.getEmail());
    jsonGen.writeStringField("password", user.getPassword());
    jsonGen.writeArrayFieldStart("posts");
    for (String post : user.getPosts()) {
      jsonGen.writeObject(post);
    }
    jsonGen.writeEndArray();
    jsonGen.writeEndObject();
  }
}
