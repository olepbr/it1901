package mememedb.restapi;

// Spark
import spark.Request;
import spark.Response;

// Mememe Core
import core.datastructures.User;
import core.json.MememeModule;

// Jackson
import com.fasterxml.jackson.core.JsonProcessingException;

public class UserService {

  public Response getAllUsers(Request request, Response response) {
    // 204: No Content
    response.status(204);
    // Empty body
    response.body("");
    return response;
  }

  public Response getUser(Request request, Response response) {
    User user = Main.database.getUser(request.params("nickname"));
    if(user != null){
      try {
        // 200: OK
        response.status(200);
        response.body(MememeModule.serializeUser(user));
      } catch(JsonProcessingException e) {
        System.err.println("Json Processing Error");
        e.printStackTrace();
        // 500: Internal Server Error
        response.status(500);
        response.body("{\"error:\", \"Json Processing Error\"}");
      }
    } else {
      // 404: Not found
      response.status(404);
      // Return empty list
      response.body("[]");
    }
    return response;
  }

  public Response postUser(Request request, Response response) {
    return response;
  }

  public Response deleteUser(Request request, Response response) {
    return response;
  }

  public Response putUser(Request request, Response response) {
    return response;
  }
}
