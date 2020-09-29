module mememedb {
  requires javafx.fxml;
  requires transitive javafx.graphics;
  requires javafx.controls;
requires com.fasterxml.jackson.databind;
requires com.fasterxml.jackson.core;
requires commons.validator;

  exports mememedb;
  exports mememedb.io;
  exports mememedb.ui;
  exports mememedb.json;
  exports mememedb.datastructures;

  opens mememedb to javafx.fxml;
  opens mememedb.ui to javafx.fxml;
}
