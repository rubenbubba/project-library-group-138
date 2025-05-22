package be.ucll.integration;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
class LibraryIntegrationTest {

    @Autowired
    private WebTestClient web;

    /* ---------- Users ---------- */

    @Test
    void getAllUsers_returns200AndNonEmptyArray() {
        web.get().uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isNumber()
                .jsonPath("$.length()").value(n -> Assertions.assertTrue(((Integer) n) >= 3));
    }

    @Test
    void getAdults_onlyAdultsInResponse() {
        web.get().uri("/users/adults")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[*].age").value(list ->
                        ((java.util.List<?>) list).forEach(a -> Assertions.assertTrue(((Integer) a) >= 18)));
    }

    @Test
    void getUsersInAgeRange_returnsWithinBounds() {
        web.get().uri("/users/age/16/25")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[*].age").value(list ->
                        ((java.util.List<?>) list).forEach(a ->
                                Assertions.assertTrue(((Integer) a) >= 16 && ((Integer) a) <= 25)));
    }

    @Test
    void getUsersByName_filtersCaseInsensitive() {
        web.get().uri("/users?name=ali")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].name").isEqualTo("Alice");
    }

    /* ---------- Publications ---------- */

    @Test
    void getPublications_titleFilterWorks() {
        web.get().uri("/publications?title=harry")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].title").value(v -> Assertions.assertTrue(v.toString().toLowerCase().contains("harry")));
    }

    @Test
    void getPublications_typeFilterWorks() {
        web.get().uri("/publications?type=magazine")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[*].editor").exists();   // field only present on magazines
    }

    @Test
    void getPublications_stockFilterWorks() {
        web.get().uri("/publications/stock/5")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[*].numberOfCopies").value(list ->
                        ((java.util.List<?>) list).forEach(c -> Assertions.assertTrue(((Integer) c) >= 5)));
    }

    /* ---------- Loans ---------- */

    @Test
    void getLoansForUser_returnsArray() {
        web.get().uri("/users/bob@ucll.be/loans")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray();
    }

    @Test
    void deleteLoansForUser_thenListIsEmpty() {
        // delete
        web.delete().uri("/users/bob@ucll.be/loans")
                .exchange()
                .expectStatus().isOk();

        // verify
        web.get().uri("/users/bob@ucll.be/loans")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("[]");
    }

    /* ---------- User deletion ---------- */

    @Test
    void deleteUser_completelyRemovesUser() {
        // delete Alice (loans auto-deleted first by controller)
        web.delete().uri("/users/alice@ucll.be")
                .exchange()
                .expectStatus().isOk();

        // verify user list no longer contains Alice
        web.get().uri("/users?name=alice")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("[]");
    }
    @Test
    void filterByInterest_returnsAliceAndDave() {
        web.get().uri("/users/interests?interest=java")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2);
    }

    @Test
    void interestAgeSorted_returnsDaveThenAlice() {
        web.get().uri("/users/interests/location?interest=java&age=30")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].name").isEqualTo("Dave")
                .jsonPath("$[1].name").isEqualTo("Alice");
    }

}
