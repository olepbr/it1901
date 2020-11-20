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
  +setupServer(int port): void
  +shutdownServer(): void
  +setupDatabase(): void
  +setDatabase(LocalDatabase database): void
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
}

UserService ..> jackson

class PostService {
  +getAllPosts(Request request, Response response): String
  +createPost(Request request, Response response): String
  +getPost(Request request, Response response): String
}

PostService ..> jackson

class CommentService {
  +getAllComments(Request request, Response response): String
  +createComment(Request request, Response response): String
  +getComment(Request request, Response response): String
}

CommentService ..> jackson

component jackson {
}

component Spark {
}

component core {
}
```

## REST API

Message and error responses have the following structure:

```json
{
  "message": "string",
  "error": "string"
}
```

### `GET /`

Returns welcome message.

```
Welcome to mememedb!
```

### `GET /user`

Retrieves a list of all the users in the database.

Responds with the following json structure:

```json
[
  {
    "nickname": "string",
    "name": "string",
    "email": "string",
    "posts":
      ["uuid", "uuid", ..., "uuid"],
    "password": "string"
  }
]
```

### `POST /user`

Creates a new user in the database.

If the nickname already exists, an error is returned.

Expects a body with the following structure:

```json
{
  "nickname": "string",
  "name": "string",
  "email": "string",
  "password": "string"
}
```

### `GET /user/:nickname`

Retrieves the user with the nickname `nickname`.

If the user does not exists, a 404 response is returned with a message
explaining why.

Responds with the following structure:

```json
{
  "nickname": "string",
  "name": "string",
  "email": "string",
  "posts":
    ["uuid", "uuid", ..., "uuid"],
  "password": "string"
}
```

### `GET /post`

Retrieves all posts.

Responds with the following structure:

```json
[
  {
    "uuid": "string",
    "owner": "string",
    "caption": "string",
    "image": "string",
    "comments": [
      {
        "uuid": "string",
        "author": "user nickname",
        "text": "string"
      }
    ]
  }
]
```

### `POST /post`

Creates a new post in the database.

Responds with a message and/or an error.

Expects a body with the following structure:

```json
{
  "owner": "string",
  "caption": "string",
  "image": "string"
}
```

### `GET /post/:postUUID`

Retrieves a specific post.

Responds with status code 404 and a message and/or an error if the post does not
exists.

On success, responds with the following structure:

```json
{
  "uuid": "string",
  "owner": "string",
  "caption": "string",
  "image": "string",
  "comments": [
    {
      "uuid": "string",
      "author": "user nickname",
      "text": "string"
    }
  ]
}
```

### `GET /post/:postUUID/comment`

If postUUID does not exist 404 and error message.

Returns:

```json
[
  {
    "uuid": "string",
    "author": "nickname",
    "text": "string"
  }
]
```

### `POST /post/:postUUID/coment`

Responds with message and/or error.

404 if postUUID does not exist.

Body:

```json
{
  "author": "nickname",
  "text": "string"
}
```

### `GET /post/:postUUID/comment/:commentUUID`

Returns:

```json
{
  "author": "nickname",
  "text": "string"
}
```
