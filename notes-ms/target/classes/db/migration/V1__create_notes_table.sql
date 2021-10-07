CREATE TABLE "notes"
(
    id         UUID        NOT NULL
        CONSTRAINT notes_pkey PRIMARY KEY,
    user_id    UUID        NOT NULL,
    title      VARCHAR(50) NOT NULL,
    content    VARCHAR(1000) NULL DEFAULT NULL,
    created_on TIMESTAMP   NOT NULL,
    updated_on TIMESTAMP   NOT NULL
);