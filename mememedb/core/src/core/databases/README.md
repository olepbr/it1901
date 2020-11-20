# Databases
The database package represents the root data structure in the project, and the implemented objects are used by the UI layers to store and interact with data.
The database classes in mememedb all implement the **DatabaseInterface** interface, which is used by the fxui package to communicate with the app.
The app implements two main types of database, the **RestDatabase**, and the **LocalDatabase**:

## **DatabaseInterface**
The main method structure of the database, used by the **fxui** objects to create and store data.
The interface allows the storage and data modules to be changed without needing to change the fxui, so long as the methods defined are kept in working order.

## **LocalDatabase**
The **LocalDatabase** is the root object of the projects datastructure, storing and managing both the users of the app, and their posts and comments.
It has the ability to interface with the persistence layer using an **IO** object, or can alternatively be constructed as an independent volatile object.

## **RestDatabase**
The **RestDatabase** acts as a virtual database, storing no data on its own, instead corresponding with a REST server containing the actual data.

## **DatabaseFactory**
The **DatabaseFactory** acts as a factory class for the **DatabaseInterface**, and can construct and return any of the implemented databases for use in the app.