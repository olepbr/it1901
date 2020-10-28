package core.datastructures;

import core.io.IO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Class that represents all the data in the application. */
public class Database {

  private List<User> users;
  private List<Post> posts;

  private IO storage;

  /** Generates a new empty database object. Database contains all Users and Posts of the app. */
  public Database() {
    users = new ArrayList<User>();
  }

  /**
   * Generates a new database object, Initialized with data from the given IO Stores database.
   * changes using the IO.
   */
  public Database(IO io) {
    storage = io;
    users = io.getDatabase().getUsers();
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
  public void savePost(Post post, User user) throws IOException {
    if (!users.contains(user)) {
      users.add(user);
    }
    users.get(users.indexOf(user)).addPost(post);
    saveToStorage();
  }

  /**
   * Creates a new User in the database, unless the user already exists. Automatically updates
   * storage.
   *
   * @param user The User to save.
   */
  public void saveUser(User user) {
    if (!users.contains(user)) {
      users.add(user);
      saveToStorage();
    }
  }

  /**
   * Fetches a list of all posts in the current database.
   *
   * @return Returns a list containing all posts.
   */
  public List<Post> getPostList() {
    List<Post> posts = new ArrayList<Post>();
    for (User user : users) {
      for (Post post : user.getPosts()) {
        posts.add(post);
      }
    }
    return posts;
  }

  public void addPost(Post post) {
    posts.add(post);
  }

  /**
   * Fetches a list of all users in the database.
   *
   * @return returns a list of all users
   */
  public List<User> getUsers() {
    return users;
  }

  /**
   * Fetches a list of all posts in the database.
   *
   * @return returns a list of all posts
   */
  public List<Post> getPosts() {
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
    for (User user : users) {
      if ((user.getEmail().equals(username) || user.getNickname().equals(username))
          && user.getPassword().equals(user.hashPassword(password))) {
        return user;
      }
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
    for (User user : users) {
      if (user.getNickname().equals(username)) {
        return true;
      }
    }
    return false;
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
