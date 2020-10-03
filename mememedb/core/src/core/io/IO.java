package core.io;

import java.io.File;
import java.io.IOException;
import java.util.List;

import core.datastructures.Post;
import core.datastructures.User;


public interface IO {

  /**
   * stores image in database (or other storage location).
   *
   * @param image File referencing the image that should be saved.
   * @throws IOException If an error occurs during reading or writing of the file.
   */
  public void saveImage(File image) throws IOException;

  /**
   * gets reference to image given name.
   *
   * @param name The name of the image to search for.
   * @return A File object referencing the image.
   */
  public File getImageFromName(String name);

  /**
   * Gets a List containing all users in the app
   * 
   * @return A List containing all currently stored users
   */
  public List<User> getUserList();

  /**
   * Writes the given userlist to file.
   * 
   * @param userlist The List to save;
   */
  public void save(List<User> userlist);
}



