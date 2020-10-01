module it1901.mememedb.core {
  requires com.fasterxml.jackson.core;
  requires com.fasterxml.jackson.databind;
  requires guava;

  exports it1901.mememedb.core.io;
  exports it1901.mememedb.core.json;
  exports it1901.mememedb.core.datastructures;
  exports core.datastructures;
}
