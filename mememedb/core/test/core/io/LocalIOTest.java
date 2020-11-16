package core.io;

<<<<<<< HEAD
import core.datastructures.Database;
=======
import core.datastructures.LocalDatabase;

>>>>>>> 30-restructure-datastructures
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
