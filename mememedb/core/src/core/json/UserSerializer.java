package core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

import core.datastructures.Post;
import core.datastructures.User;

class UserSerializer extends JsonSerializer<User> {

  /**
   * Class for serializing User objects format: { "id": "...", "name": "...", "nickname": "...",
   * "email": "...", "password": "...", "posts": [ ... ] }
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
    jsonGen.writeStringField("password", user.getPassword());
    jsonGen.writeArrayFieldStart("posts");
    for (Post post : user.getPosts()) {
      jsonGen.writeObject(post);
    }
    jsonGen.writeEndArray();
    jsonGen.writeEndObject();
  }
}
