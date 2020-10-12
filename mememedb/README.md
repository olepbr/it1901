[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.idi.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2015/gr2015)
# MEMEMEDB

MEMEMEDB is a meme sharing application.
With this application users may view, share, post and comment on memes.
A meme consists of a picture and a caption.

## Code Organization

The code is organized by component,
and has the following directory structure:

* **`<module>/src/<module>/`** - contains the module code
* **`<module>/src/<module>/<component>`** - each subcomponent (e.g json) has its own folder
* **`<module>/src/resources/`** - contains images etc.
* **`<module>/test/<module>/<component>`** - contains the tests

The following class diagram shows the basic outline of the code structure:

![Class Diagram](ClassDiagram.png)
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

BrowserController --> PostController

class PostController {
}

AppController --> BrowserController

class BrowserController {
}

AppController --> LoginController

class LoginController {
}

interface IO {
    + getPostList(): List<Post>
    + savePost(Post): void
    + saveImage(File): void
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
    + addPost(Post): Void
}

class Post {
    - caption: String
    - image: String
}

class Database {
}

User "1" <--> "*" Post : Owner
Database "storage" --> IO
Database --> "*" User
```

## User Stories
* I want to be able to post a picture to the app, and see it appear on the main page
* I want to be able to create and log in with my account

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
* `javafx:run` - Run the javafx app from fxui directory.
* `javadoc:aggregate` - Generate javadocs for the project
