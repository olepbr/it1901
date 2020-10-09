package core.io;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.datastructures.Database;
import core.json.MememeModule;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/** IO implementation for local file system. */
public class LocalIO implements IO {

  private ObjectMapper mapper;
  private File userDir;
  private File userFile;
  private Database database;
  private File imageDir;

  /**
   * Constructor. Initializes database object from file or skeleton. Also checks for image folder
   * and copies images from resources.
   */
  public LocalIO() {
    // Create object for json-parsing
    mapper = new ObjectMapper();
    mapper.registerModule(new MememeModule());

    Reader reader = null;
    userDir = Paths.get(System.getProperty("user.home"), "mememedb").toFile();
    imageDir = new File(userDir.getAbsolutePath() + "/images");
    String userFileName = "user.json";
    userFile = new File(userDir.getAbsolutePath() + "/" + userFileName);

    // Try reading data from home folder
    if (userFile.exists() && userFile.isFile()) {
      try {
        reader = new FileReader(userFile, StandardCharsets.UTF_8);
      } catch (IOException e) {
        System.err.println("Could not read user.json from home folder.");
        e.printStackTrace();
      }
    } else {
      // If not, try reading from skeleton in resources
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

    // Start with null database
    database = null;

    // Try reading data from file
    if (reader != null) {
      try {
        database = MememeModule.deserializeDatabase(reader);
      } catch (JsonParseException e) {
        System.out.println("Error parsing file.");
        e.printStackTrace();
      } catch (IOException e) {
        System.out.println("Error reading file.");
        e.printStackTrace();
      } finally {
        try {
          reader.close();
        } catch (IOException e) {
          // ???
        }
      }
    }
    // Or just create an empty database
    else {
      database = new Database();
    }

    // Write data to home folder
    try {
      if (!userDir.isDirectory() && userDir.mkdir()) {
        mapper.writeValue(userFile, database);
      }
    } catch (IOException e) {
      System.err.println("Error writing file");
      e.printStackTrace();
    }

    // Check image folder
    if (!(imageDir.exists() && imageDir.isDirectory()) && imageDir.mkdir()) {
      // Find built in example memes
      File resourceImageDir = new File(getClass().getResource("/img").getFile());
      System.out.println(resourceImageDir.getPath());
      // List with files in img directory
      File[] resourceImages = resourceImageDir.listFiles();
      if (resourceImages != null) {
        // Iterate trough images
        for (File image : resourceImages) {
          File file = new File(imageDir.getAbsolutePath() + "/" + image.getName());
          try {
            Files.copy(image.toPath(), file.toPath());
          } catch (IOException e) {
            System.err.println("IO error");
            e.printStackTrace();
          }
        }
      }
    }
  }

  /**
   * Saves a specified image.
   *
   * @param image The image to save.
   * @throws IOException If the file cannot be found.
   */
  public void saveImage(File image) throws IOException {
    // Create directory if it does not exist
    System.out.println(imageDir.isDirectory());
    if (imageDir.isDirectory() || imageDir.mkdir()) {
      String absPath = imageDir.getAbsolutePath() + "/" + image.getName();
      File file = new File(absPath);
      if (!file.exists()) {
        Files.copy(image.toPath(), file.toPath());
      }
    }
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

  public Database getDatabase() {
    return database;
  }

  /**
   * Writes serialized data to file.
   *
   * @param database Database object with data.
   */
  public void saveDatabase(Database database) {
    FileWriter writer = null;
    try {
      writer = new FileWriter(userFile, StandardCharsets.UTF_8);
      writer.write(MememeModule.serializeDatabase(database));
    } catch (JsonProcessingException e) {
      System.out.println("Error processing data serialization");
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("Error writing to file");
      e.printStackTrace();
    } finally {
      if (writer != null) {
        try {
          writer.close();
        } catch (IOException e) {
          System.out.println("Error closing file");
          e.printStackTrace();
        }
      }
    }
  }
}
