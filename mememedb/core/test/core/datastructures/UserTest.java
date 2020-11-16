package core.datastructures;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UserTest {

  @Test
  public void TestHashing(){
    String password = "averysafepassword";
    User user = new User("bert", "bert", "bert@bert.com", password);
    assertEquals(User.hashPassword(password), user.getPassword(), "Error in user construction, given passwords should always be hashed unless otherwise specified");
  }

}
