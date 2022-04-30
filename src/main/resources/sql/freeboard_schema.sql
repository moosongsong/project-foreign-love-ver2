CREATE TABLE free_board
(
    id         binary(16)   NOT NULL,
    title      VARCHAR(128) NOT NULL,
    content    text         NOT NULL,
    user_id    BINARY(16)   NOT NULL,
    image_url  VARCHAR(500) DEFAULT NULL,
    created_at datetime(6)  NOT NULL,
    updated_at datetime(6)  DEFAULT NULL,
    deleted_at datetime(6)  DEFAULT NULL
);

INSERT INTO free_board
VALUES (UNHEX(REPLACE('739f1169-2064-4df3-98c1-7b03960fbed6', '-', '')), '비올라 비행기 들고 탈 수 있나요?', '아니 비올라 분명 비행기 탑승 가능하다고 했는데, 전화해보니 담당자가 가져와봐야 안다고 하더라구요.', UNHEX(REPLACE('efc2140f-3d03-4cde-b0f0-9e8eaccdf039', '-', '')),
        'https://picsum.photos/900/600/?random', NOW(), null, null);
INSERT INTO free_board
VALUES (UNHEX(REPLACE('de7714e7-c127-4782-99da-5a6fed147fde', '-', '')), '아까 비올라 물어봤던 사람인데', '내꺼 17인치라서 안된대.... 그냥 좌석 하나 더 사야할듯...', UNHEX(REPLACE('efc2140f-3d03-4cde-b0f0-9e8eaccdf039', '-', '')),
        'https://picsum.photos/900/600/?random', NOW(), null, null);

CREATE VIEW freeboard_view as
SELECT
    free_board.id AS 'id', free_board.title AS 'title', free_board.content AS 'content', free_board.image_url AS 'image_url',
    free_board.created_at AS 'created_at', free_board.updated_at AS 'updated_at', free_board.deleted_at AS 'deleted_at',
    free_board.user_id AS 'user_id', users.email AS 'user_email', users.name AS 'user_name', users.password AS 'user_password', users.nickname AS 'user_nickname',
    users.image_url AS 'user_image_url', users.created_at AS 'user_created_at', users.updated_at AS 'user_updated_at', users.deleted_at AS 'user_deleted_at',
    users.school_id AS 'school_id', schools.name AS 'school_name', schools.nation_id AS 'nation_id', nations.name AS 'nation_name'
FROM free_board LEFT JOIN users ON free_board.user_id = users.id
                LEFT JOIN schools ON users.school_id = schools.id LEFT JOIN nations ON schools.nation_id = nations.id;