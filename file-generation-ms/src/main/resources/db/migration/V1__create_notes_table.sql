CREATE TABLE "notes"
(
    id         UUID        NOT NULL
        CONSTRAINT notes_pkey PRIMARY KEY,
    user_id    UUID        NOT NULL,
    processing_type  VARCHAR(20) NOT NULL,
    created_on TIMESTAMP   NOT NULL,
    updated_on TIMESTAMP   NULL
);