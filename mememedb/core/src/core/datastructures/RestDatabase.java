package core.datastructures;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.json.MememeModule;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.Map;

public class RestDatabase implements DatabaseInterface {

  private final String endpointBaseUriString;

  private ObjectMapper mapper;

  public RestDatabase(String endpointBaseUriString) {
    this.endpointBaseUriString = endpointBaseUriString;
    this.mapper = new ObjectMapper().registerModule(new MememeModule());
  }

  private HttpResponse<String> requestHandler(String path, Object o, String type) {
    try {
      HttpRequest request;
      if (type.equalsIgnoreCase("POST")) {
        request = HttpRequest.newBuilder(new URI(endpointBaseUriString + path))
            .header("Accept", "application/json")
            .POST(BodyPublishers.ofString(mapper.writeValueAsString(o)))
            .build();
      } else if (type.equalsIgnoreCase("PUT")) {
        request = HttpRequest.newBuilder(new URI(endpointBaseUriString + path))
            .header("Accept", "application/json")
            .PUT(BodyPublishers.ofString(mapper.writeValueAsString(o)))
            .build();
      } else if (type.equalsIgnoreCase("DELETE")) {
        request = HttpRequest.newBuilder(new URI(endpointBaseUriString + path))
            .header("Accept", "application/json")
            .DELETE()
            .build();
      } else if (type.equalsIgnoreCase("GET")) {
        request = HttpRequest.newBuilder(new URI(endpointBaseUriString + path))
            .header("Accept", "application/json")
            .GET()
            .build();
      } else {
        throw new IllegalArgumentException("Invalid request type");
      }
      return HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
    } catch (URISyntaxException e) {
      e.printStackTrace();
      return null;
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void newComment(String text, String owner, String postUuid) {
    requestHandler("/post/" + postUuid + "/comment", new Comment(owner, text), "POST");
  }

  @Override
  public void newPost(String owner, String caption, File image) {
    try {
      requestHandler("/post", new Post(owner, caption, image), "POST");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void newUser(String name, String nickname, String email, String password) {
    requestHandler("/user", new User(name, nickname, email, password), "POST");
  }

  @Override
  public Collection<User> getUsers() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<String, User> getUserMap() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<Post> getPosts() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<String, Post> getPostMap() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public User tryLogin(String username, String password) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean usernameExists(String username) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Comment getComment(String commentUuid, String postUuid) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Post getPost(String postUuid) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public User getUser(String nickname) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<Comment> getComments(String postUuid) {
    // TODO Auto-generated method stub
    return null;
  }
}
