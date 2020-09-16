package mememedb;

import java.io.File;
import java.util.List;

public interface IO {
	
	
	public List<Post> getPostList();
	public void savePost(Post post);
	public void saveImage(File image);
	
}
