package be.ucll.integration;

import be.ucll.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class LoanPricingIntegrationTest {

    @Autowired  private WebTestClient web;
    @Autowired  private LoanRepository loanRepo;

    @Test
    void fullFlow_priceAndFineAreCorrect() {
        /* ---- create loan ---- */
        long loanId = web.post().uri("/loans?userEmail=john@ucll.be&publicationId=1")
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").value(Integer.class::cast)
                .returnResult()
                .getResponseBodyContentAsInteger("$.id");

        /* ---- fast-forward time by manipulating DB (test only) ---- */
        loanRepo.findById((long)loanId).ifPresent(l -> {
            l.setBorrowedDate(java.time.LocalDate.now().minusDays(20));
            loanRepo.save(l);
        });

        /* ---- return loan ---- */
        web.put().uri("/loans/{id}/return", loanId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.price").isEqualTo(0.0)      // free loan consumed
                .jsonPath("$.fine").value(f ->
                        assertThat((Double)f).isEqualTo(3.0));  // 6 days late * 0.5
    }
}
