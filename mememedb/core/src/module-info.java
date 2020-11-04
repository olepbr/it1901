module core {
  requires com.fasterxml.jackson.core;
  requires com.fasterxml.jackson.databind;
  requires guava;

  exports core.io;
  exports core.json;
  exports core.datastructures;

  opens core.datastructures to
      com.fasterxml.jackson.databind;
}
