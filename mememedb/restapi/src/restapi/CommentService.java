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
   * Retrieves all comments for a post.
   *
   * @param request Spark Request object containing the http request.
   * @param response Spark Response object containing details of the response.
   * @return A JSON string containing the serialized comments or a message/error.
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
        return "{\"message\": \"\", \"error\": \"Json Processing Error\"}";
      }
    } else {
      response.status(HTTP_NOT_FOUND);
      return "";
    }
  }

  /**
   * Creates a comment for a post.
   *
   * @param request Spark Request object containing the http request.
   * @param response Spark Response object containing details of the response.
   * @return A JSON string containing a message or error.
   */
  public String createComment(Request request, Response response) {
    response.type("application/json");
    if (Server.database.getPost(request.params("postID")) != null) {
      try {
        Comment comment = mapper.readValue(request.body(), Comment.class);
        if (Server.database.nicknameExists(comment.getAuthor())) {
          Server.database.newComment(
              comment.getText(), comment.getAuthor(), request.params("postID"));
          response.status(HTTP_OK);
          return "{\"message\": \"Comment creation successful\", \"error\": \"\"}";
        } else {
          response.status(HTTP_NOT_FOUND);
          return "{\"message\": \"\", \"error\": \"User does not exist\"}";
        }
      } catch (JsonProcessingException e) {
        System.err.println("Json Processing Error");
        response.status(HTTP_INTERNAL_ERROR);
        return "{\"message\": \"\", \"error\": \"Json Processing Error\"}";
      }
    } else {
      response.status(HTTP_NOT_FOUND);
      return "{\"message\": \"\", \"error\": \"Post does not exist\"}";
    }
  }

  // Requests to "/:commentID"
  /**
   * Gets all comments for a given post.
   *
   * @param request Spark Request object containing the http request.
   * @param response Spark Response object containing details of the response.
   * @return A JSON string containing the serialized comment or an error message.
   */
  public String getComment(Request request, Response response) {
    response.type("application/json");
    Post post = Server.database.getPost(request.params("postID"));
    Comment comment =
        Server.database.getComment(request.params("postID"), request.params("commentID"));
    if (post == null) {
      response.status(HTTP_NOT_FOUND);
      return "{\"message\": \"\", \"error\": \"Post does not exist\"}";
    } else if (comment == null) {
      response.status(HTTP_NOT_FOUND);
      return "{\"message\": \"\", \"error\": \"Comment does not exist\"}";
    } else {
      try {
        response.status(HTTP_OK);
        return mapper.writeValueAsString(comment);
      } catch (JsonProcessingException e) {
        System.err.println("Json Processing Error");
        e.printStackTrace();
        response.status(HTTP_INTERNAL_ERROR);
        return "{\"error:\", \"Json Processing Error\", \"message\": \"\"}";
      }
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
