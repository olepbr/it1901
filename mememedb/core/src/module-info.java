module core {
  requires com.fasterxml.jackson.core;
  requires com.fasterxml.jackson.databind;
  requires guava;
  requires java.net.http;

  exports core.io;
  exports core.json;
  exports core.datastructures;
  exports core.databases;

  opens core.datastructures to
      com.fasterxml.jackson.databind;
}
