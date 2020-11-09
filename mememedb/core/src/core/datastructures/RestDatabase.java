package core.datastructures;

import java.io.File;
import java.util.Collection;
import java.util.Map;

public class RestDatabase implements DatabaseInterface {

  @Override
  public void newComment(String text, String owner, String postUUID) {
    // TODO Auto-generated method stub

  }

  @Override
  public void newPost(String owner, String caption, File image) {
    // TODO Auto-generated method stub

  }

  @Override
  public void newUser(String name, String nickname, String email, String password) {
    // TODO Auto-generated method stub

  }

  @Override
  public Collection<User> getUsers() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<String, User> getUserMap() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<Post> getPosts() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<String, Post> getPostMap() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public User tryLogin(String username, String password) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean usernameExists(String username) {
    // TODO Auto-generated method stub
    return false;
  }

}