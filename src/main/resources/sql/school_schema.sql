CREATE TABLE schools
(
    id        binary(16) PRIMARY KEY,
    name      VARCHAR(50) NOT NULL,
    nation_id BINARY(16)  NOT NULL
);

INSERT INTO schools
VALUES (UNHEX(REPLACE('c6db63ac-0d82-4c9d-b8e7-1acf8d8be438', '-', '')), '메사추세츠 공과대학교',
        UNHEX(REPLACE('bff5d3db-0f09-4c23-8418-1c00e3011ad7', '-', '')));
INSERT INTO schools
VALUES (UNHEX(REPLACE('78b3a349-c835-421f-8433-7bea83555bb8', '-', '')), '케임브리지 대학교',
        UNHEX(REPLACE('f1c12619-0145-459e-ae9f-7bf214b7a876', '-', '')));
INSERT INTO schools
VALUES (UNHEX(REPLACE('0b2c8e7e-8e6d-4993-a354-3c8586a64710', '-', '')), '취리히 연방 공과대학교',
        UNHEX(REPLACE('7d7d52c1-076c-4134-97a0-e703bb353822', '-', '')));
