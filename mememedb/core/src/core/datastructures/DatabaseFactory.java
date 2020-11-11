package core.datastructures;

import core.io.LocalIO;

public class DatabaseFactory {

  public DatabaseInterface getDatabase(String databaseType) {
    if (databaseType.equalsIgnoreCase("rest")) {
        return new RestDatabase("http://localhost:8000");
    }
    else if (databaseType.equalsIgnoreCase("local")) {
      return new LocalDatabase(new LocalIO());
    }
    else {
      return new LocalDatabase();
    }
  }
}