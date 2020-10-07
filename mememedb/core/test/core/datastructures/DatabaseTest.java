package core.datastructures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import core.io.IO;

public class DatabaseTest {

  
  List<User> testData = new ArrayList<User>(
    Arrays.asList(new User(1, "Bert Johnson", "berjon29", "bert@johnson.com"), new User(2, "Joe Mama", "jomama", "joe@mama.com")));




  @Test
  public void TestConstructDatabase() {
    final IO mockIO = mock(IO.class);
    //test with empty dataset
    doReturn(new ArrayList<User>()).when(mockIO).getUserList();
    final Database databaseEmpty = new Database(mockIO);
    assertEquals(databaseEmpty.getUsers(), new ArrayList<User>(), "Database is not empty with empty construction");
    
    //test with already created dataset
    doReturn(testData).when(mockIO).getUserList();
    final Database databaseFull = new Database(mockIO);
    assertEquals(testData, databaseFull.getUsers(), "Database does not retain data structure when constructing");
  }

  @Test
  public void TestNewUser() {
    final IO mockIO = mock(IO.class);
    doReturn(new ArrayList<User>()).when(mockIO).getUserList();
    final Database database = new Database(mockIO);
    for (User user : testData) {
      database.saveUser(user);
    }
    assertEquals(database.getUsers(), testData, "Bug in user addition");
    verify(mockIO, times(testData.size())).save(anyList());
  }

  @Test
  public void TestPost() {
    final IO mockIO = mock(IO.class);
    doReturn(testData).when(mockIO).getUserList();
    Database database = new Database(mockIO);
    assertEquals(new ArrayList<Post>(), database.getPostList(), "Database returns non-empty post list when there are no posts");
    Post post1 = new Post("berjon29", "Hello", "hello.png");
    Post post2 = new Post("jomama", "YO MAMA", "sofat.png");
    Post post3 = new Post("jomama", "YO 1231232MAMAs", "sofat1223.png");
    try {
      database.savePost(post1, new File("C:/"), testData.get(0));
      database.savePost(post2, new File("C:/"), testData.get(1));
      database.savePost(post3, new File("C:/"), testData.get(1));
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unknown error when saving posts");
    }
    assertEquals(new ArrayList<Post>(Arrays.asList(post1, post2, post3)), database.getPostList(), "Database does not return posts after addition");
  }

  @Test
  public void TestIdAssigner() {
    final IO mockIO = mock(IO.class);
    Database database = new Database(mockIO);
    doReturn(testData).when(mockIO).getUserList();
    assertEquals(1, database.getNewID(), "Error in id assignment, expected 1, got " + database.getNewID());
    database.reload();
    assertEquals(3, database.getNewID(), "Error in id assignment, expected 3, got " + database.getNewID());
  }


  

}