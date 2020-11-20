package core.datastructures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DatabaseFactoryTest {
  @Test
  public void TestGetEmptyDatabase(){
    DatabaseInterface db = new DatabaseFactory().getDatabase("asdfuogfs");
    assertTrue(db instanceof LocalDatabase);
    assertEquals(0, db.getPosts().size(),"Error: returned database is not empty");
    assertEquals(0, db.getUsers().size(),"Error: returned database is not empty");
  }

  @Test
  public void TestGetIODatabase(){
    DatabaseInterface db = new DatabaseFactory().getDatabase("io");
    assertTrue(db instanceof LocalDatabase);
  }

  @Test
  public void TestGetRestDatabase(){
    DatabaseInterface db = new DatabaseFactory().getDatabase("rest");
    assertTrue(db instanceof RestDatabase);
  }
}