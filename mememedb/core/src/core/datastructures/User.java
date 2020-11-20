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
   * @param name This user's real name.
   * @param nickname This user's nickname, this should be unique.
   * @param email This user's email. Email must be a valid email on the form "xxx@xx.xx"
   */
  public User(String name, String nickname, String email, String password) {
    this.name = name;
    this.nickname = nickname;
    this.email = email;
    setPassword(password);
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

  /**
   * Returns the uuids of the posts belonging to the User.
   *
   * @return A list of uuids, each belonging to one of the users posts
   */
  public List<String> getPosts() {
    return posts;
  }

  /**
   * Adds the given postuuid to the user.
   *
   * @param postUUID the uuid of the post to add
   */
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
  public static String hashPassword(String password) {
    String hashedPassword =
        Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    return hashedPassword;
  }

  /**
   * Sets the hashed password of the user directly.
   *
   * @param password The password to set, this is stored directly
   *                 as the password without being hashed by the program
   */
  public void setHashedPassword(String password) {
    this.hashedPassword = password;
  }

  /**
   * Sets the password of the user, password is hashed using SHA256 before it is stored.
   *
   * @param password The password to use
   */
  public void setPassword(String password) {
    this.hashedPassword = hashPassword(password);
  }

  /**
   * Returns the hashed password of the user.
   *
   * @return The hashed password string
   */
  public String getPassword() {
    return hashedPassword;
  }

  @Override
  public String toString() {
    return String.format("[User name=%s nickname=%s email=%s password=%s]",
        getName(), getNickname(), getEmail(), getPassword());
  }
}
