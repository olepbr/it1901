package restapi;

import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_OK;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.datastructures.Comment;
import core.datastructures.Post;
import core.json.MememeModule;
import java.util.Collection;
import spark.Request;
import spark.Response;

/** Contains methods for communicating post data to/from the database. */
public class CommentService {

  private ObjectMapper mapper;

  public CommentService() {
    mapper = new ObjectMapper();
    mapper.registerModule(new MememeModule());
  }

  // HTTP Status Codes: https://en.wikipedia.org/wiki/List_of_HTTP_status_codes

  // Requests to "/"
  /**
   * [TODO:description]
   *
   * @param request [TODO:description]
   * @param response [TODO:description]
   * @return [TODO:description]
   */
  public String getAllComments(Request request, Response response) {
    response.type("application/json");
    Collection<Comment> comments = Server.database.getComments(request.params("postID"));
    if (Server.database.getPost(request.params("postID")) != null) {
      try {
        response.status(HTTP_OK);
        return mapper.writeValueAsString(comments);
      } catch (JsonProcessingException e) {
        System.err.println("Json Processing Error");
        e.printStackTrace();
        response.status(HTTP_INTERNAL_ERROR);
        return "{\"error:\", \"Json Processing Error\"}";
      }
    } else {
      response.status(HTTP_NOT_FOUND);
      return "";
    }
  }

  /**
   * [TODO:description]
   *
   * @param request [TODO:description]
   * @param response [TODO:description]
   * @return [TODO:description]
   */
  public String createComment(Request request, Response response) {
    response.type("application/json");
    if (Server.database.getPost(request.params("postID")) != null) {
      try {
        Comment comment = mapper.readValue(request.body(), Comment.class);
        Server.database.newComment(
            comment.getText(), comment.getAuthor(), request.params("postID"));
        response.status(HTTP_OK);
        return "{\"message:\", \"Comment creation successful\"}";
      } catch (JsonProcessingException e) {
        System.err.println("Json Processing Error");
        response.status(HTTP_INTERNAL_ERROR);
        return "{\"error:\", \"Json Processing Error\"}";
      }
    } else {
      response.status(HTTP_NOT_FOUND);
      return "";
    }
  }

  // Requests to "/:commentID"
  /**
   * Gets all comments for a given post.
   *
   * @param request Spark Request object containing the http request.
   * @param response Spark Response object containing details of the response.
   * @return A JSON string containing the serialized comments or an error message.
   */
  public String getComment(Request request, Response response) {
    response.type("application/json");
    Post post = Server.database.getPost(request.params("postID"));
    Comment comment =
        Server.database.getComment(request.params("postID"), request.params("commentID"));
    if (post != null && comment != null) {
      try {
        response.status(HTTP_OK);
        return mapper.writeValueAsString(comment);
      } catch (JsonProcessingException e) {
        System.err.println("Json Processing Error");
        e.printStackTrace();
        response.status(HTTP_INTERNAL_ERROR);
        return "{\"error:\", \"Json Processing Error\"}";
      }
    } else {
      response.status(HTTP_NOT_FOUND);
      return "";
    }
  }

  /**
   * [TODO:description]
   *
   * @param request [TODO:description]
   * @param response [TODO:description]
   * @return [TODO:description]
   */
  // public String updateComment(Request request, Response response) {
  //   response.type("application/json");
  //   response.status(HTTP_NOT_IMPLEMENTED);
  //   // TODO: Implement
  //   return "{\"error\": \"This method is not implemented\"}";
  // }

  /**
   * [TODO:description]
   *
   * @param request [TODO:description]
   * @param response [TODO:description]
   * @return [TODO:description]
   */
  // public String deleteComment(Request request, Response response) {
  //   response.type("application/json");
  //   response.status(HTTP_NOT_IMPLEMENTED);
  //   // TODO: Implement
  //   return "{\"error\": \"This method is not implemented\"}";
  // }
}
