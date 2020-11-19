[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.idi.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2015/gr2015)
# MEMEMEDB

MEMEMEDB is a meme sharing application.
With this application users may view, share, and comment on memes.
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
skinparam linetype ortho

component core {
	package core.io
	package core.json
	package core.datastructures
}

component restapi {
    package restapi.main
}

component runner {
    package main
}

component jackson {
}

component guava{
}

component org.apache.commons.validator{
}

component spark{
}

restapi ..> jackson
restapi ..> spark
restapi ..> core.datastructures
restapi .left.> core.io
restapi ..> core.json

core.json ..> jackson
core.datastructures .down.> core.io
core.datastructures ..> restapi.main
core.io .up.> core.datastructures
core.io ..> core.json
core.datastructures .left.> guava
runner ..> fxui
runner ..> restapi


component fxui {
	package fxui.fxui
}

fxui ..> core.datastructures
fxui .left.> org.apache.commons.validator

component javafx {
	component fxml {
	}
	component controls{
	}
	component swing{
	}
	component graphics{
	}
}

fxui.fxui .right.> javafx
```

The following class diagram shows the basic outline of the internal class structure. 
Note that the RestDatabase corresponds with the restapi module, which is explained in further detail
in the module's own [README](restapi/README.md):

```plantuml
package fxui <<Frame>> {
    class App
    class AppController
    class PostController
    class BrowserController
    class LoginController
    class PostViewController
}

package core.io <<Frame>> {
    interface IO
    class LocalIO
}

package restapi <<Frame>> {
}


package core.json <<Frame>> {
    class MememeModule
    class PostSerializer
    class PostDeserializer
    class UserSerializer
    class UserDeserializer
    class CommentSerializer
    class CommentDeserializer
    class DatabaseSerializer
    class DatabaseDeserializer
}

package core.datastructures <<Frame>> {
    class User
    class Post
    class Comment
    class DatabaseFactory
    interface DatabaseInterface
    class LocalDatabase
    class RestDatabase
}
RestDatabase -> restapi
LocalDatabase -[hidden]right-> RestDatabase

class App {
    + start(Stage) void
}

App --> AppController

class AppController {
    - database: DatabaseInterface
    - activeUser: User
    + handleLogin(): void
    + handleLogout(): void
}

BrowserController --> PostController

class PostController {
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

PostController -> PostViewController

class PostViewController {
    + addComment(): void
}

interface IO {
    + getDatabase(): LocalDatabase
    + saveDatabase(Post): void
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
MememeModule --> DatabaseSerializer
MememeModule --> DatabaseDeserializer
MememeModule --> CommentSerializer
MememeModule --> CommentDeserializer

AppController "activeUser"--> User
AppController -> DatabaseFactory
PostController "post" --> Post

class User {
    - id: int
    - name: String
    - nickname: String
    - email: String
    - posts: List<String>
    + addPost(String): void
}

class Post {
    - uuid: String
    - caption: String
    - image: String
    - owner: String
    + addComment(Comment): void
}

class Comment {
    - uuid: String
    - text: String
    - author: String
}

interface DatabaseInterface{
    + newPost(String, String, File): void
    + newUser(String, String, String, String): void
    + newComment(String, String, String)
    + getUser(String): User
    + getPosts(): Collection<Post>
    + getPost(String): Post
    + getComments(String): Collection<Comment>
    + tryLogin(String, String): User
    + usernameExists(String): boolean
}

class LocalDatabase implements DatabaseInterface
class RestDatabase implements DatabaseInterface
DatabaseFactory -> DatabaseInterface


class LocalDatabase {
    + saveToStorage(): void
}

class DatabaseFactory {
    + getDatabase(String): DatabaseInterface
}

Post "comments" <--> "*" Comment
LocalDatabase "storage" --> IO
LocalDatabase "users" --> "*" User
LocalDatabase "posts" --> "*" Post
```

## User Stories

* As a content creator, I want to be able to post a picture with a caption to the app, and see it appear in the app
* As a content creator, I want to have a place to store my memes
* I want to be able to create and log in with my account
* As a person who enjoys memes, I want to be able to view and comment on other people's posts
* I want to be able to log out of my account when I need to

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
* `javadoc:aggregate` - Generate javadocs for the project
* `exec:java -Dexec.arguments="local"` - Runs the app using a local database for persistence
* `exec:java -Dexec.arguments="rest"` - Runs the app using a restserver for persistence