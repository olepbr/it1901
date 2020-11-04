package core.io;

import core.datastructures.Database;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LocalIOTest {

  @Test
  public void testLocalIO() {
    LocalIO testIO = new LocalIO();
    Database database = new Database(testIO);
    Assertions.assertNotNull(database);
  }
}
