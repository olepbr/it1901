package core.io;

import core.datastructures.Database;
import java.io.File;
import java.io.IOException;

/** Interface for handling Input/Output. */
public interface IO {

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
