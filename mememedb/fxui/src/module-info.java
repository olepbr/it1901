module it1901.mememedb.fxui {
  requires javafx.fxml;
  requires javafx.controls;
  requires transitive javafx.graphics;
  requires com.fasterxml.jackson.databind;
  requires com.fasterxml.jackson.core;
  requires commons.validator;

  requires it1901.mememedb.core;

  exports it1901.mememedb.fxui;

  opens it1901.mememedb.fxui to javafx.fxml;
}
