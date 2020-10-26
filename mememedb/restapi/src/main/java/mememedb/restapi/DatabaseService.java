package mememedb.restapi;

import core.datastructures.Database;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(DatabaseService.DATABASE_SERVICE_PATH)
public class DatabaseService {

  public static final String DATABASE_SERVICE_PATH = "database";

  @Inject private Database database;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Database getDatabase() {
    return database;
  }
}
