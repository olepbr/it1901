package core.datastructures;

public class DatabaseFactory {

  public DatabaseInterface getDatabase(String databaseType) {
    if (databaseType.equalsIgnoreCase("rest"))
      return new RestDatabase();
    else if (databaseType.equalsIgnoreCase("local")) {
      return new LocalDatabase();
    }
    else {
      return null;
    }
  }
}