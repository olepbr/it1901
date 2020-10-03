module core {
  requires com.fasterxml.jackson.core;
  requires com.fasterxml.jackson.databind;
  requires guava;

  exports core.io;
  exports core.json;
  exports core.datastructures;
}
