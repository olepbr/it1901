package it1901.mememedb.core.datastructures;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides a class for Users.
 *
 * @author Magne Halvorsen
 */

public class User {
  private int id;
  private String name;
  private String nickname;
  private String email;
  private List<Post> posts = new ArrayList<>();
  private String hashedPassword;

  public User() {

  }

  /**
   * Initializes a User object.
   * 
   * @param id This user's id.
   * @param name This user's name.
   * @param nickname This user's nickname.
   * @param email This user's email.
   */
  public User(int id, String name, String nickname, String email) {
    this.id = id;
    this.name = name;
    this.nickname = nickname;
    this.email = email;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<Post> getPosts() {
    return posts;
  }

  public void addPost(Post post) {
    posts.add(post);
  }

  public String hashPassword(String password){
    String hashedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    return hashedPassword;
  }

  public void setPassword(String password) {
    this.hashedPassword = hashPassword(password);
  }
  
  public String getPassword() {
    return hashedPassword;
  }
  
  
  
  @Override
  public String toString() {
    return String.format("[User id=%d name=%s nickname=%s email=%s", 
        getId(), getName(), getNickname(), getEmail());
  }

}