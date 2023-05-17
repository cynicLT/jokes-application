package org.cynic.jokes.e2e;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JokesIT extends ItConfiguration {

    @Test
    void givenRandom_whenCall_thenGetRandom() {
        RestAssured.given()
                .when()
                .get("/jokes/random")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .assertThat()
                .body(Matchers.equalTo("Random Joke"));
    }

    @Test
    void givenSearch_whenCallBadQuery_thenError() {
        RestAssured.given()
                .when()
                .pathParam("query", "1")
                .get("/jokes/search/{query}")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .assertThat()
                .body("code", Matchers.is("error.request.not-valid"))
                .body("values", Matchers.contains("search.query size must be between 3 and 120"));
    }

    @Test
    void givenCategory_whenCallGoodCategory_thenOk() {
        RestAssured.given()
                .when()
                .pathParam("category", "123")
                .get("/jokes/category/{category}")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .assertThat()
                .body(Matchers.equalTo("Random Joke"));
    }

    @Test
    void givenCategory_whenCallBadCategory_thenError() {
        RestAssured.given()
                .when()
                .pathParam("category", "1")
                .get("/jokes/category/{category}")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .assertThat()
                .body("code", Matchers.is("error.request.not-valid"))
                .body("values", Matchers.contains("category.category size must be between 3 and 120"));
    }

}
