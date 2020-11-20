package core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.datastructures.LocalDatabase;
import core.datastructures.Post;
import core.datastructures.User;
import java.io.IOException;

/**
 * Class for serializing a database of users format: { "users": [ ... ], "posts": [...] }
 *
 * @author Ole Peder Brandtzæg
 */
class DatabaseSerializer extends JsonSerializer<LocalDatabase> {

  /**
   * Class for serializing a database into a JSON object. 
   * Format: { "users": [ ... ], "posts": [...] }
   *
   * @param database The database object to serialize.
   * @param jsonGen The JsonGenerator object writing the JSON (provided by Jackson).
   * @param serializerProvider The SerializerProvider object obtaining the necessary serializers
   *     (also provided by Jackson).
   * @throws IOException In the event that the JsonGenerator encounters an error in writing the JSON
   *     object.
   */
  @Override
  public void serialize(
      LocalDatabase database, JsonGenerator jsonGen, SerializerProvider serializerProvider)
      throws IOException {
    jsonGen.writeStartObject();
    jsonGen.writeArrayFieldStart("users");
    for (User user : database.getUsers()) {
      jsonGen.writeObject(user);
    }
    jsonGen.writeEndArray();
    jsonGen.writeArrayFieldStart("posts");
    for (Post post : database.getPosts()) {
      jsonGen.writeObject(post);
    }
    jsonGen.writeEndArray();
    jsonGen.writeEndObject();
  }
}
