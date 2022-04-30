CREATE TABLE nations
(
    id   binary(16) PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

INSERT INTO nations VALUES (UNHEX(REPLACE('bff5d3db-0f09-4c23-8418-1c00e3011ad7', '-', '')), '미국');
INSERT INTO nations VALUES (UNHEX(REPLACE('f1c12619-0145-459e-ae9f-7bf214b7a876', '-', '')), '영국');
INSERT INTO nations VALUES (UNHEX(REPLACE('7d7d52c1-076c-4134-97a0-e703bb353822', '-', '')), '스위스');
