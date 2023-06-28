DROP DATABASE IF EXISTS mediscreen;

CREATE DATABASE mediscreen;

USE mediscreen;

DROP TABLE IF EXISTS patient;
CREATE TABLE patient
(
    id         int         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    last_name  VARCHAR(50) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    birthdate  DATE        NOT NULL,
    sex        VARCHAR(1)  NOT NULL,
    address    VARCHAR(200),
    phone      VARCHAR(30)
);

INSERT INTO patient (id, last_name, first_name, birthdate, sex, address, phone)
VALUES (1, 'TestNone', 'Test', '1966-12-31', 'F', '1 Brookside St', '100-222-3333'),
       (2, 'TestBorderline', 'Test', '1945-06-24', 'M', '2 High St', '200-333-4444'),
       (3, 'TestInDanger', 'Test', '2004-06-18', 'M', '3 Club Road', '300-444-5555'),
       (4, 'TestEarlyOnset', 'Test', '2002-06-28', 'F', '4 Valley Dr', '400-555-6666'),
       (5, 'Ferguson', 'Lucas', '1968-06-22', 'M', '2 Warren Street ', '387-866-1399'),
       (6, 'Rees', 'Pippa', '1952-09-27', 'F', '745 West Valley Farms Drive', '628-423-0993'),
       (7, 'Arnold ', 'Edward ', '1952-11-11', 'M', '599 East Garden Ave ', '123-727-2779'),
       (8, 'Sharp ', 'Anthony', '1946-11-26', 'M', '894 Hall Street', '451-761-8383'),
       (9, 'Ince ', 'Wendy ', '1958-06-29', 'F', '4 Southampton Road ', '802-911-9975'),
       (10, 'Ross', 'Tracey', '1949-12-07', 'F', '40 Sulphur Springs Dr', '131-396-5049'),
       (11, 'Wilson', 'Claire', '1966-12-31', 'F', '12 Cobblestone St ', '300-452-1091'),
       (12, 'Buckland ', 'Max ', '1945-06-24', 'M', '193 Vale St ', '833-534-0864'),
       (13, 'Clark ', 'Natalie ', '1964-06-18', 'F', '12 Beechwood Road', '241-467-9197'),
       (14, 'Bailey ', 'Piers ', '1959-06-28', 'M', '1202 Bumble Dr ', '747-815-0557');