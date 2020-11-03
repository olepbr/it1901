package core.datastructures;

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

  private String name;
  private String nickname;
  private String email;
  private List<String> posts = new ArrayList<>();
  private String hashedPassword;

  public User() {}

  /**
   * Initializes a User object.
   *
   * @param name This user's name.
   * @param nickname This user's nickname.
   * @param email This user's email.
   */
  public User(String name, String nickname, String email) {
    this.name = name;
    this.nickname = nickname;
    this.email = email;
  }

  /* Get/Set Name */
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /* Get/Set Nickname */
  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  /* Get/Set email */
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<String> getPosts() {
    return posts;
  }

  public void addPost(String postUUID) {
    posts.add(postUUID);
  }

  /* Passwords */
  /**
   * Hashes the given String using SHA256 encryption.
   *
   * @param password The password to check
   * @return The hashed password as String
   */
  public String hashPassword(String password) {
    String hashedPassword =
        Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    return hashedPassword;
  }

  public void setHashedPassword(String password) {
    this.hashedPassword = password;
  }

  public void setPassword(String password) {
    this.hashedPassword = hashPassword(password);
  }

  public String getPassword() {
    return hashedPassword;
  }

  @Override
  public String toString() {
    return String.format(
        "[User name=%s nickname=%s email=%s password=%s]", getName(), getNickname(), getEmail(), getPassword());
  }
}
