package mememedb;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IO {
	
	
	public List<Post> getPostList();
	public void savePost(Post post) throws IOException;
	public void saveImage(File image) throws IOException;
	
}
