package core.datastructures;

import com.google.common.hash.Hashing;
import it1901.mememedb.core.datastructures.User;

import java.nio.charset.StandardCharsets;

public class PasswordHasher {

    private String hashedPassword;
    private it1901.mememedb.core.datastructures.User user;

    public void hashPassword(String password){
        String hashedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        this.hashedPassword = hashedPassword;
    }

    public String getHashedPassword(User user){
        return hashedPassword;
    }
}
