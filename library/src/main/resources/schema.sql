/* ---------- CLEAN SLATE -------------------------------------------------- */
DROP TABLE IF EXISTS loans;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS profile;
DROP TABLE IF EXISTS memberships;
DROP TABLE IF EXISTS publications;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS magazines;

/* ---------- SUPPORTING TABLES ------------------------------------------- */
CREATE TABLE memberships (
                             id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                             start_date  DATE            NOT NULL,
                             end_date    DATE            NOT NULL,
                             fee_cents   INT             NOT NULL
);

CREATE TABLE profile (
                         id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                         location    VARCHAR(255),
                         interests   VARCHAR(1024)
);

/* ---------- PUBLICATION INHERITANCE  ------------------------------------ */
/* “Single-table” strategy with a discriminator column.                     */
CREATE TABLE publications (
                              id                BIGINT AUTO_INCREMENT PRIMARY KEY,
                              dtype             VARCHAR(31)      NOT NULL,     -- “Book” / “Magazine”
                              title             VARCHAR(255)     NOT NULL,
                              publication_year  INT              NOT NULL,
                              number_of_copies  INT              NOT NULL,

    /* Book-only columns */
                              author            VARCHAR(255),
                              isbn              VARCHAR(20),

    /* Magazine-only columns */
                              editor            VARCHAR(255),
                              issn              VARCHAR(20)
);

/* ---------- USER & LOAN -------------------------------------------------- */
CREATE TABLE users (
                       id             BIGINT AUTO_INCREMENT PRIMARY KEY,
                       email          VARCHAR(255) UNIQUE NOT NULL,
                       name           VARCHAR(255)        NOT NULL,
                       age            INT                 NOT NULL,
                       password       VARCHAR(255)        NOT NULL,

                       profile_id     BIGINT,
                       membership_id  BIGINT,

                       CONSTRAINT fk_user_profile
                           FOREIGN KEY (profile_id) REFERENCES profile(id),

                       CONSTRAINT fk_user_membership
                           FOREIGN KEY (membership_id) REFERENCES memberships(id)
);

CREATE TABLE loans (
                       id             BIGINT AUTO_INCREMENT PRIMARY KEY,
                       user_id        BIGINT NOT NULL,
                       publication_id BIGINT NOT NULL,
                       loan_date      DATE   NOT NULL,

                       CONSTRAINT fk_loan_user
                           FOREIGN KEY (user_id)        REFERENCES users(id),
                       CONSTRAINT fk_loan_publication
                           FOREIGN KEY (publication_id) REFERENCES publications(id)
);
