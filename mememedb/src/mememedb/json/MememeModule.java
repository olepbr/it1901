package mememedb.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import mememedb.User;
import mememedb.Post;

public class MememeModule extends SimpleModule {
	/**
	 * Module collecting individual class serializers
	 * 
	 * @author Ole Peder Brandtzæg
	 * 
	 */

	private static final String NAME = "MememeModule";
	private static final VersionUtil VERSION_UTIL = new VersionUtil() {};

	public MememeModule() {
		super(NAME, VERSION_UTIL.version()); 
		addSerializer(Post.class, new PostSerializer());
		addSerializer(User.class, new UserSerializer());
		addDeserializer(Post.class, new PostDeserializer());
		addDeserializer(User.class, new UserDeserializer());
	}

}
