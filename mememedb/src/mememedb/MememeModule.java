package mememedb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

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
	}
	
	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new MememeModule());
		User user = new User(1, "Ole", "olepbr", "olebra@yolo.com");
		Post post = new Post(user, "haha", "files/yolo.png");
		user.addPost(post);
		try {
			System.out.println(mapper.writeValueAsString(user));
		} catch (JsonProcessingException e) {
			System.err.println("Fungerade inte!");
			e.printStackTrace();
		}
	}
}