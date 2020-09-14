package mememedb.json;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import mememedb.Post;

public class PostSerializer extends JsonSerializer<Post> {

	/**
	 * Class for serializing Post objects
	 * format: { "owner": "...", "caption": "...", "image": "..." }
	 * 
	 * @author Ole Peder Brandtzæg
	 * 
	*/

	@Override
	public void serialize(Post post, JsonGenerator jGen, SerializerProvider serializerProvider) throws IOException {
		jGen.writeStartObject();
		jGen.writeStringField("owner", post.getOwner().getNickname());
		jGen.writeStringField("caption", post.getText());
		jGen.writeStringField("image", post.getImage());
		jGen.writeEndObject();
	}
}
