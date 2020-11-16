package core.datastructures;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * Provides a class for representing posts. Images are stored in base64 format
 * for ease of use
 */
public class Post {

  private String uuid;
  private String owner;
  private String caption;
  private String image;
  private Map<String, Comment> comments = new HashMap<String, Comment>();

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
   * @param image A file referencing the image of the post.
   * @throws IOException If a problem occurs when reading the image from file
   */
  public Post(String owner, String caption, File image) throws IOException {
    this.setOwner(owner);
    this.setImage(image);
    this.setText(caption);
    this.uuid = (UUID.randomUUID().toString());
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

  /**
   * Returns a collection containing all comments belonging to the given post.
   *
   * @return A collection of the comments
   */
  public Collection<Comment> getComments() {
    return comments.values();
  }

  /**
   * Adds a new comment to the post.
   *
   * @param comment The comment to add.
   */
  public void addComment(Comment comment) {
    comments.put(comment.getUUID(), comment);
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder(String.format(
        "[Post id=%s owner=%s caption=%s image=%s]", getUUID(), getOwner(), getText(), getImage()));
    s.append("\nComments:");
    for (Comment comment : this.getComments()) {
      s.append("\n" + comment.toString());
    }
    return s.toString();
  }
}
