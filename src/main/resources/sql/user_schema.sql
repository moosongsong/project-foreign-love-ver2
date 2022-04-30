CREATE TABLE users
(
    id         BINARY(16) PRIMARY KEY,
    email      VARCHAR(64)  NOT NULL,
    name       VARCHAR(16)  NOT NULL,
    password   VARCHAR(128) NOT NULL,
    school_id  BINARY(16)   NOT NULL,
    nickname   VARCHAR(16)  NOT NULL,
    image_url  VARCHAR(500) DEFAULT NULL,
    created_at datetime(6)  NOT NULL,
    updated_at datetime(6)  DEFAULT NULL,
    deleted_at datetime(6)  DEFAULT NULL
);

INSERT INTO users
VALUES (UNHEX(REPLACE('efc2140f-3d03-4cde-b0f0-9e8eaccdf039', '-', '')), 'songe08@gmail.com', '송무송', PASSWORD('1234') ,
        UNHEX(REPLACE('c6db63ac-0d82-4c9d-b8e7-1acf8d8be438', '-', '')), '무송', 'https://picsum.photos/300/200/?random', NOW(), null, null);

INSERT INTO users
VALUES (UNHEX(REPLACE('8d7d52c1-076c-4134-97a0-e703cc353822', '-', '')), 'kate@naver.com', '케이트', PASSWORD('1234') ,
        UNHEX(REPLACE('0b2c8e7e-8e6d-4993-a354-3c8586a64710', '-', '')), 'KATE', 'https://picsum.photos/300/200/?random', NOW(), null, null);

CREATE VIEW users_view as
SELECT users.id AS 'id', users.email AS 'email', users.name AS 'name', users.password AS 'password', users.nickname AS 'nickname',
       users.image_url AS 'image_url', users.created_at AS 'created_at', users.updated_at AS 'updated_at', users.deleted_at AS 'deleted_at',
       users.school_id AS 'school_id', schools.name AS 'school_name', schools.nation_id AS 'nation_id', nations.name AS 'nation_name'
FROM users LEFT JOIN schools ON users.school_id = schools.id LEFT JOIN nations ON schools.nation_id = nations.id;
