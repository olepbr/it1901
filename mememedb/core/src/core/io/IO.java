package core.io;

import core.datastructures.Database;
import java.io.File;
import java.io.IOException;


public interface IO {

  /**
   * stores image in database (or other storage location).
   *
   * @param image File referencing the image that should be saved.
   * @throws IOException If an error occurs during reading or writing of the file.
   */
  public void saveImage(File image) throws IOException;

  /**
   * gets reference to image given name.
   *
   * @param name The name of the image to search for.
   * @return A File object referencing the image.
   */
  public File getImageFromName(String name);

  /**
   * Gets a database of users.
   * 
   * @return A database containing currently stored users.
   */
  public Database getDatabase();

  /**
   * Writes the given database to file.
   * 
   * @param database The database to save;
   */
  public void saveDatabase(Database database);
}



