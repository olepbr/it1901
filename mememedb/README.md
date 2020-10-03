[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.idi.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2015/gr2015)
# MEMEMEMDB

MEMEMEDB is a meme sharing application.
With this application users may view, share, post and comment on memes.
A meme consists of a picture and a caption,
and users may add comments to the posted meme.

## Code Organization

The code is organized by component,
and has the following directory structure:

* **`src/mememedb/`** - contains the main code
* **`src/mememedb/<component>`** - each subcomponent (e.g json) has its own folder
* **`src/resources/`** - contains images etc.
* **`test/mememedb/`** - contains the tests

The following class diagram shows the outline of the code structure:

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
}

AppController --> PostController

class PostController {
}

AppController --> BrowserController

class BrowserController {
}

AppController --> LoginController

class LoginController {
}

AppController --> IO
PostController --> IO
BrowserController --> IO

interface IO {
    + getPostList(): List<Post>
    + savePost(Post): void
    + saveImage(File): void
}

class LocalIO implements IO
IO --> Post
LocalIO --> MememeModule

class MememeModule{
    + serializeUser(User): String
    + deserializeUser(String): User
    + serializePost(Post): String
    + deserializePost(String): Post
}

MememeModule --> User
MememeModule --> Post
MememeModule --> PostSerializer
MememeModule --> PostDeserializer
MememeModule --> UserSerializer
MememeModule --> UserDeserializer

UserListSerializer --> User
UserSerializer --> User
UserSerializer --> Post
UserDeserializer --> User
UserDeserializer --> Post
PostSerializer --> Post
PostDeserializer --> Post

AppController --> User
AppController --> Post
AppController --> Database
BrowserController --> User
BrowserController --> Post
BrowserController --> Database
LoginController --> User
LoginController --> Database
PostController --> User
PostController --> Post
PostController --> Database

class User {
    - id: int
    - name: String
    - nickname: String
    - email: String
    + addPost(Post): Void
}

class Post {
    - caption: String
    - image: String
}

class Database {
}

User "1" <--> "*" Post : Owner
Database --> IO
Database --> UserListSerializer
```

## User Story
I want to be able to post a picture to the app, and see it appear on the main page

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
* `javafx:run` - Run the javafx app.
* `javadoc:aggregate` - Generate javadocs for the project
