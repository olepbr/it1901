package mememedb.io;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import mememedb.datastructures.Post;
import mememedb.datastructures.User;
import mememedb.json.MememeModule;

public class LocalIO implements IO {

  private ObjectMapper mapper = new ObjectMapper();
  private File dataFile;
  private User user;

  /**
   * Initializes a LocalIO object.
   */
  public LocalIO() {
    mapper.registerModule(new MememeModule());
    String absPath = Paths.get("").toUri().getPath();
    if (!Pattern.matches(".*mememedb[/\\\\]*$", absPath)) {
      absPath += "mememedb/";
    }
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

  /**
   * Saves a specified image.
   * 
   * @param image The image to save.
   * @throws IOException If the file cannot be found.
   */
  public void saveImage(File image) throws IOException {
    String absPath = Paths.get("").toUri().getPath();
    if (!Pattern.matches(".*mememedb[/\\\\]*$", absPath)) {
      absPath += "mememedb/";
    }
    absPath = absPath + "src/resources/img/" + image.getName();
    File file = new File(absPath);
    Files.copy(image.toPath(), file.toPath());
  }

  /**
   * Gets an image from a string path.
   * 
   * @param name The path of the image.
   * @return The File object for the image.
   */
  public File getImageFromName(String name) {
    // gets image given name, assumes images are stored in img under resources.
    String absPath = Paths.get("").toUri().getPath();
    if (!Pattern.matches(".*mememedb[/\\\\]*$", absPath)) {
      absPath += "mememedb/";
    }
    absPath = absPath + "src/resources/img/" + name;
    File image = new File(absPath);
    return image;
  }



}
