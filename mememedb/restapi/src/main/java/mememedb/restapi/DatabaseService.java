package mememedb.restapi;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import core.datastructures.Database;

@Path(DatabaseService.DATABASE_SERVICE_PATH)
public class DatabaseService {

  public static final String DATABASE_SERVICE_PATH = "database";

  @Inject
  private Database database;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String getDatabase() {
    return "I am in so much pain";
  }
}
