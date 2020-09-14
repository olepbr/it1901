package mememedb;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class UserSerializer extends JsonSerializer<User> {

	/*
	 * Class for serializing User objects
	 * format: { "id": "...", "name": "...", "nickname": "...", "email": "...", "posts": [ ... ] }
	 * 
	 * @author Ole Peder Brandtzæg
	 * 
	*/

	@Override
	public void serialize(User user, JsonGenerator jGen, SerializerProvider serializerProvider) throws IOException {
		jGen.writeStartObject();
		jGen.writeNumberField("id", user.getId());
		jGen.writeStringField("name", user.getName());
		jGen.writeStringField("nickname", user.getNickname());
		jGen.writeStringField("email", user.getEmail());
		jGen.writeArrayFieldStart("posts");
		for (Post post : user.getPosts()) {
			jGen.writeObject(post);
		}
		jGen.writeEndArray();
		jGen.writeEndObject();
	}
}
