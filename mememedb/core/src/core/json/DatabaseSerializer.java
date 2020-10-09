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
   * [TODO:description]
   *
   * @param database [TODO:description]
   * @param jsonGen [TODO:description]
   * @param serializerProvider [TODO:description]
   * @throws IOException [TODO:description]
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
