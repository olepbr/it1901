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
