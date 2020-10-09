package core.datastructures;

/** Provides a class for representing posts. */
public class Post {

  private String owner;
  private String caption;
  private String image;

  public Post() {}

  /**
   * Initializes a Post object.
   *
   * @param owner The owner of the post.
   * @param caption The post's caption.
   * @param image The image of the post.
   */
  public Post(String owner, String caption, String image) {
    this.owner = owner;
    this.caption = caption;
    this.image = image;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getText() {
    return caption;
  }

  public void setText(String text) {
    this.caption = text;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  @Override
  public String toString() {
    return String.format("[Post owner=%s caption=%s image=%s]", getOwner(), getText(), getImage());
  }
}
