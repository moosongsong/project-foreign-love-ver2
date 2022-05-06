CREATE TABLE comments
(
    id         binary(16)  NOT NULL,
    content    text        NOT NULL,
    board_id   BINARY(16)  NOT NULL,
    user_id    BINARY(16)  NOT NULL,
    created_at datetime(6) NOT NULL,
    deleted_at datetime(6) DEFAULT NULL
);

INSERT INTO comments
VALUES (UNHEX(REPLACE('739f1169-2064-4df3-98c1-7b03960fbed6', '-', '')), '비올라 비행기 들고 탈 수 있나요?',
        UNHEX(REPLACE('ced6f153-cbd8-11ec-ad03-179d00adf880', '-', '')),
        UNHEX(REPLACE('b0071072-cbd8-11ec-ad03-55a871cd9d5d', '-', '')), NOW(), null);
INSERT INTO comments
VALUES (UNHEX(REPLACE('de7714e7-c127-4782-99da-5a6fed147fde', '-', '')), '비아 하기 싫다나요?',
        UNHEX(REPLACE('ced6f153-cbd8-11ec-ad03-179d00adf880', '-', '')),
        UNHEX(REPLACE('efc2140f-3d03-4cde-b0f0-9e8eaccdf039', '-', '')), NOW(), null);

CREATE VIEW comments_view as
SELECT comments.id         AS 'id',
       comments.content    AS 'content',
       comments.board_id   AS 'board_id',
       comments.created_at AS 'created_at',
       comments.deleted_at AS 'deleted_at',
       comments.user_id    AS 'user_id',
       users.email         AS 'user_email',
       users.name          AS 'user_name',
       users.password      AS 'user_password',
       users.nickname      AS 'user_nickname',
       users.image_url     AS 'user_image_url',
       users.created_at    AS 'user_created_at',
       users.updated_at    AS 'user_updated_at',
       users.deleted_at    AS 'user_deleted_at',
       users.school_id     AS 'school_id',
       schools.name        AS 'school_name',
       schools.nation_id   AS 'nation_id',
       nations.name        AS 'nation_name'
FROM comments
         LEFT JOIN users ON comments.user_id = users.id
         LEFT JOIN schools ON users.school_id = schools.id
         LEFT JOIN nations ON schools.nation_id = nations.id