module fxui {
  requires javafx.fxml;
  requires javafx.controls;
  requires transitive javafx.graphics;
  requires com.fasterxml.jackson.databind;
  requires com.fasterxml.jackson.core;
  requires commons.validator;

  requires core;

  exports fxui;

  opens fxui to javafx.fxml;
}
