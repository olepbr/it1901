package core.datastructures;

import java.util.UUID;

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

  public String getUUID(){
    return uuid;
  }

  public void setUUID(String uuid){
    this.uuid = uuid;
  }

  public Comment(String author, String text) {
    this.text = text;
    this.author = author;
    this.uuid = UUID.randomUUID().toString();
  }

  public Comment() {
}

public String toString(){
    return this.author + "commented:\n" + this.text;
  }
}