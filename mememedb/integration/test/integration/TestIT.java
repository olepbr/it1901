package integration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import restapi.Server;

public class TestIT {

  // Set up RestDatabase

  @BeforeAll
  public static void before() {
    // Find a way to generate an empty database before we begin?
    Server.setupDatabase();
    Server.setupServer();
  }

  @AfterAll
  public static void after() {
    Server.shutdownServer();
  }

  // The simplest test
  @Test
  public void testTrue() {
    Assertions.assertTrue(true);
  }

  // Test communication LocalIO <-> LocalDatabase <-> REST API <-> RestDatabase

  // Test creation of user
  //
  // Test verify that user was created
  //
  // Test creation of post
  //
  // Test verify that post was created
  //
  // Test creation of comment
  //
  // Test get comment that was created
  //
  // Test creation of exising user and handle error
  //
  // Test more cases with error handling
}
