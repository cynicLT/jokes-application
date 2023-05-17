package org.cynic.jokes.e2e;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import java.net.URL;
import org.cynic.jokes.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("it")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class ItConfiguration extends Configuration {

    @LocalServerPort
    private int port;

    @Value("${feign-client.chuck-norris.url}")
    private URL api;

    public WireMockServer wireMockServer;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        wireMockServer = new WireMockServer(api.getPort());
        wireMockServer.start();
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }
}
