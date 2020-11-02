package core.datastructures;

public class Comment {
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

  public Comment(String author, String text) {
    this.text = text;
  }

  public String toString(){
    return this.author + "commented:\n" + this.text;
  }
}