module mememedb {
  requires javafx.fxml;
  requires transitive javafx.graphics;
  requires javafx.controls;
requires com.fasterxml.jackson.databind;
requires com.fasterxml.jackson.core;

  exports mememedb;

  opens mememedb to javafx.fxml;
}
