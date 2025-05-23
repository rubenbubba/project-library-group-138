package be.ucll.integration;

import be.ucll.repository.DbInitializer;
import be.ucll.repository.PublicationDbInitializer;
import be.ucll.repository.PublicationRepository;
import be.ucll.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Sql("classpath:schema.sql")          /* drop-and-create tables before every test            */
class LoanFlowIntegrationTest {

    @Autowired private WebTestClient web;
    @Autowired private DbInitializer users;
    @Autowired private PublicationDbInitializer pubs;
    @Autowired private PublicationRepository pubRepo;
    @Autowired private UserRepository userRepo;

    @BeforeEach
    void resetDb() {
        users.initialize();          // seed users & profiles
        pubs.seed();                 // seed books & magazines
    }

    /* ---------------- full happy flow ---------------- */

    @Test
    void lendAndReturn_updatesStockAndPrice() {

        Long bookId = pubRepo.findAll().get(0).getId();              // first seeded book
        String email = userRepo.findAll().get(0).getEmail();         // first seeded user

        /* ---- lend ---- */
        Map<String,String> req = Map.of("email", email,
                "publicationId", bookId.toString());

        web.post().uri("/loans")
                .bodyValue(req)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isNumber()
                .jsonPath("$.price").value(p -> assertEquals(2.0, ((Number) p).doubleValue()));

        int afterLend = pubRepo.findById(bookId).orElseThrow().getNumberOfCopies();
        assertEquals(4, afterLend);                       // seeded 5  → now 4

        /* ---- return ---- */
        Long loanId =
                web.get().uri("/loans/user/" + email)
                        .exchange()
                        .returnResult(String.class)
                        .getResponseBody()
                        .map(s -> s.replaceAll("[^0-9]", ""))     // crude: first number == id
                        .blockFirst(java.time.Duration.ofSeconds(2))
                        .isBlank() ? null : Long.valueOf(          // safeguard – should never be null
                        web.get().uri("/loans/user/" + email)
                                .exchange()
                                .expectBody()
                                .jsonPath("$[0].id").value(Integer.class::cast));

        web.put().uri("/loans/{id}/return", loanId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.returnDate").exists()
                .jsonPath("$.price").isNumber();

        int afterReturn = pubRepo.findById(bookId).orElseThrow().getNumberOfCopies();
        assertEquals(5, afterReturn);                     // stock restored
    }
}
