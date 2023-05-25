DROP DATABASE IF EXISTS mediscreen;

CREATE DATABASE mediscreen;

USE mediscreen;

DROP TABLE IF EXISTS patient;
CREATE TABLE patients
(
    id         int         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    last_name  VARCHAR(50) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    birthdate  VARCHAR(10) NOT NULL,
    sex        VARCHAR(1)  NOT NULL,
    address    VARCHAR(200),
    phone      VARCHAR(30)
);

INSERT INTO patients (last_name, first_name, birthdate, sex, address, phone)
VALUES ('TestNone', 'Test', '1966-12-31', 'F', '1 Brookside St', '100-222-3333'),
       ('TestBorderline', 'Test', '1945-06-24', 'M', '2 High St', '200-333-4444'),
       ('TestInDanger', 'Test', '2004-06-18', 'M', '3 Club Road', '300-444-5555'),
       ('TestEarlyOnset', 'Test', '2002-06-28', 'F', '4 Valley Dr', '400-555-6666');