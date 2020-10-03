package core.json;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import core.datastructures.User;

public class UserListSerializer extends JsonSerializer<List<User>> {

	/**
	 * Class for serializing a list of User objects 
	 * format: { "users": [ ... ] }
	 * 
	 * @author Ole Peder Brandtzæg
	 * 
	 */

	@Override
	public void serialize(List<User> users, JsonGenerator jsonGen, SerializerProvider serializerProvider) 
			throws IOException {
		jsonGen.writeStartObject();
		jsonGen.writeArrayFieldStart("users");
		for (User user : users) {
			jsonGen.writeObject(user);
		}
		jsonGen.writeEndArray();
		jsonGen.writeEndObject();
	}
}
