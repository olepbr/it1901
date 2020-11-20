# Core Module (core)

This project contains the domain and persistence layers for [mememedb](../README.md). It is split into three packages: datastructures, io and json.

## Domain Layer

The domain layer contains the classes and logic for managing the types of data handled by the application,
more specifically users, posts and comments, as well as the database classes, the application's root element who are also responsible for communicating with the persistence layer.
The structure classes are located in the **[mememedb.core.datastructures](src/core/datastructures/README.md)** package, and the database related classes are located in the **[mememedb.core.databases](src/core/databases/README.md)** package.

## Persistence Layer

The persistence layer contains the classes for saving the domain layer's data.
For this application, persistence is achieved by writing the data to file using JSON syntax.
Our persistence layer is split into two packages: the **[mememedb.core.io](src/core/io/README.md)** package, which handles writing and reading from file,
and the **[mememedb.core.json](src/core/json/README.md)** package, which contains the different classes for serialization/deserialization to/from JSON.