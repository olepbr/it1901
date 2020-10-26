package mememedb.restserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.datastructures.Database;
import core.io.LocalIO;
import java.io.IOException;
import mememedb.restapi.DatabaseService;
import mememedb.restapi.MememeObjectMapperProvider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class MememeConfig extends ResourceConfig {

  public MememeConfig() {
    this(createDefaultDatabase());
  }

  private static Database readValue(final String json) {
    try {
      Database database =
          new MememeObjectMapperProvider()
              .getContext(ObjectMapper.class)
              .readValue(json, Database.class);
      System.out.println("Read " + json + " as " + database);
      return database;
    } catch (final Exception e) {
      System.out.println("Exception when reading " + json + ": " + e);
    }
    return null;
  }

  public MememeConfig(final String json) throws IOException {
    this(readValue(json));
  }

  /**
   * Initialize this MememeConfig so the server starts out with the provided Database.
   *
   * @param database the initial Database
   */
  public MememeConfig(final Database database) {
    System.out.println("Serving " + database.getUsers());
    register(DatabaseService.class);
    register(MememeObjectMapperProvider.class);
    register(JacksonFeature.class);

    register(
        new AbstractBinder() {
          @Override
          protected void configure() {
            bind(database);
          }
        });
  }

  private static Database createDefaultDatabase() {
    return new Database(new LocalIO());
  }
}
