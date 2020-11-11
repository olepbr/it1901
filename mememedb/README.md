[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.idi.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2015/gr2015)
# MEMEMEDB

MEMEMEDB is a meme sharing application.
With this application users may view, share, and post memes.
A meme consists of a picture and a caption.
Example memes can be found in the project directory in the folder **memes**.
By default, the application has a default user, the credentials of which are:
username: **xXx_gertrude_xXx**, password: **strawberryjam**.

## Code Organization

The code is organized by component,
and has the following directory structure:

* **`<module>/src/<module>/`** - contains the module code
* **`<module>/src/<module>/<component>`** - each subcomponent (e.g json) has its own folder
* **`<module>/src/resources/`** - contains images etc.
* **`<module>/test/<module>/<component>`** - contains the tests

The following diagram shows the dependencies between the various internal and external modules

```plantuml
component core {
	package core.io
	package core.json
	package core.datastructures
}

component jackson {
}

component guava{
}

component org.apache.commons.validator{
}

core.json ..> jackson
core.datastructures ..> core.io
core.io ..> core.datastructures
core.io ..> core.json
core.datastructures ..> guava

component fxui {
	package fxui.fxui
}

fxui ..> core.datastructures
fxui ..> core.io
fxui ..> org.apache.commons.validator

component javafx {
	component fxml {
	}
}

fxui ..> javafx
fxui ..> fxml
```

The following class diagram shows the basic outline of the internal class structure:

```plantuml
package fxui <<Frame>> {
    class App
    class AppController
    class PostController
    class BrowserController
    class LoginController
}

package core.io <<Frame>> {
    interface IO
    class LocalIO
}

package core.json <<Frame>> {
    class MememeModule
    class PostSerializer
    class PostDeserializer
    class UserSerializer
    class UserDeserializer
    class UserListSerializer
}

package core.datastructures <<Frame>> {
    class User
    class Post
    class Database
}

class App {
    + start(Stage) void
}

App --> AppController

class AppController {
    - database: Database
    - activeUser: User
    + handleLogin(): void
    + handleLogout(): void
}

BrowserController --> PostController

class PostController {
    - post: Post
}

AppController --> BrowserController

class BrowserController {
    + handleAddContent(): void
    + updatePosts(): void
    + handleLogout(): void
}

AppController --> LoginController

class LoginController {
    + login(): void
    + registerClick(): void
    + createUser(): void
}

interface IO {
    + getDatabase(): Database
    + saveDatabase(Post): void
    + saveImage(File): void
    + getImage(String): File
}

class LocalIO implements IO
LocalIO --> MememeModule

class MememeModule{
    + serializeUser(User): String
    + deserializeUser(String): User
    + serializePost(Post): String
    + deserializePost(String): Post
}

MememeModule --> PostSerializer
MememeModule --> PostDeserializer
MememeModule --> UserSerializer
MememeModule --> UserDeserializer
MememeModule --> UserListSerializer

AppController "activeUser"--> User
AppController "database"--> Database
PostController --> Post

class User {
    - id: int
    - name: String
    - nickname: String
    - email: String
    - posts: List<Post>
    + addPost(Post): Void
}

class Post {
    - caption: String
    - image: String
    - owner: String
}

class Database {
    - users: List<User>
    - storage: IO
    + saveToStorage(): void
    + savePost(Post, File, User): void
    + saveUser(User): void
    + getPostList(): List<Post>
    + getUsers(): List<User>
    
}

User "1" <--> "*" Post : Owner
Database "storage" --> IO
Database "users" --> "*" User
```

## User Stories

* I want to be able to post a picture to the app, and see it appear on the main page
* I want to be able to create and log in with my account

## Storage method

This app uses an implicit storage method, where the user is not directly informed of how or where data is stored.
Implicit storage is used as it allows multiple users to interact with a shared database, which is an integral part of our idea, even though only local storage is supported at the moment.

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

### `GET /user/{nickname}/post`

Retrieves all posts made by the given user.

If the user does not exists, a 404 response is returned with a message
explaining why.

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

## Maven Goals

* `validate` - Validate the project is correct
and all necessary information is available.
* `compile` - Compile the source code of the project.
* `test` - Test the compiled source code.
* `verify` - Run any checks on results of integration tests
to ensure quality criteria are met.
The goal `jacoco:check` runs as part of this goal.
* `jacoco:check` - Checks that the code coverage metrics are being met.
* `jacoco:report` - Generates test coverage report to `target/site/jacoco`.
* `javafx:run -f fxui/pom.xml` - Run the javafx app.
* `javadoc:aggregate` - Generate javadocs for the project
