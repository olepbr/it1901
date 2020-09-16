package mememedb;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mememedb.json.MememeModule;

public class LocalIO implements IO {
	
	private ObjectMapper mapper = new ObjectMapper();
	private File dataFile;
	private User user;

	public LocalIO() {
		mapper.registerModule(new MememeModule());
		String absPath = Paths.get("").toUri().getPath();
		if (! Pattern.matches(".*mememedb[/\\\\]*$", absPath))
			absPath += "mememedb/";
		absPath = absPath + "src/resources/data/user.json";
		dataFile = new File(absPath);
		try {
			user = mapper.readValue(dataFile, User.class);
		} catch (JsonParseException e) {
			user = new User();
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error reading file.");	
			e.printStackTrace();
			}	
	}
	
	public List<Post> getPostList() {
		return user.getPosts();
	}
	
	public void savePost(Post post) throws IOException {
		user.addPost(post);
		mapper.writeValue(dataFile, user);
	}
	
	public void saveImage(File image) throws IOException {
		String absPath = Paths.get("").toUri().getPath();
		if (! Pattern.matches(".*mememedb[/\\\\]*$", absPath))
			absPath += "mememedb/";
		absPath = absPath + "src/resources/img/" + image.getName();
		File file = new File(absPath);
		Files.copy(image.toPath(), file.toPath());
	}
	
	public File getImageFromName(String name) {
		//gets image given name, assumes images are stored in img under resources.
		String absPath = Paths.get("").toUri().getPath();
		if(!Pattern.matches(".*mememedb[/\\\\]*$", absPath)) {
			absPath+="mememedb/";
		}
		absPath=absPath + "src/resources/img/" + name;
		File image = new File(absPath);
		return image;
	}
	
	
	
}