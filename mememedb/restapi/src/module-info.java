module restapi {
  requires com.fasterxml.jackson.databind;
  requires com.fasterxml.jackson.core;
  requires spark.core;
  requires transitive core;

  exports restapi;
}
