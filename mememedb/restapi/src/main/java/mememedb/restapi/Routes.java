package mememedb.restapi;

import static spark.Spark.*;

/** Decides what service to call based on URI. */
public class Routes {

  private static UserService userService = new UserService();

  public static void configureRoutes() {
    path(
        "/user",
        () -> {
          get("/", (request, response) -> userService.getAllUsers(request, response));
          get("/:nickname", (request, response) -> userService.getUser(request, response));
        });
  }
}
