package core.json;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.datastructures.Database;
import core.datastructures.User;

class DatabaseSerializer extends JsonSerializer<Database> {

	/**
	 * Class for serializing a database of users 
	 * format: { "users": [ ... ] }
	 * 
	 * @author Ole Peder Brandtzæg
	 * 
	 */

	@Override
	public void serialize(Database database, JsonGenerator jsonGen, SerializerProvider serializerProvider) 
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
