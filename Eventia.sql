CREATE DATABASE eventiadb;

USE eventiadb;

CREATE TABLE Customers(
customer_id INT PRIMARY KEY AUTO_INCREMENT,
customer_name VARCHAR(30),
customer_password VARCHAR(30));

CREATE TABLE Events(
event_id INT PRIMARY KEY AUTO_INCREMENT,
event_name VARCHAR(100),
event_venue VARCHAR(50),
event_date TIMESTAMP,
event_cost DECIMAL);

CREATE TABLE Tickets(
ticket_id INT PRIMARY KEY AUTO_INCREMENT,
customer_id INT,
event_id INT,
CONSTRAINT tickets_customer_id_FK FOREIGN KEY (customer_id) REFERENCES Customers(customer_id),
CONSTRAINT tickets_event_id_FK FOREIGN KEY (event_id) REFERENCES Events(event_id));

INSERT INTO Customers(customer_name, customer_password) VALUES('Max', 'qweqwe');
INSERT INTO Customers(customer_name, customer_password) VALUES('Susan', 'qaqaqa');
INSERT INTO Customers(customer_name, customer_password) VALUES('Mary', 'veveve');
SELECT * FROM Customers;

INSERT INTO Events(event_name, event_venue, event_date, event_cost) VALUES('The Weeknd', 'London, United Kingdom', '2017-08-05 18:30:00', 100);
INSERT INTO Events(event_name, event_venue, event_date, event_cost) VALUES('J. Cole', 'Dublin, Ireland', '2017-10-11 20:00:00', 80);
INSERT INTO Events(event_name, event_venue, event_date, event_cost) VALUES('Drake', 'Toronto, Canada', '2017-10-15 19:00:00', 100);
INSERT INTO Events(event_name, event_venue, event_date, event_cost) VALUES('NAV', 'Toronto, Canada', '2017-10-16 22:00:00', 70);
SELECT * FROM Events;

INSERT INTO Tickets(customer_id, event_id) VALUES(1, 2);
INSERT INTO Tickets(customer_id, event_id) VALUES(1, 3);
INSERT INTO Tickets(customer_id, event_id) VALUES(1, 4);
INSERT INTO Tickets(customer_id, event_id) VALUES(2, 2);
INSERT INTO Tickets(customer_id, event_id) VALUES(2, 3);
INSERT INTO Tickets(customer_id, event_id) VALUES(3, 1);
INSERT INTO Tickets(customer_id, event_id) VALUES(3, 2);
SELECT * FROM Tickets;