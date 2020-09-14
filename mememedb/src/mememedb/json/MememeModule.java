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
	
	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new MememeModule());
		User user = new User(1, "Ole", "olepbr", "olebra@yolo.com");
		Post post = new Post(user.getNickname(), "haha", "files/yolo.png");
		user.addPost(post);
		try {
			String json = mapper.writeValueAsString(user);
			System.out.println(json);
			User user2 = mapper.readValue(json, User.class);
			System.out.println(user2);
			for (Post post2 : user2.getPosts()) {
				System.out.println(post2);
			}
		} catch (JsonProcessingException e) {
			System.err.println("Fungerade inte!");
			e.printStackTrace();
		}
	}
}