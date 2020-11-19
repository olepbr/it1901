package core.datastructures;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * The interface to the database used by the ui
 */
public interface DatabaseInterface {




  /**
   * Creates a new Comment in the database, using the supplied information.
   * Automatically updates storage.
   *
   * @param text The text belonging to the comment.
   * @param owner The nickname of the user that made the comment.
   * @param postUUID The UUID of the post that the comment belongs to.
   */
  void newComment(String text, String owner, String postUUID);

  /**
   * Creates a new Post in the database from the given arguments. Automatically updates storage.
   *
   * @param owner The user that made the post
   * @param caption The caption belonging to the post
   * @param image A file referencing the image belonging to the post
   */
  void newPost(String owner, String caption, File image) throws IOException;

  /**
   * Creates a new Post in the database from the given arguments. Automatically updates storage.
   *
   * @param owner The user that made the post
   * @param caption The caption belonging to the post
   * @param imageData The base64 data containing the imgae of the post
   */
  void newPost(String owner, String caption, String imageData);

  /**
   * Creates a new User in the database, unless the user already exists.
   * Automatically updates storage.
   *
   * @param name     The name of the user.
   * @param nickname The nickname of the user.
   * @param email    The email of the user.
   * @param password The password of the user.
   * @throws IllegalStateException In the case of the nickname already existing in
   *                               the database.
   */
  void newUser(String name, String nickname, String email, String password);

  /**
   * Fetches the comment with the given UUID from the given post.
   *
   * @param commentUUID The uuid of the comment
   * @param postUUID The uuid of the post the comment belongs to
   * @return The comment object with the given uuid
   */
  Comment getComment(String commentUUID, String postUUID);

  /**
   * Fetches a single post.
   *
   * @param postUUID the UUID of the post to fetch
   * @return the Post object with the given UUID
   */
  Post getPost(String postUUID);

  /**
   * Fetches a single user.
   *
   * @param nickname The nickname of the user.
   * @return The user object corresponding to the user with the given nickname.
   */
  User getUser(String nickname);

  /**
   * Fetches a collection of all users in the database.
   *
   * @return returns a collection of all users
   */
  Collection<User> getUsers();

  /**
   * Fetches a list of all posts in the database.
   *
   * @return returns a list of all posts
   */
  Collection<Post> getPosts();

  /** 
   * Fetches the comments of a given post, sorted by date 
   *
   * @param postUUID the UUID of the post to fetch comments from
   */
  List<Comment> getComments(String postUUID);

  /**
   * Attempts to find a user in the database with the given information.
   *
   * @param nickname Nickname or email of the user
   * @param password The password of the User
   * @return User that logged on if it exists, null if no such user exists
   */
  User tryLogin(String nickname, String password);
    
  /**
   * Checks if the input nickname already exists in the database.
   *
   * @param nickname The nickname to check
   * @return true if the nickname exists in the database
   */
  boolean nicknameExists(String nickname);
}
