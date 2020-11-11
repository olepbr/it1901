package restapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.datastructures.Post;
import spark.Request;
import spark.Response;

/** Contains methods for communicating post data to/from the database. */
public class PostService {

  private ObjectMapper mapper;

  public PostService() {
    mapper = new ObjectMapper();
  }

  // HTTP Status Codes: https://en.wikipedia.org/wiki/List_of_HTTP_status_codes

  // Requests to "/"
  /**
   * Gets all posts from database and returns them as JSON.
   *
   * @param request Spark Request object containing the http request.
   * @param response Spark Response object containing details of the response.
   * @return A JSON string containing the serialized posts.
   */
  public String getAllPosts(Request request, Response response) {
    response.type("application/json");
    // 501: Not Implemented
    // TODO: Implement
    response.status(501);
    return "";
  }

  /**
   * Create a post in the database and returns it as JSON.
   *
   * @param request Spark Request object containing the http request.
   * @param response Spark Response object containing details of the response.
   * @return A JSON string containing the srialized post or a JSON string containing an error
   *     message on failure.
   */
  public String createPost(Request request, Response response) {
    response.type("application/json");
    Post post;
    try {
      post = mapper.readValue(request.body(), Post.class);
    } catch (JsonProcessingException e) {
      System.err.println("Json Processing Error");
      e.printStackTrace();
      // 500: Internal Server Error
      response.status(500);
      return "{\"error:\", \"Json Processing Error\"}";
    }
    // try {
    //   // TODO: Create post in database
    //   // 501: Not Implemented
    //   response.status(501);
    //   return "";
    // } catch (JsonProcessingException e) {
    //   System.err.println("Json Processing Error");
    //   e.printStackTrace();
    //   // 500: Internal Server Error
    //   response.status(500);
    //   return "{\"error:\", \"Json Processing Error\"}";
    // }
    // 501: Not Implemented
    // TODO: Implement
    response.status(501);
    return "";
  }

  // Requests to "/:postID"
  /**
   * Get a single user from database and returns it as JSON.
   *
   * @param request Spark Request object containing the http request.
   * @param response Spark Response object containing details of the response.
   * @return A JSON string containing the srialized user, an empty string if user does not exist or
   *     a JSON string containing an error message on failure.
   */
  public String getPost(Request request, Response response) {
    response.type("application/json");
    Post post = Main.database.getPost(request.params("postID"));
    if (post != null) {
      try {
        // 200: OK
        response.status(200);
        return mapper.writeValueAsString(post);
      } catch (JsonProcessingException e) {
        System.err.println("Json Processing Error");
        e.printStackTrace();
        // 500: Internal Server Error
        response.status(500);
        return "{\"error:\", \"Json Processing Error\"}";
      }
    } else {
      // 404: Not found
      response.status(404);
      return "{\"error:\", \"Resource not found\"}";
    }
  }

  public String updatePost(Request request, Response response) {
    response.type("application/json");
    // 501: Not Implemented
    // TODO: Implement
    response.status(501);
    return "";
  }

  public String deletePost(Request request, Response response) {
    response.type("application/json");
    // 501: Not Implemented
    // TODO: Implement
    response.status(501);
    return "";
  }

  // Other Requests
  public String getAllPostsByUser(Request request, Response response) {
    response.type("application/json");
    // 501: Not Implemented
    // TODO: Implement
    response.status(501);
    return "";
  }
}
