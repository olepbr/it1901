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
package UI <<Frame>> {
    class AppController
    class PostController
    class UserController
}

package IO <<Frame>> {
    interface IO
    class CloudController
    class LocalIO
}

package JSON <<Frame>> {
    class MememeModule
}

package DataStructures <<Frame>> {
    class User
    class Post
    class Comment
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

AppController --> UserController

class UserController {
}

AppController --> IO
UserController --> IO
PostController --> IO

interface IO {
    + getPostList(): List<Post>
    + savePost(Post): void
    + saveImage(File): void
}

class CloudController implements IO
class LocalIO implements IO
CloudController --> MememeModule
LocalIO --> MememeModule

class MememeModule{
    + serializeUser(User): String
    + deserializeUser(String): User
    + serializePost(Post): String
    + deserializePost(String): Post
    + serializeComment(Comment): String
    + deserializeComment(String): Comment
}

MememeModule --> User
MememeModule --> Post
MememeModule --> Comment

UserController --> User
AppController --> Post

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
    + addComment(Comment): void
}

class Comment {
    - comment: String
    - date: String
}

User "1" <--> "*" Post : Owner
Post "*" <--> "1" Comment : Parent
Comment "1" <--> "*" User : Owner


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
