package core.datastructures;

import core.io.IO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

  @Override
  public void newComment(String text, String owner, String postUUID) {
    Post post = posts.getOrDefault(postUUID, null);
    if (post != null) {
      post.addComment(new Comment(owner, text));
      saveToStorage();
    }
  }

  @Override
  public void newPost(String owner, String caption, File image) {
    try {
      Post post = new Post(owner, caption, image);
      addPost(post);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void newPost(String owner, String caption, String imageData) {
    Post post = new Post(owner, caption, imageData);
    addPost(post);
  }

  @Override
  public void newUser(String name, String nickname, String email, String password) {
    if (nicknameExists(nickname)) {
      throw new IllegalStateException("Nickname already exists in database!");
    } else {
      User user = new User(name, nickname, email, password);
      addUser(user);
    }
  }

  @Override
  public User getUser(String nickname) {
    return users.get(nickname);
  }

  @Override
  public Collection<User> getUsers() {
    return users.values();
  }

  @Override
  public Collection<Post> getPosts() {
    return posts.values();
  }


  @Override
  public User tryLogin(String nickname, String password) {
    User user = users.getOrDefault(nickname, null);
    if (user != null && user.getPassword().equals(User.hashPassword(password))) {
      return user;
    }
    return null;
  }

  @Override
  public boolean nicknameExists(String nickname) {
    boolean exists = (users.getOrDefault(nickname, null) != null);
    return exists;
  }

  @Override
  public Post getPost(String postUUID) {
    return posts.getOrDefault(postUUID, null);
  }

  @Override
  public List<Comment> getComments(String postUUID) {
    List<Comment> comments = new ArrayList<Comment>(
        posts.getOrDefault(postUUID, null).getComments());
    Collections.sort(comments);
    return comments;
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




  /*
   * Pre-constructed object addition and internal getter methods used in testing and
   * json-deserializing, not part of the DatabaseInterface.
   */

  /**
   * Directly adds the given Post object to the Database.
   */
  public void addPost(Post post) {
    if (!posts.containsKey(post.getUUID())) {
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

  /**
   * Returns the internal postMap, used when creating 
   * database from IO.
   *
   * @return A map mapping posts to their ids
   */
  public Map<String, Post> getPostMap() {
    return posts;
  }

  /**
   * Fetches the internal mapping between 
   * users and nicknames, used when constructing from IO.
   *
   * @return A map mapping users to their nicknames
   */
  public Map<String, User> getUserMap() {
    return users;
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
}
