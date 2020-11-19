# FXUI module (fxui)

This project contains the user-interface layer for [mememedb](../README.md).

## User-interface layer

The user-interface layer contains classes for displaying the app, as well as relaying user input to the other domains.
The interface makes heavy use of the javafx framework, the fxml files that govern the visual layout are located in the * `src/resources/` folder.
The specific controller instances, as well as the main App-launcher, are located in the * `src/fxui/` folder.

## Classes

- **App** - Launches the javafx-app, initializes the AppController
- **AppController** - Mastercontroller, initializes and switches between the subcontrollers(Browser and Login)
- **LoginController** - Handles Logging in existing users and Registering new users
- **BrowserController** - Handles displaying and adding new posts
- **PostController** - Subcontroller of BrowserController, handles displaying each specific post in the browser
- **PostViewControlelr** Subcontroller of PostController, handles a window showing the contents of a specific post, as well as comment functionality
