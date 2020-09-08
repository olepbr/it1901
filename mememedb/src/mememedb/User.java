package mememedb;
/**
 * Provides a class for Users
 *
 * @author Magne Halvorsen
 *
 */

public class User
{
  private int id;
  private String name;
  private String nickname;
  private String email;

  public User(int id, String name, String nickname, String email)
  {
    this.id = id;
    this.name = name;
    this.nickname = nickname;
    this.email = email;
  }
}
