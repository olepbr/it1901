# Runner-module
This module contains the runner class, responsible for running the project,
and activating the REST server if using the RestDatabase.
The runner class does not contain any tests, as it is mostly a utility class for easier launching of the two main app types,
 and its code can easily be tested by running the exec:java goal.

## `Runner.main()`
This is the only method in the module, and runs the main methods of 
the restapi and fxui, in order to launch the app. 
The arguments given to it through exec:java determine which databaseType 
the fxui should use from the DatabaseFactory, and whether or not to run the REST server.
