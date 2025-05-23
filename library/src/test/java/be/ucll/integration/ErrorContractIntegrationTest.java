package be.ucll.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ErrorContractIntegrationTest {

    @Autowired private WebTestClient web;

    @Test
    void postingInvalidUser_returnsUnifiedErrorBody() {
        String json = """
            {
              "name"    : "x",
              "password": "short",
              "email"   : "notanemail",
              "age"     : 150
            }""";

        web.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.error").isEqualTo("Validation failed")
                .jsonPath("$.message").exists()
                .jsonPath("$.path").isEqualTo("/users")
                .jsonPath("$.timestamp").exists();
    }
}
