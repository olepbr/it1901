package core.io;

import core.databases.LocalDatabase;

/** Interface for handling Input/Output. */
public interface IO {

  /**
   * Gets a database of users.
   *
   * @return A database containing currently stored users.
   */
  public LocalDatabase getDatabase();

  /**
   * Writes the given database to file.
   *
   * @param database The database to save;
   */
  public void saveDatabase(LocalDatabase database);
}
