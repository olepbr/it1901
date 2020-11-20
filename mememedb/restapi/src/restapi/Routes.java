package restapi;

import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;

/** Decides what service to call based on URI. */
public class Routes {

  private static UserService userService;
  private static PostService postService;
  private static CommentService commentService;

  // Construct the individual controllers.
  static {
    userService = new UserService();
    postService = new PostService();
    commentService = new CommentService();
  }

  /** Sets ut routes. They decide which method to use based on what URI was requested. */
  public static void configureRoutes() {
    path(
        "/",
        () -> {
          get("", (request, response) -> "Welcome to mememedb!\n\r");
        });
    path(
        "/user",
        () -> {
          get("", (request, response) -> userService.getAllUsers(request, response));
          post("", (request, response) -> userService.createUser(request, response));
          get("/:nickname", (request, response) -> userService.getUser(request, response));
        });
    path(
        "/post",
        () -> {
          get("", (request, response) -> postService.getAllPosts(request, response));
          post("", (request, response) -> postService.createPost(request, response));
          get("/:postID", (request, response) -> postService.getPost(request, response));
        });
    path(
        "/post/:postID/comment",
        () -> {
          get("", (request, response) -> commentService.getAllComments(request, response));
          post("", (request, response) -> commentService.createComment(request, response));
          get("/:commentID", (request, response) -> commentService.getComment(request, response));
        });
  }
}
