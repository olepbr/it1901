package core.io;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.datastructures.LocalDatabase;
import core.json.MememeModule;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/** IO implementation for local file system. */
public class LocalIO implements IO {

  private File userDir;
  private File userFile;
  private LocalDatabase database;
  private ObjectMapper mapper;

  /** Constructor. Attempts to intialize a database based on files in the 
   * Home folder. If no such files exist yet, they are populated using a basic 
   * template database. If all else fails, initializes with an empty database.
   */
  public LocalIO() {
    this.mapper = new ObjectMapper();
    mapper.registerModule(new MememeModule());
    Reader reader = null;
    userDir = Paths.get(System.getProperty("user.home"), "mememedb").toFile();
    String userFileName = "database.json";
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
      URL skeletonUrl = getClass().getResource("database.json");
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
        database = mapper.readValue(reader, LocalDatabase.class);
      } catch (JsonParseException e) {
        System.err.println("Error parsing file.");
        e.printStackTrace();
      } catch (IOException e) {
        System.err.println("Error reading file.");
        e.printStackTrace();
      } finally {
        try {
          reader.close();
        } catch (IOException e) {
          System.err.println("Error closing reader.");
          e.printStackTrace();
        }
      }
    } else {
      // Or just create an empty database
      database = new LocalDatabase();
    }

    // Write data to home folder
    if (database != null) {
      if (!userDir.isDirectory() && userDir.mkdir()) {
        saveDatabase(database);
      } else if (userDir.isDirectory()) {
        saveDatabase(database);
      }
    }
  }

  /**
   * Returns the database made during construction.
   *
   * @return The constructed database.
   */
  public LocalDatabase getDatabase() {
    return database;
  }

  /**
   * Writes serialized data to file.
   *
   * @param database Database object with data.
   */
  public void saveDatabase(LocalDatabase database) {
    FileWriter writer = null;
    try {
      writer = new FileWriter(userFile, StandardCharsets.UTF_8);
      writer.write(mapper.writeValueAsString(database));
    } catch (JsonProcessingException e) {
      System.err.println("Error processing data serialization");
      e.printStackTrace();
    } catch (IOException e) {
      System.err.println("Error writing to file");
      e.printStackTrace();
    } finally {
      if (writer != null) {
        try {
          writer.close();
        } catch (IOException e) {
          System.err.println("Error closing file");
          e.printStackTrace();
        }
      }
    }
  }
}
