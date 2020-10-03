package core.io;

import java.io.File;
import java.io.IOException;
import java.util.List;

import core.datastructures.Post;


public interface IO {

  /**
   * returns list of posts in database
   *
   * @return [TODO:description]
   */
  public List<Post> getPostList();

  /**
   * stores post in database
   *
   * @param post [TODO:description]
   * @throws IOException [TODO:description]
   */
  public void savePost(Post post) throws IOException;

  /**
   * stores image in database (or other storage location)
   *
   * @param image [TODO:description]
   * @throws IOException [TODO:description]
   */
  public void saveImage(File image) throws IOException;

  /**
   * gets reference to image given name
   *
   * @param name [TODO:description]
   * @return [TODO:description]
   */
  public File getImageFromName(String name);
}
