package core.datastructures;

import java.util.UUID;

/**Class that represents a comment for a given post in the app. */
public class Comment {

  private String uuid;
  private String text;
  private String author;

  public String getText() {
    return text;
  }

  public String getAuthor() {
    return author;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getUUID() {
    return uuid;
  }

  public void setUUID(String uuid) {
    this.uuid = uuid;
  }


  /**
   * Generates a new Comment from the given paramaters, 
   * id is assigned automatically using java's UUID class.
   *
   * @param author the username of the author of the comment
   * @param text the text in the comment
   */
  public Comment(String author, String text) {
    this.text = text;
    this.author = author;
    this.uuid = UUID.randomUUID().toString();
  }

  /**
   *Generates an empty comment for use in construction.
   */
  public Comment() {
  }

  public String toString() {
    return this.author + "commented:\n" + this.text;
  }
}