package org.cynic.jokes.e2e;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatusIT extends ItConfiguration {

    @Test
    void givenStatus_whenCalling_thenOk() {
        RestAssured.given()
                .when()
                .get("/health/status")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .assertThat()
                .body(Matchers.equalTo("OK"));
    }
}
