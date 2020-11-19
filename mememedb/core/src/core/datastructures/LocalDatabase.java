package core.datastructures;

import core.io.IO;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/** Class that represents all the data in the application. */
public class LocalDatabase implements DatabaseInterface {
  /*
   * Maps are used, as the ordering of objects is less important compared to the
   * connection between the name/id and the object itself.
   */
  private Map<String, User> users;
  private Map<String, Post> posts;

  private IO storage;

  /**
   * Generates a new empty database object. LocalDatabase contains all Users and
   * Posts of the app.
   */
  public LocalDatabase() {
    users = new HashMap<String, User>();
    posts = new HashMap<String, Post>();
  }

  /**
   * Generates a new database object, Initialized with data from the given IO
   * Stores database changes using the IO.
   */
  public LocalDatabase(IO io) {
    storage = io;
    users = io.getDatabase().getUserMap();
    posts = io.getDatabase().getPostMap();
  }

  /** Saves cached database, overwriting previous data in storage. */
  public void saveToStorage() {
    if (storage != null) {
      storage.saveDatabase(this);
    }
  }

  /**
   * Creates a new Comment in the database, using the supplied information.
   * Automatically updates storage.
   *
   * @param text The text belonging to the comment.
   * @param owner The username of the user that made the comment.
   * @param postUUID The UUID of the post that the comment belongs to.
   */
  public void newComment(String text, String owner, String postUUID) {
    Post post = posts.getOrDefault(postUUID, null);
    if (post != null) {
      post.addComment(new Comment(owner, text));
      saveToStorage();
    }
  }

  /**
   * Creates a new Post in the database from the given arguments. Automatically updates storage.
   *
   * @param owner The user that made the post
   * @param caption The caption belonging to the post
   * @param image A file referencing the image belonging to the post
   */
  public void newPost(String owner, String caption, File image) {
    try {
      Post post = new Post(owner, caption, image);
      posts.put(post.getUUID(), post);
      saveToStorage();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Creates a new Post in the database from the given arguments. Automatically updates storage.
   *
   * @param owner The user that made the post
   * @param caption The caption belonging to the post
   * @param imageData The base64 data containing the imgae of the post
   */
  public void newPost(String owner, String caption, String imageData) {
    Post post = new Post(owner, caption, imageData);
    posts.put(post.getUUID(), post);
    saveToStorage();
  }



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
  public void newUser(String name, String nickname, String email, String password) {
    if (usernameExists(nickname)) {
      throw new IllegalStateException("Username already exists in database!");
    } else {
      User user = new User(name, nickname, email, password);
      users.put(nickname, user);
      saveToStorage();
    }
  }

  /**
   * Fetches a single user.
   *
   * @param nickname The nickname of the user.
   * @return The user object corresponding to the user with the given nickname.
   */
  public User getUser(String nickname) {
    return users.get(nickname);
  }

  /**
   * Fetches a collection of all users in the database.
   *
   * @return returns a collection of all users
   */
  public Collection<User> getUsers() {
    return users.values();
  }

  /**
   * Fetches the mapping between users and usernames.
   *
   * @return A map mapping users to their usernames
   */
  public Map<String, User> getUserMap() {
    return users;
  }

  /**
   * Fetches a list of all posts in the database.
   *
   * @return returns a list of all posts
   */
  public Collection<Post> getPosts() {
    return posts.values();
  }

  /**
   * Fetches the mapping between posts and Ids.
   *
   * @return A map mapping posts to their Ids
   */
  public Map<String, Post> getPostMap() {
    return posts;
  }

  /**
   * Attempts to find a user in the database with the given information.
   *
   * @param username Username or email of the user
   * @param password The password of the User
   * @return User that logged on if it exists, null if no such user exists
   */
  public User tryLogin(String username, String password) {
    User user = users.getOrDefault(username, null);
    if (user != null && user.getPassword().equals(User.hashPassword(password))) {
      return user;
    }
    return null;
  }

  /**
   * Checks if the input username already exists in the database.
   *
   * @param username The username to check
   * @return true if the username exists in the database
   */
  public boolean usernameExists(String username) {
    boolean exists = (users.getOrDefault(username, null) != null);
    return exists;
  }

  /*
   * Pre-constructed object addition methods used in testing and
   * json-deserializing, not part of the DatabaseInterface.
   */

  /**
   * Directly adds the given Post object to the Database.
   */
  public void addPost(Post post) {
    if (!posts.containsKey(post.getUUID())){
      posts.put(post.getUUID(), post);
    }
    if (!users.get(post.getOwner()).getPosts().contains(post.getUUID())) {
      users.get(post.getOwner()).addPost(post.getUUID());
    }
    saveToStorage();
  }

  /**
   * Directly adds the given User to the Database.
   */
  public void addUser(User user) {
    users.put(user.getNickname(), user);
    saveToStorage();
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder("Users:\n");
    for (User user : this.getUsers()) {
      s.append(user.toString() + "\n");
    }
    s.append("Posts:\n");
    for (Post post : this.getPosts()) {
      s.append(post.toString() + "\n");
    }
    return s.toString().trim();
  }

  @Override
  public Comment getComment(String commentUUID, String postUUID) {
    for (Comment comment : posts.get(postUUID).getComments()) {
      if (comment.getUUID().equals(commentUUID)) {
        return comment;
      }
    }
    return null;
  }

  @Override
  public Post getPost(String postUUID) {
    return posts.getOrDefault(postUUID, null);
  }

  @Override
  public Collection<Comment> getComments(String postUUID) {
    return posts.getOrDefault(postUUID, null).getComments();
  }
}
