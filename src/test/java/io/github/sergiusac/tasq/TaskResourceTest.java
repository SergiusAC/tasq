package io.github.sergiusac.tasq;

import io.quarkus.test.junit.QuarkusTest;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class TaskResourceTest {

    @Test
    public void testGetSingleTaskEndpoint() {
        given()
          .when().get("/tasks")
          .then()
             .statusCode(200)
             .body(CoreMatchers.notNullValue());
    }

}