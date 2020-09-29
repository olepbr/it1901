package mememedb.datastructures;

import java.util.ArrayList;
import java.util.List;

import mememedb.io.IO;
import mememedb.io.LocalIO;

public class Database {
  
  List<User> users;
  IO storage;
  
  
  /**
   * Generates a new database object, Database contains all Users and Posts of the app, 
   * and corresponds with an IO-object to read and write data.
   */
  public Database() {
    storage = new LocalIO();
    reload();
  }
  
  
  /**
   * Removes cached database, and reloads from storage.
   */
  public void reload() {
    users = storage.getUserList();
  }
  
  /**
   * Saves cached database, overwriting previous data in storage.
   */
  public void saveToStorage() {
    storage.save(users);
  }
  
  /**
   * Saves post in database for user.
   * Automatically updates storage.
   * 
   * @param post The Post to save.
   * @param user Owner of the post.
   */
  public void savePost(Post post, User user) {
    if(!users.contains(user)) {
      users.add(user);
    }
    else {
      users.get(users.indexOf(user)).addPost(post);
    }
    saveToStorage();
  }
  
  /**
   * Creates a new User in the database, unless the user already exists.
   * Automatically updates storage.
   * 
   * @param user The User to save.
   */
  public void saveUser(User user) {
    if(!users.contains(user)) {
      users.add(user);
    }
    saveToStorage();
  }
  
  /**
   * Fetches a list of all posts in the current database.
   * 
   * @return Returns a list containing all posts.
   */
  public List<Post> getPostList(){
    List<Post> posts = new ArrayList<Post>();
    for(User user : users) {
      for(Post post : user.getPosts()) {
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
  public List<User> getUsers(){
    return users;
  }
  
  
  
  
  
  
  
  
  
}
