[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.idi.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2015/gr2015)

# MEMEMEDB

![MEMEME LOGO](mememedb.png)

MEMEMEDB is a meme sharing application.
With this application users may view, share, and comment on memes.
A meme consists of a picture and a caption.
Example memes can be found in the project directory in the folder **memes**.
By default, the application has a default user, the credentials of which are:
username: **xXx_gertrude_xXx**, password: **strawberryjam**.

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
* `exec:java -Dexec.arguments="rest"` - Runs the app using a rest server for persistence

## Code Organization

The code is organized by component,
and has the following directory structure:

* **`<module>/src/<module>/`** - contains the module code
* **`<module>/src/<module>/<component>`** - each subcomponent (e.g json) has its own folder
* **`<module>/src/resources/`** - contains images etc.
* **`<module>/test/<module>/<component>`** - contains the tests

The following diagram shows the dependencies between the various internal and external modules

```plantuml
left to right direction

package core {
    package io
    package json
    package datastructures
    package databases
}


package restapi {
}

package spark{
}

package runner {
}

package jackson {
}

package guava{
}

package commons-validator{
}


package fxui {
}

package javafx {
    component fxml {
    }
    component controls{
    }
    component swing{
    }
    component graphics{
    }
}

restapi ..> spark
restapi ..> jackson
restapi ..> core

core .up.> jackson
core .up.> guava

runner ..> fxui
runner ..> restapi

guava -[hidden] jackson
guava -[hidden] spark
guava -[hidden] commons\-validator
jackson -[hidden] commons\-validator
javafx -[hidden] commons\-validator

fxui ..> core
fxui .up.> javafx
fxui ..> commons\-validator
```

The following class diagram shows the basic outline of the internal class structure.
Note that the `RestDatabase` corresponds with the `restapi` module, which is explained in further detail
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

## Sequence diagram

Below is a diagram showing the sequence of calls done when the user clicks on the
"post" button in the browser while using the `RestDatabase` and `restapi`.

```plantuml
actor user
participant BrowserController
participant PostController
participant RestDatabase
participant RestServer
database LocalDatabase

activate BrowserController
user -> BrowserController: Post
BrowserController -> RestDatabase: newPost
activate RestDatabase
RestDatabase -> RestServer: POST /post
activate RestServer
RestServer -> LocalDatabase: newPost
activate LocalDatabase
return
deactivate LocalDatabase
RestDatabase <-- RestServer: message
return
deactivate RestServer
deactivate RestDatabase
BrowserController -> BrowserController ++: updatePosts
BrowserController -> RestDatabase: getPosts
activate RestDatabase
RestDatabase -> RestServer: GET /post
activate RestServer
RestServer -> LocalDatabase: getPosts
activate LocalDatabase
RestServer <-- LocalDatabase: posts
deactivate LocalDatabase
RestDatabase <-- RestServer: posts
deactivate RestServer
BrowserController <-- RestDatabase: posts
deactivate RestDatabase
BrowserController -> PostController ** : new PostController
activate PostController
BrowserController -> PostController: setPost
return
return
deactivate BrowserController
```

## Storage method

This app uses an implicit storage method, where the user is not directly informed of how or where data is stored.
Implicit storage is used as it allows multiple users to interact with a shared database, which is an integral part of our idea, even though only local storage is supported at the moment.

## External dependencies

### Junit

Junit was used as the framework for writing tests.

### Mockito

We used Mockito to mock dependencies during testing.
This was especially useful when we made unit tests for the rest api and the rest
client (which we have called `RestDatabase`).

### Spark

Spark is a framework we used to create the rest api and server.
Spark uses an embedded Jetty web server.

### Jackson

Jackson provides json serializing and deserializing.

### Guava

Guava is a google library that provides password hashing.

### commons-validator

Commons validator is a library provided by apache.
We use it to validate e-mail addresses.

## Tools

### Spotbugs

Spotbugs has proved to be a valuable tool in discovering bad practices.

However, spotbugs had one false positive in restapi.
It complained about a field not being protected that was declared
protected.
Therefore, we decided to exclude this pattern in `spotbugs-exclude.xml`.

### Checkstyle

We used checkstyle to help format our code.
This has helped us write code in a more consistent style.

We added one extra rule to the standard google checkstyle specification
to allow the variable names `UUID` and `IO`.

### Jacoco

We used jacoco to generate test coverage reports.

To generate an aggregated report,
we had to add a separate report module that depends on all the other modules.
This is because the final aggregated report has to compile after all the reports
for the individual modules.
