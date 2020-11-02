package core.datastructures;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/** Provides a class for representing posts. Images are stored in base64 format for ease of use */
public class Post {

  private String uuid;
  private String owner;
  private String caption;
  private String image;
  private List<Comment> comments = new ArrayList<Comment>();

  public Post() {}

  /**
   * Initializes a Post object.
   *
   * @param owner The owner of the post.
   * @param caption The post's caption.
   * @param image The image of the post as a base64-encoded String.
   */
  public Post(String owner, String caption, String image) {
    this.setOwner(owner);
    this.setImageData(image);
    this.setText(caption);
    this.uuid = UUID.randomUUID().toString();
  }

  /**
   * Initializes a Post object.
   *
   * @param owner The owner of the post.
   * @param caption The post's caption.
   * @param image The image of the post.
   * @throws IOException If a problem occurs when reading the image File
   */
  public Post(String owner, String caption, File image) throws IOException {
    this.setOwner(owner);
    this.setImage(image);
    this.setText(caption);
    this.uuid = (UUID.randomUUID().toString());
  }

  public void updatePost(Post post) {
    this.owner = post.owner;
    this.caption = post.caption;
    this.image = post.image;
  }

  /* Get/Set ID */
  public String getUUID() {
    return uuid;
  }

  public void setUUID(String uuid) {
    this.uuid = uuid;
  }

  /* Get/Set Owner */
  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  /* Get/Set Text */
  public String getText() {
    return caption;
  }

  public void setText(String text) {
    this.caption = text;
  }

  /* Get/Set Image */
  public String getImage() {
    return image;
  }

  /**
   * Sets the image of the post to the data in the given file.
   *
   * @param image The image file to use.
   * @throws IOException If an error occurs during reading.
   */
  public void setImage(File image) throws IOException {
    try (FileInputStream imageStream = new FileInputStream(image)) {
      byte[] data = imageStream.readAllBytes();
      String data64 = Base64.getEncoder().encodeToString(data);
      this.image = data64;
    } catch (IOException e) {
      throw e;
    }
  }

  /**
   * Bypasses the need to use a File to set image, expects a base64 encoded image as a String.
   *
   * @param image The image to save, given as a base64 encoded String
   */
  public void setImageData(String image) {
    this.image = image;
  }

  public List<Comment> getComments() {
    return comments;
  }

  /**
   * Adds a new comment to the post.
   *
   * @param comment The comment to add.
   */
  public void addComment(Comment comment) {
    comments.add(comment);
  }

  @Override
  public String toString() {
    return String.format(
        "[Post id=%s owner=%s caption=%s image=%s]", getUUID(), getOwner(), getText(), getImage());
  }
}
