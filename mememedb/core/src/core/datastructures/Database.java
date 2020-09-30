package it1901.mememedb.core.datastructures;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import it1901.mememedb.core.io.IO;
import it1901.mememedb.core.io.LocalIO;

public class Database {
  
  private List<User> users;
  private IO storage;
  
  
  /**
   * Generates a new database object, Database contains all Users and Posts of the app, 
   * and corresponds with an IO-object to read and write data.
   */
  public Database() {
    storage = new LocalIO();
    //reload();
  }
  
  
  /**
   * Removes cached database, and reloads from storage.
   */
  /*public void reload() {
    users = storage.getUserList();
  }*/
  
  /**
   * Saves cached database, overwriting previous data in storage.
   */
  /*public void saveToStorage() {
    storage.save(users);
  }*/
  
  /**
   * Saves post in database for user.
   * Automatically updates storage.
   * 
   * @param post The Post to save.
   * @param image The image belonging to the post
   * @param user Owner of the post.
   */
  public void savePost(Post post, File image, User user) {
    if(!users.contains(user)) {
      users.add(user);
    }
    users.get(users.indexOf(user)).addPost(post);
   // saveToStorage();
   //storage.saveImage(image);
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
    //saveToStorage();
  }
  
  /**
   * Fetches a list of all posts in the current database.
   * 
   * @return Returns a list containing all posts.
   */
  /*public List<Post> getPostList(){
    List<Post> posts = new ArrayList<Post>();
    for(User user : users) {
      for(Post post : user.getPosts()) {
        posts.add(post);
      }
    }
    return posts;
  }*/
  
  
  /**
   * Fetches a list of all users in the database
   * 
   * @return returns a list of all users
   */
  public List<User> getUsers(){
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
   * @param username Username or email of the user
   * @param password The password of the User
   * @return User that logged on if it exists, null if no such user exists
   */
  public User tryLogin(String username, String password) {
    for(User user : users) {
      if((user.getEmail() == username || user.getNickname() == username) && user.getPassword() == password) {
        return user;
      }
    }
    return null;
  }
  
  
  
  
  
  
  
  
}
