package it1901.mememedb.core.io;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it1901.mememedb.core.datastructures.Post;
import it1901.mememedb.core.datastructures.User;
import it1901.mememedb.core.json.MememeModule;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/** IO implementation for local file system */
public class LocalIO implements IO {

  private ObjectMapper mapper;
  private File userDir;
  private File userFile;
  private User user;
  private File imageDir;

  /** Constructor. Initializes user object from file or skeleton. */
  public LocalIO() {
    // Create object for json-parsing
    mapper = new ObjectMapper();
    mapper.registerModule(new MememeModule());

    Reader reader = null;
    userDir = Paths.get(System.getProperty("user.home"), "mememedb").toFile();
    String userFileName = "user.json";
    userFile = new File(userDir.getAbsolutePath() + "/" + userFileName);

    imageDir = new File(userDir.getAbsolutePath() + "/images");

    // Try reading user data from home folder
    if (userFile.exists() && userFile.isFile()) {
      try {
        reader = new FileReader(userFile, StandardCharsets.UTF_8);
      } catch (IOException e) {
        System.err.println("Could not read user.json from home folder.");
        e.printStackTrace();
      }
    }
    // If not, try reading from skeleton in resources
    else {
      URL skeletonUrl = getClass().getResource("/data/user.json");
      try {
        reader = new InputStreamReader(skeletonUrl.openStream(), StandardCharsets.UTF_8);
      } catch (IOException e) {
        System.err.println("Could not read skeleton user.json from resources");
        e.printStackTrace();
      } catch (NullPointerException e) {
        System.err.println("Could not read skeleton user.json from resources");
        e.printStackTrace();
      }
    }

    // Start with null user
    user = null;

    // Try reading from file
    if (reader != null) {
      try {
        user = MememeModule.deserializeUser(reader);
      } catch (JsonParseException e) {
        System.out.println("Error parsing file.");
        e.printStackTrace();
      } catch (IOException e) {
        System.out.println("Error reading file.");
        e.printStackTrace();
      } finally {
        if (reader != null) {
          try {
            reader.close();
          } catch (IOException e) {
            // ???
          }
        }
      }
    }
    // Or just create an empty user
    else {
      user = new User();
    }

    // Write to home folder
    try {
      if (!userDir.isDirectory()) {
        userDir.mkdir();
      }
      mapper.writeValue(userFile, user);
    } catch (IOException e) {
      System.err.println("Error writing file");
      e.printStackTrace();
    }
  }

  /**
   * Wrapper to get posts from user
   *
   * @return A list of posts
   */
  public List<Post> getPostList() {
    return user.getPosts();
  }

  /**
   * Adds post object to user object and saves to file
   *
   * @param post The post object to save
   * @throws IOException [TODO:description]
   */
  public void savePost(Post post) throws IOException {
    user.addPost(post);
    mapper.writeValue(userFile, user);
  }

  /**
   * Saves a specified image.
   *
   * @param image The image to save.
   * @throws IOException If the file cannot be found.
   */
  public void saveImage(File image) throws IOException {
    // Create directory if it does not exist
    if (!imageDir.isDirectory()) {
      imageDir.mkdir();
    }

    String absPath = imageDir.getAbsolutePath() + "/" + image.getName();
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
    String absPath = imageDir.getAbsolutePath() + "/" + name;
    File image = new File(absPath);
    return image;
  }
}
