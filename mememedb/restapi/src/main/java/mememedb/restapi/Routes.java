package mememedb.restapi;

import static spark.Spark.*;

/** Decides what service to call based on URI. */
public class Routes {

  private static UserService userService;
  private static PostService postService;
  private static CommentService commentService;

  static {
    userService = new UserService();
    postService = new PostService();
    commentService = new CommentService();
  }

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
          put("/:nickname", (request, response) -> userService.updateUser(request, response));
          delete("/:nickname", (request, response) -> userService.deleteUser(request, response));
          get(
              "/:nickname/post",
              (request, response) -> postService.getAllPostsByUser(request, response));
        });
    path(
        "/post",
        () -> {
          get("", (request, response) -> postService.getAllPosts(request, response));
          post("", (request, response) -> postService.createPost(request, response));
          get("/:postID", (request, response) -> postService.getPost(request, response));
          put("/:postID", (request, response) -> postService.updatePost(request, response));
          delete("/:postID", (request, response) -> postService.deletePost(request, response));
        });
    path(
        "/post/:postID/comment",
        () -> {
          get("", (request, response) -> commentService.getAllComments(request, response));
          post("", (request, response) -> commentService.createComment(request, response));
          get("/:commentID", (request, response) -> commentService.getComment(request, response));
          put(
              "/:commentID",
              (request, response) -> commentService.updateComment(request, response));
          delete(
              "/:commentID",
              (request, response) -> commentService.deleteComment(request, response));
        });
  }
}
