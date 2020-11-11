package core.datastructures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;
import core.io.IO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class DatabaseTest {

  List<User> testData = new ArrayList<User>(Arrays.asList(
      new User("Bert Johnson", "berjon29", "bert@johnson.com", "asafepassword"), new User("Joe Mama", "jomama", "joe@mama.com", "anotherpassword")));

  /**
   * Returns a mock database, always returning an empty list. Used by mocked IO
   * for passing content to database for testing purposes.
   * 
   * @return The mocked LocalDatabase
   */
  public LocalDatabase getEmptyMock() {
    LocalDatabase mockDb = mock(LocalDatabase.class);
    doReturn(new HashMap<String, User>()).when(mockDb).getUserMap();
    return mockDb;
  }

  /**
   * Returns a mock database, always returning the example testData. Used by
   * mocked IO for passing content to database for testing purposes.
   * 
   * @return The mocked LocalDatabase
   */
  public LocalDatabase getFilledMock() {
    Map<String, User> returnmap = new HashMap<String, User>();
    for (User testUser : testData) {
      returnmap.put(testUser.getNickname(), testUser);
    }
    LocalDatabase mockDb = mock(LocalDatabase.class);
    doReturn(returnmap).when(mockDb).getUserMap();
    return mockDb;
  }

  @Test
  public void TestConstructDatabase() {
    final IO mockIO = mock(IO.class);
    // test with empty dataset
    doReturn(getEmptyMock()).when(mockIO).getDatabase();
    final LocalDatabase databaseEmpty = new LocalDatabase(mockIO);
    assertEquals(databaseEmpty.getUsers().size(), 0, "LocalDatabase is not empty with empty construction");

    // test with already created dataset
    doReturn(getFilledMock()).when(mockIO).getDatabase();
    final LocalDatabase databaseFull = new LocalDatabase(mockIO);
    assertEquals(testData.size(), databaseFull.getUsers().size(), "LocalDatabase does not retain users when constructing");
    for (User user : testData) {
      assertTrue(databaseFull.getUsers().contains(user), "LocalDatabase does not retain users when constructing");
    }
  }

  @Test
  public void TestNewUser() {
    final IO mockIO = mock(IO.class);
    doReturn(getEmptyMock()).when(mockIO).getDatabase();
    final LocalDatabase database = new LocalDatabase(mockIO);
    for (User user : testData) {
      database.addUser(user);
    }
    for (User user : testData) {
      assertTrue(database.getUsers().contains(user),
          "Error in user addition");
    }
    verify(mockIO, times(testData.size())).saveDatabase(database);
    ;
  }

  @Test
  public void TestPost() {
    final IO mockIO = mock(IO.class);
    doReturn(getFilledMock()).when(mockIO).getDatabase();
    LocalDatabase database = new LocalDatabase(mockIO);
    assertEquals(0, database.getPosts().size(),
        "LocalDatabase returns non-empty post list when there are no posts");
    Post post1 = new Post("berjon29", "Hello", "hello.png");
    Post post2 = new Post("jomama", "YO MAMA", "sofat.png");
    Post post3 = new Post("jomama", "YO 1231232MAMAs", "sofat1223.png");
    try {
      database.addPost(post1);
      database.addPost(post2);
      database.addPost(post3);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unknown error when saving posts");
    }
    for(Post post : Arrays.asList(post1, post2, post3)) {
      assertTrue(database.getPosts().contains(post),
          "Error in post addition");
    }
  }

  @Test
  public void TestNewComment() {
    final IO mockIO = mock(IO.class);
    doReturn(getFilledMock()).when(mockIO).getDatabase();
    LocalDatabase database = new LocalDatabase(mockIO);
    Post post = new Post("berjon29", "A funi image", "imagedata");
    database.addPost(post);
    database.newComment("haha, funi", "berjon29", post.getUUID());
    Comment comment = new Comment("berjon29", "haha, funi");
    for(Comment commment : database.getPost(post.getUUID()).getComments()){
      assertEquals(commment.getAuthor(), comment.getAuthor());
      assertEquals(commment.getText(), comment.getText());
    }

  }

  @Test
  public void TestUsernameExists() {
    final IO mockIO = mock(IO.class);
    doReturn(getFilledMock()).when(mockIO).getDatabase();
    LocalDatabase database = new LocalDatabase(mockIO);
    assertEquals(false, database.usernameExists("notARealUser"),
        "Error in username availability, username should not exist yet");
    assertEquals(true, database.usernameExists("berjon29"),
        "Error in username availability, username should already exist");
  }

  @Test
  public void TestLogin() {
    final IO mockIO = mock(IO.class);
    doReturn(getFilledMock()).when(mockIO).getDatabase();
    LocalDatabase database = new LocalDatabase(mockIO);
    System.out.println(database);
    System.out.println(User.hashPassword("asafepassword"));
    assertEquals(testData.get(0), database.tryLogin(testData.get(0).getNickname(), "asafepassword"), "Error in login method, should return User");
    assertEquals(null, database.tryLogin(testData.get(0).getNickname(), "awrongpassword"),
        "Error in login method, should fail with incorrect input");
  }
}
