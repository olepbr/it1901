package core.datastructures;

import core.io.LocalIO;

public class DatabaseFactory {

  /**
   * Returns a specific type of database by reading the given String.
   *
   * @param databaseType A String corresponding to the wanted type of database.
   * @return 
   *     <ul>
   *     <li>A RestDatabase if the given String is "rest"</li>
   *     <li>A LocalDatabase that reads and writes to file if given String is "io"</li>
   *     <li>An empty LocalDatabase without persistence if given any other String</li>
   *     </ul>
   */
  public DatabaseInterface getDatabase(String databaseType) {
    if (databaseType.equalsIgnoreCase("rest")) {
      return new RestDatabase("http://localhost:8000");
    } else if (databaseType.equalsIgnoreCase("local")) {
      return new LocalDatabase(new LocalIO());
    } else {
      return new LocalDatabase();
    }
  }
}