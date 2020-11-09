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
   * Maps are used, as the ordering of objects is less important compared to the connection between
   * the name/id and the object itself.
   */
  private Map<String, User> users;
  private Map<String, Post> posts;

  private IO storage;

  /** Generates a new empty database object. LocalDatabase contains all Users and Posts of the app. */
  public LocalDatabase() {
    users = new HashMap<String, User>();
    posts = new HashMap<String, Post>();
  }

  /**
   * Generates a new database object, Initialized with data from the given IO Stores database. changes
   * using the IO.
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
   * Creates a new Comment in the database, unless the post already exists. Automatically updates
   * storage.
   *
   * @param comment The comment to add.
   */
  public void newComment(String text, String owner, String postUUID) {
    Post post = posts.getOrDefault(postUUID, null);
    if (post  != null) {
      post.addComment(new Comment(owner, text));
      saveToStorage();
    }
  }

  /**
   * Creates a new Post in the database. Automatically updates
   * storage.
   *
   * @param post The Post to add.
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
   * Creates a new User in the database, unless the user already exists. Automatically updates
   * storage.
   *
   * @param name The name of the user.
   * @param nickname The nickname of the user.
   * @param email The email of the user.
   * @param password The password of the user.
   * @throws IllegalStateException In the case of the nickname already existing in the database.
   */
  public void newUser(String name, String nickname, String email, String password) {
    if (usernameExists(nickname)) {
      throw new IllegalStateException("Username already exists in database!");
    }
    else {
      User user = new User(name, nickname, email, User.hashPassword(password));
      users.put(nickname, user);
      saveToStorage();
    }
  }

  /**
   * Fetches a single user
   *
   * @param nickname The nickname of the user
   * @return The user object of the user with nickname
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
   * Fetches the mapping between users and usernames
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
   * Fetches the mapping between posts and Ids
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

  @Override
  public String toString() {
    String s = "Users:\n";
    for (User user : this.getUsers()) {
      s += user.toString() + "\n";
    }
    s += "Posts:\n";
    for (Post post : this.getPosts()) {
      s += post.toString() + "\n";
    }
    s = s.trim();
    return s;
  }
}
