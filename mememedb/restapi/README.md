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

## REST API

Message and error responses have the following structure:

```json
{
  "message": "string",
  "error": "string"
}
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
      ["UUID", "UUID", ..., "UUID"],
    "hashedPassword": "string"
  }
]
```

### `POST /user`

Creates a new user in the database.

If the username already exists, an error is returned.

Expects a body with the following structure:

```json
{
  "nickname": "string",
  "name": "string",
  "email": "string",
  "hashedPassword":  "string"
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
    ["UUID", "UUID", ..., "UUID"],
  "hashedPassword": "string"
}
```

### `PUT /user/:nickname`

Updates a user.

Responds with a message and/or an error.

Expects a body with the following structure:

```json
{
  "nickname": "string",
  "name": "string",
  "email": "string",
  "hashedPassword": "string"
}
```

### `DELETE /user/:nickname`

Deletes a user.

Responds with a message and/or an error.

### `GET /post`

Retrieves all posts.

Responds with the following structure:

```json
[
  {
    "UUID": "string",
    "title": "string",
    "caption": "string",
    "image": "string",
    "comments":
      [
        {
          "UUID": "string",
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
[
  {
    "title": "string",
    "caption": "string",
    "image": "string"
  }
]
```

### `GET /post/:postUUID`

Response:

```json
{
  "UUID": "string",
  "title": "string",
  "caption": "string",
  "image": "string",
  "comments":
    [
      {
        "UUID": "string",
        "author": "user nickname",
        "text": "string"
      }
    ]
}
```

### `PUT /post/:postUUID

Updates post.

Responds with message and/or error.

body:

```json
{
  "UUID": "string",
  "title": "string",
  "caption": "string",
  "image": "string"
}
```

### `DELETE /post/:postUUID

Returns message and/or error.

### `GET /post/:postUUID/comment`

If postUUID does not exist 404.

Returns:

```json
[
  {
    "UUID": "string",
    "author": "user nickname",
    "text": "string"
  }
]
```

### `POST /post/:postUUID/coment

Responds with message and/or error.

404 if postUUID does not exist.

Body:

```json
{
  "author": "user nickname",
  "text": "string"
}
```

### `GET /post/:postUUID/comment/:commentUUID`


### `PUT /post/:postUUID/comment/:commentUUID`

body:

```json
{
  "UUID": "string",
  "author": "user nickname",
  "text": "string"
}
```

### `DELETE /post/:postUUID/comment/:commentUUID`

Deletes comment.
