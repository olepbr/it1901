package core.io;

import core.databases.LocalDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LocalIOTest {

  @Test
  public void testLocalIO() {
    LocalIO testIO = new LocalIO();
    LocalDatabase database = new LocalDatabase(testIO);
    Assertions.assertNotNull(database);
  }
}
