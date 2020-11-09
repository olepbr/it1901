package core.datastructures;

import core.io.IO;
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

  /** Generates a new empty database object. Database contains all Users and Posts of the app. */
  public Database() {
    users = new HashMap<String, User>();
    posts = new HashMap<String, Post>();
  }

  /**
   * Generates a new database object, Initialized with data from the given IO Stores database.
   * changes using the IO.
   */
  public Database(IO io) {
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
   * Creates a new User in the database, unless the user already exists. Automatically updates
   * storage.
   *
   * @param user The User to add.
   */
  public void addPost(Post post) {
    users.get(post.getOwner()).addPost(post.getUUID());
    posts.put(post.getUUID(), post);
    saveToStorage();
  }

  /**
   * Creates a new User in the database, unless the user already exists. Automatically updates
   * storage.
   *
   * @param user The User to save.
   */
  public void addUser(User user) {
    if (!users.keySet().contains(user.getNickname())) {
      users.put(user.getNickname(), user);
    }
    saveToStorage();
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
    if (user != null && user.getPassword().equals(user.hashPassword(password))) {
      return user;
    }
    return null;
  }

  /**
   * Sets the post for the PostController
   * @param post
   */

  public void newPost(Post post){
    addPost(post);    
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
   * Adds a new comment to post and returns it to fxui
   * @param author Nickname of user who wrote comment
   * @param commentText Actual content/input of comment
   * @return The comment made
   */
  public Comment newComment(String author, String commentText){
    Comment comment = new Comment(author, commentText);
    this.post.addComment(comment);
    return comment;
  }

  public Collection<Comment> getCommentList(){
    return this.post.getComments();
  }

  @Override
  public String toString() {
    String s = "Users:\n";
    for(User user : this.getUsers()) {
      s += user.toString() + "\n";
    }
    s += "Posts:\n";
    for(Post post : this.getPosts()) {
      s += post.toString() + "\n";
    }
    s = s.trim();
    return s;
  }
}
