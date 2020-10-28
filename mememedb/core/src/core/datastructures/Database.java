package core.datastructures;

import core.io.IO;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/** Class that represents all the data in the application. */
public class Database {
  /* Maps are used, as the ordering of objects is less important compared
     to the connection between the name/id and the object itself. */
  private Map<String, User> users;
  private Map<String, Post> posts;

  private IO storage;

  /**
   * Generates a new empty database object. Database contains all Users and Posts
   * of the app.
   */
  public Database() {
    users = new HashMap<String, User>();
  }

  /**
   * Generates a new database object, Initialized with data from the given IO Stores database.
   * changes using the IO.
   */
  public Database(IO io) {
    storage = io;
    users = io.getDatabase().getUserMap();
  }

  /** Saves cached database, overwriting previous data in storage. */
  public void saveToStorage() {
    if (storage != null) {
      storage.saveDatabase(this);
    }
  }

  /**
   * Saves post in database for user. Automatically updates storage.
   *
   * @param post The Post to save.
   * @param user Owner of the post.
   */
  public void savePost(Post post) throws IOException {
    users.get(post.getOwner()).addPost(post.getId());
    posts.put(post.getId(), post);
    saveToStorage();
  }

  /**
   * Creates a new User in the database, unless the user already exists. Automatically updates
   * storage.
   *
   * @param user The User to save.
   */
  public void saveUser(User user) {
    if (!users.keySet().contains(user.getNickname())) {
      users.put(user.getNickname(), user);
      saveToStorage();
    }
  }

  /**
   * Fetches a collection of all posts in the current database.
   *
   * @return Returns a collection containing all posts.
   */
  public Map<String, Post> getPostList() {
    return posts;
  }

  public void addPost(Post post) {
    posts.put(post.getId(), post);
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
    if(user!= null && user.getPassword() == user.hashPassword(password)){
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

  /**
   * Makes a unique ID based on the amount of users that already exist in the database.
   *
   * @return a new ID for a user being created.
   */
  public int getNewId() {
    int id = users.size();
    id++;
    return id;
  }
}
