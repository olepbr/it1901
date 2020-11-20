package core.datastructures;


import java.time.LocalDateTime;
import java.util.UUID;

/** Class that represents a comment for a given post in the app.
 *  Implements comparable to be able to sort comments according to date */
public class Comment implements Comparable<Comment> {

  private String uuid;
  private String text;
  private String author;
  private String date;

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

  /**
   * Sets the time the comment was posted on. Used for sorting comments by date
   *
   * @param date The time, given as the string version
   *             of a java LocalDateTime, e.g. "2007-12-03T10:15:30"
   */
  public void setDate(String date) {
    this.date = date;
  }

  public String getUUID() {
    return uuid;
  }

  public void setUUID(String uuid) {
    this.uuid = uuid;
  }


  /**
   * Returns the time the comment was posted on.
   *
   * @return The time, given as the string version of a java LocalDateTime,
   *          e.g. "2007-12-03T10:15:30"
   */
  public String getDate() {
    return this.date;
  }

  /**
   * Generates a new Comment from the given paramaters, id is assigned
   * automatically using java's UUID class.
   *
   * @param author the username of the author of the comment
   * @param text   the text in the comment
   */
  public Comment(String author, String text) {
    this.text = text;
    this.author = author;
    this.uuid = UUID.randomUUID().toString();
    this.date = LocalDateTime.now().toString();
  }

  /**
   * Generates an empty comment for use in construction.
   */
  public Comment() {
  }

  @Override
  public String toString() {
    return "\n" + this.author + " commented:\n" + this.text;
  }
  
  /**
   * Compares the dates of two comments(using the uuids to break ties) to allow comment sorting.
   * Only returns equal if the comments are the same object.
   */
  @Override
  public int compareTo(Comment other) {
    if (this.equals(other)) {
      return 0;
    }
    LocalDateTime time1 = LocalDateTime.parse(this.getDate());
    LocalDateTime time2 = LocalDateTime.parse(other.getDate());
    int foo = time1.compareTo(time2);
    if (foo == 0) {
      return this.getUUID().compareTo(other.getUUID());
    }
    return foo;
  }

  /**
   * Implements equals(), following convention of implementing equality alongside comparator.
   * Checks whether the compared objects are the same object.
   */
  @Override
  public boolean equals(Object other) {
    return this == other;
  }

 
}