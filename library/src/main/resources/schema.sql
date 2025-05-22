/* --------------------------------------------------------------
   USERS  &  PROFILE  &  MEMBERSHIP
   --------------------------------------------------------------*/
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS profile CASCADE;
DROP TABLE IF EXISTS membership CASCADE;

CREATE TABLE profile (
                         id         BIGINT AUTO_INCREMENT PRIMARY KEY,
                         location   VARCHAR(128),
                         interests  VARCHAR(255)
);

CREATE TABLE membership (
                            id            BIGINT AUTO_INCREMENT PRIMARY KEY,
                            start_date    DATE       NOT NULL,
                            end_date      DATE       NOT NULL,
                            free_loans    INT        NOT NULL,
                            user_id       BIGINT UNIQUE  -- 1-to-1
);

CREATE TABLE users (
                       id           BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name         VARCHAR(128)  NOT NULL,
                       password     VARCHAR(255)  NOT NULL,
                       email        VARCHAR(255)  NOT NULL UNIQUE,
                       age          INT           NOT NULL,
                       profile_id   BIGINT,
                       CONSTRAINT fk_user_profile
                           FOREIGN KEY (profile_id) REFERENCES profile(id)
);

/* hook back from membership to user */
ALTER TABLE membership
    ADD CONSTRAINT fk_member_user FOREIGN KEY (user_id) REFERENCES users(id);

/* --------------------------------------------------------------
   PUBLICATION  (single-table inheritance for Book & Magazine)
   --------------------------------------------------------------*/
DROP TABLE IF EXISTS publication CASCADE;

CREATE TABLE publication (
                             id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
                             dtype              VARCHAR(31) NOT NULL,   -- discriminator: BOOK / MAGAZINE
                             title              VARCHAR(255) NOT NULL,
                             publication_year   INT         NOT NULL,
                             number_of_copies   INT         NOT NULL,
    -- book specific
                             author             VARCHAR(255),
                             isbn               VARCHAR(20),
    -- magazine specific
                             editor             VARCHAR(255),
                             issn               VARCHAR(20)
);

/* --------------------------------------------------------------
   LOAN
   --------------------------------------------------------------*/
DROP TABLE IF EXISTS loan CASCADE;

CREATE TABLE loan (
                      id             BIGINT AUTO_INCREMENT PRIMARY KEY,
                      borrowed_date  DATE         NOT NULL,
                      returned_date  DATE,
                      user_id        BIGINT       NOT NULL,
                      publication_id BIGINT       NOT NULL,
                      CONSTRAINT fk_loan_user        FOREIGN KEY (user_id)        REFERENCES users(id),
                      CONSTRAINT fk_loan_publication FOREIGN KEY (publication_id) REFERENCES publication(id)
);
