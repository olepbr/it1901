package core.datastructures;

import core.io.IO;
import core.io.LocalIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Database {

  private List<User> users;

  private IO storage;

  /**
   * Generates a new database object, Database contains all Users and Posts of the app, and
   * corresponds with an IO-object to read and write data.
   */
  public Database() {
    storage = new LocalIO();
    users = new ArrayList<User>();
  }

  /** Saves cached database, overwriting previous data in storage. */
  public void saveToStorage() {
    storage.saveDatabase(this);
  }

  /**
   * Saves post in database for user. Automatically updates storage.
   *
   * @param post The Post to save.
   * @param image The image belonging to the post
   * @param user Owner of the post.
   */
  public void savePost(Post post, File image, User user) throws IOException {
    if (!users.contains(user)) {
      users.add(user);
    }
    users.get(users.indexOf(user)).addPost(post);
    saveToStorage();
    storage.saveImage(image);
  }

  /**
   * Creates a new User in the database, unless the user already exists. Automatically updates
   * storage.
   *
   * @param user The User to save.
   */
  public void saveUser(User user) {
    if (! users.contains(user)) {
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

  /**
   * Fetches a list of all users in the database
   *
   * @return returns a list of all users
   */
  public List<User> getUsers() {
    return users;
  }

  /**
   * Gets a File that references the image in the database with the given name.
   *
   * @param imgName The name of the image to find.
   * @return File pointing to the given image
   */
  public File getImage(String imgName) {
    return storage.getImageFromName(imgName);
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
          && user.getPassword().equals(password)) {
        return user;
      }
    }
    return null;
  }

  /**
   * Checks if the input username already exists in the database.
   *
   * @param username
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
  public int getNewID() {
    int id = users.size();
    id++;
    return id;
  }
}
