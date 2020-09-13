package mememedb;

public class Post {

    private User owner;
    private String caption;
    private String image;

    public Post(User owner, String caption, String image){
        this.owner = owner;
        this.caption = caption;
        this.image = image;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
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
}
