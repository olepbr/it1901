package core.datastructures;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Provides a class implementing the Database interface that utilises the REST API to acquire data.
 *
 * @author Ole Peder Brandtzæg
 */
public class RestDatabase implements DatabaseInterface {

  private final String endpointBaseUriString;

  private ObjectMapper mapper;

  public RestDatabase(String endpointBaseUriString) {
    this.endpointBaseUriString = endpointBaseUriString;
    this.mapper = new ObjectMapper().registerModule(new MememeModule());
  }

  /**
   * Internal method for generating requests to the REST API/server. Uses the protected modifier
   * to allow for testing.
   *
   * @param path The path to add to the base URI of the server, for instance "/user".
   * @param o The object to pass in the request, e.g. a Comment, Post or User.
   * @param type The type of request to make.
   * @return The response from the server.
   */
  protected HttpResponse<String> requestHandler(String path, Object o, String type) {
    try {
      HttpRequest request;
      if (type.equalsIgnoreCase("POST")) {
        request = HttpRequest.newBuilder(new URI(endpointBaseUriString + path))
            .header("Accept", "application/json")
            .POST(BodyPublishers.ofString(mapper.writeValueAsString(o))).build();
      } else if (type.equalsIgnoreCase("PUT")) {
        request = HttpRequest.newBuilder(new URI(endpointBaseUriString + path))
            .header("Accept", "application/json")
            .PUT(BodyPublishers.ofString(mapper.writeValueAsString(o))).build();
      } else if (type.equalsIgnoreCase("DELETE")) {
        request = HttpRequest.newBuilder(new URI(endpointBaseUriString + path))
            .header("Accept", "application/json")
            .DELETE().build();
      } else if (type.equalsIgnoreCase("GET")) {
        request = HttpRequest.newBuilder(new URI(endpointBaseUriString + path))
            .header("Accept", "application/json")
            .GET().build();
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
  public void newComment(String text, String owner, String postUUID) {
    requestHandler("/post/" + postUUID + "/comment", new Comment(owner, text), "POST");
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
  public void newPost(String owner, String caption, String imageData) {
    requestHandler("/post", new Post(owner, caption, imageData), "POST");
  }

  @Override
  public void newUser(String name, String nickname, String email, String password) {
    requestHandler("/user", new User(name, nickname, email, password), "POST");
  }

  @Override
  public Collection<User> getUsers() {
    Collection<User> userCollection = null;
    try {
      userCollection =
          mapper.readValue(requestHandler("/user", null, "GET")
              .body(), new TypeReference<Collection<User>>() {});
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return userCollection;
  }

  @Override
  public Map<String, User> getUserMap() {
    Collection<User> userCollection = getUsers();
    return userCollection.stream()
        .collect(Collectors.toMap(User::getNickname, Function.identity()));
  }

  @Override
  public Collection<Post> getPosts() {
    Collection<Post> postCollection = null;
    try {
      postCollection =
          mapper.readValue(requestHandler("/post", null, "GET")
              .body(), new TypeReference<Collection<Post>>() {});
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return postCollection;
  }

  @Override
  public Map<String, Post> getPostMap() {
    Collection<Post> postCollection = getPosts();
    return postCollection.stream().collect(Collectors.toMap(Post::getUUID, Function.identity()));
  }

  @Override
  public User tryLogin(String nickname, String password) {
    User user = getUserMap().getOrDefault(nickname, null);
    if (user != null && user.getPassword().equals(User.hashPassword(password))) {
      return user;
    }
    return null;
  }

  @Override
  public boolean nicknameExists(String nickname) {
    return getUserMap().containsKey(nickname);
  }

  @Override
  public Comment getComment(String commentUUID, String postUUID) {
    Comment comment = null;
    try {
      comment = mapper
          .readValue(requestHandler("/post/:" + postUUID + "/comment/:" + commentUUID, null, "GET")
          .body(), Comment.class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return comment;
  }

  @Override
  public Post getPost(String postUUID) {
    Post post = null;
    try {
      post = mapper
          .readValue(requestHandler("/post/:" + postUUID, null, "GET")
          .body(), Post.class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return post;
  }

  @Override
  public User getUser(String nickname) {
    User user = null;
    try {
      user = mapper
          .readValue(requestHandler("/user/:" + nickname, null, "GET")
          .body(), User.class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return user;
  }

  @Override
  public List<Comment> getComments(String postUUID) {
    List<Comment> comments = null;
    try {
      comments =
          mapper
          .readValue(requestHandler("/post/:" + postUUID + "/comment", null, "GET")
          .body(), new TypeReference<List<Comment>>() {});
          Collections.sort(comments);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return comments;
  }



}
