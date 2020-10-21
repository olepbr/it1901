package mememedb.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import core.json.MememeModule;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MememeObjectMapperProvider implements ContextResolver<ObjectMapper> {

  private final ObjectMapper objectMapper;

  public MememeObjectMapperProvider() {
    objectMapper = new ObjectMapper().registerModule(new MememeModule());
  }

  @Override
  public ObjectMapper getContext(final Class<?> type) {
    return objectMapper;
  }
}
