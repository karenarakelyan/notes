ALTER TABLE users
    DROP COLUMN password,
    ALTER COLUMN updated_on DROP NOT NULL;

INSERT INTO users (id, email, created_on, updated_on)
VALUES ('27df0c5c-f491-4520-bdab-e3bd798fb3c5', 'test@bestnotes.com', '2021-10-09 14:36:41.000000',
        '2021-10-09 14:36:45.000000');
INSERT INTO users (id, email, created_on, updated_on)
VALUES ('7fd13c41-2906-4ada-8bff-818cf70a8519', 'user1@bestnotes.com', '2021-10-09 15:56:48.000000',
        '2021-10-09 14:36:45.000000');
INSERT INTO users (id, email, created_on, updated_on)
VALUES ('a4ba2ccb-031d-4fac-8dcf-dee8572898b6', 'user2@bestnotes.com', '2021-10-09 15:56:50.000000',
        '2021-10-09 14:36:45.000000');
INSERT INTO users (id, email, created_on, updated_on)
VALUES ('9c9f8e05-2a28-47ce-a42f-52c4000fa64b', 'user3@bestnotes.com', '2021-10-09 15:56:52.000000',
        '2021-10-09 14:36:45.000000');
INSERT INTO users (id, email, created_on, updated_on)
VALUES ('39e40db1-fb02-4b7b-8be5-7e81f1819c3c', 'user4@bestnotes.com', '2021-10-09 15:56:54.000000',
        '2021-10-09 14:36:45.000000');
INSERT INTO users (id, email, created_on, updated_on)
VALUES ('a93607f2-abef-4b1d-b993-78255203a263', 'user5@bestnotes.com', '2021-10-09 15:56:55.000000',
        '2021-10-09 14:36:45.000000');