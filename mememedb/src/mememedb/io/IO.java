package mememedb.io;

import java.io.File;
import java.io.IOException;
import java.util.List;

import mememedb.datastructures.Post;

public interface IO {
	
	
	public List<Post> getPostList();//return list of posts in database
	public void savePost(Post post) throws IOException;//store post in database
	public void saveImage(File image) throws IOException;//store image in database(or other storage location)
	public File getImageFromName(String name);//get reference to image given name
	
}
