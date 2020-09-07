module mememedb {
  requires javafx.fxml;
  requires transitive javafx.graphics;
  requires javafx.controls;

  exports mememedb;

  opens mememedb to javafx.fxml;
}
