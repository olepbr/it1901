package core.datastructures;

import java.util.UUID;

public class Comment {
  private String id;
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

  public String getId(){
    return id;
  }

  public void setId(String id){
    this.id = id;
  }

  public Comment(String author, String text) {
    this.text = text;
    this.author = author;
    id = UUID.randomUUID().toString();
  }

  public String toString(){
    return this.author + "commented:\n" + this.text;
  }
}