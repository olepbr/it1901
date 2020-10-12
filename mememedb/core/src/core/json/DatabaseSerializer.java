package core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.datastructures.Database;
import core.datastructures.User;
import java.io.IOException;

/**
 * Class for serializing a database of users format: { "users": [ ... ] }
 *
 * @author Ole Peder Brandtzæg
 */
class DatabaseSerializer extends JsonSerializer<Database> {

  /**
   * Class for serializing a database into a JSON object.
   * Format: {"users": [ ... ] }
   *
   * @param database The database object to serialize.
   * @param jsonGen The JsonGenerator object writing the JSON (provided by Jackson).
   * @param serializerProvider The SerializerProvider object obtaining the necessary serializers (also provided by Jackson).
   * @throws IOException In the event that the JsonGenerator encounters an error in writing the JSON object.
   */
  @Override
  public void serialize(
      Database database, JsonGenerator jsonGen, SerializerProvider serializerProvider)
      throws IOException {
    jsonGen.writeStartObject();
    jsonGen.writeArrayFieldStart("users");
    for (User user : database.getUsers()) {
      jsonGen.writeObject(user);
    }
    jsonGen.writeEndArray();
    jsonGen.writeEndObject();
  }
}
