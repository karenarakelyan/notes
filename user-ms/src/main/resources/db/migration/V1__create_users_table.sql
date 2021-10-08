CREATE TABLE "users"
(
    id         UUID        NOT NULL
        CONSTRAINT notes_pkey PRIMARY KEY,
    email      VARCHAR(50) NOT NULL,
    password   VARCHAR(50) NOT NULL,
    created_on TIMESTAMP   NOT NULL,
    updated_on TIMESTAMP   NOT NULL
);