# REST API Server

## Class diagram

```plantuml
skinparam linetype ortho

component restapi {
  class Server
  class Routes
  class UserService
  class PostService
  class CommentService
}

restapi .left.> core

class Server {
  +setup(): void
  +shutdown(): void
  #database: LocalDatabase
}

Server --> Routes
Server .right.> Spark

class Routes {
  +configureRoutes(): void
}

Routes --> UserService
Routes --> PostService
Routes --> CommentService
Routes .right.> Spark

class UserService {
  +getAllUsers(Request request, Response response): String
  +createUser(Request request, Response response): String
  +getUser(Request request, Response response): String
  +updateUser(Request request, Response response): String
  +deleteUser(Request request, Response response): String
}

UserService ..> jackson

class PostService {
  +getAllPosts(Request request, Response response): String
  +createPost(Request request, Response response): String
  +getPost(Request request, Response response): String
  +updatePost(Request request, Response response): String
  +deletePost(Request request, Response response): String
}

PostService ..> jackson

class CommentService {
  +getAllComments(Request request, Response response): String
  +createComment(Request request, Response response): String
  +getComment(Request request, Response response): String
  +updateComment(Request request, Response response): String
  +deleteComment(Request request, Response response): String
}

CommentService ..> jackson

component jackson {
}

component Spark {
}

component core {
}
```
