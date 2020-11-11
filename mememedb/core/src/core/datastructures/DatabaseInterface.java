package core.datastructures;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public interface DatabaseInterface {
	void newComment(String text, String owner, String postUUID);
	void newPost(String owner, String caption, File image) throws IOException;
	void newUser(String name, String nickname, String email, String password);
	Collection<User> getUsers();
	Map<String, User> getUserMap();
	Collection<Post> getPosts();
	Map<String, Post> getPostMap();
	User tryLogin(String username, String password);
	boolean usernameExists(String username);
}
