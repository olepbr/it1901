package core.datastructures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;
import core.io.IO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DatabaseTest {

  List<User> testData =
      new ArrayList<User>(
          Arrays.asList(
              new User(1, "Bert Johnson", "berjon29", "bert@johnson.com"),
              new User(2, "Joe Mama", "jomama", "joe@mama.com")));
  

  /**
   * Returns a mock database, always returning an empty list. 
   * Used by mocked IO for passing content to database for testing purposes.
   * 
   * @return The mocked Database
   */
  public Database getEmptyMock(){
    Database mockDb = mock(Database.class);
    doReturn(new ArrayList<User>()).when(mockDb).getUsers();
    return mockDb;
  }

  /**
   * Returns a mock database, always returning the example testData. 
   * Used by mocked IO for passing content to database for testing purposes.
   * 
   * @return The mocked Database
   */
  public Database getFilledMock(){
    Database mockDb = mock(Database.class);
    doReturn(testData).when(mockDb).getUsers();
    return mockDb;
  }

  @Test
  public void TestConstructDatabase() {
    final IO mockIO = mock(IO.class);
    // test with empty dataset
    doReturn(getEmptyMock()).when(mockIO).getDatabase();
    final Database databaseEmpty = new Database(mockIO);
    assertEquals(
        databaseEmpty.getUsers(),
        new ArrayList<User>(),
        "Database is not empty with empty construction");

    // test with already created dataset
    doReturn(getFilledMock()).when(mockIO).getDatabase();
    final Database databaseFull = new Database(mockIO);
    assertEquals(
        testData,
        databaseFull.getUsers(),
        "Database does not retain data structure when constructing");
  }

  @Test
  public void TestNewUser() {
    final IO mockIO = mock(IO.class);
    doReturn(getEmptyMock()).when(mockIO).getDatabase();
    final Database database = new Database(mockIO);
    for (User user : testData) {
      database.saveUser(user);
    }
    assertEquals(database.getUsers(), testData, "Bug in user addition");
    verify(mockIO, times(testData.size())).saveDatabase(database);;
  }

  @Test
  public void TestPost() {
    final IO mockIO = mock(IO.class);
    doReturn(getFilledMock()).when(mockIO).getDatabase();
    Database database = new Database(mockIO);
    assertEquals(
        new ArrayList<Post>(),
        database.getPostList(),
        "Database returns non-empty post list when there are no posts");
    Post post1 = new Post("berjon29", "Hello", "hello.png");
    Post post2 = new Post("jomama", "YO MAMA", "sofat.png");
    Post post3 = new Post("jomama", "YO 1231232MAMAs", "sofat1223.png");
    try {
      database.savePost(post1, testData.get(0));
      database.savePost(post2, testData.get(1));
      database.savePost(post3, testData.get(1));
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unknown error when saving posts");
    }
    assertEquals(
        new ArrayList<Post>(Arrays.asList(post1, post2, post3)),
        database.getPostList(),
        "Database does not return posts after addition");
  }

  @Test
  public void TestIdAssigner() {
    final IO mockIO = mock(IO.class);
    doReturn(getEmptyMock()).when(mockIO).getDatabase();
    Database database = new Database(mockIO);
    assertEquals(
        1, database.getNewId(), "Error in id assignment, expected 1, got " + database.getNewId());
    doReturn(getFilledMock()).when(mockIO).getDatabase();
    Database database2 = new Database(mockIO);
    assertEquals(
        3, database2.getNewId(), "Error in id assignment, expected 3, got " + database.getNewId());
    assertEquals(
        3, database2.getNewId(), "Error in id assignment, expected 3, got " + database.getNewId());
  }

  @Test
  public void TestUsernameExists() {
    final IO mockIO = mock(IO.class);
    doReturn(getFilledMock()).when(mockIO).getDatabase();
    Database database = new Database(mockIO);
    assertEquals(false, database.usernameExists("notARealUser"), 
        "Error in username availability, username should not exist yet");
    assertEquals(true, database.usernameExists("berjon29"), 
        "Error in username availability, username should already exist");
  }

  @Test
  public void TestLogin() {
    final IO mockIO = mock(IO.class);
    testData.get(0).setPassword("asafepassword");
    doReturn(getFilledMock()).when(mockIO).getDatabase();
    Database database = new Database(mockIO);
    assertEquals(testData.get(0), database.tryLogin("berjon29", "asafepassword"), 
        "Error in login method, should return User");
    assertEquals(null, database.tryLogin("berjon29", "awrongpassword"), 
        "Error in login method, should fail with incorrect input");
  }
}
