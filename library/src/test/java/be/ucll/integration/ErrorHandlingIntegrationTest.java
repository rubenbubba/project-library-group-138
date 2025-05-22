package be.ucll.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ErrorHandlingIntegrationTest {

    @Autowired
    private WebTestClient web;

    @Test
    void postUser_withShortPassword_returns400Json() {
        String body = """
                {"name":"Tiny","password":"123","email":"tiny@ucll.be","age":20}
                """;

        web.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.error").isEqualTo("Validation failed")
                .jsonPath("$.message").value(m ->
                        ((String) m).contains("Password must be at least 8 characters long."));
    }
}