package it1901.mememedb.core.io;

import java.io.File;
import java.io.IOException;
import java.util.List;

import it1901.mememedb.core.datastructures.Post;

public interface IO {

  // return list of posts in database
  public List<Post> getPostList();

  // store post in database
  public void savePost(Post post) throws IOException;

  // store image in database (or other storage location)
  public void saveImage(File image) throws IOException;

  // get reference to image given name
  public File getImageFromName(String name);
}
