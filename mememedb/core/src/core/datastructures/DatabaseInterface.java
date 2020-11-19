package core.datastructures;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface DatabaseInterface {
  void newComment(String text, String owner, String postUUID);

  void newPost(String owner, String caption, File image) throws IOException;

  void newPost(String owner, String caption, String imageData);

  void newUser(String name, String nickname, String email, String password);

  Comment getComment(String commentUUID, String postUUID);

  Post getPost(String postUUID);

  User getUser(String nickname);

  Collection<User> getUsers();

  Map<String, User> getUserMap();

  Collection<Post> getPosts();

  Map<String, Post> getPostMap();

  List<Comment> getComments(String postUUID);

  User tryLogin(String username, String password);

  boolean usernameExists(String username);
}
