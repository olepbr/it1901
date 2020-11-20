module restapi {
  requires com.fasterxml.jackson.databind;
  requires com.fasterxml.jackson.core;
  requires transitive spark.core;
  requires transitive core;

  exports restapi;
}
