# miniaspireapi
Code can be run as spring boot project.
Requirements : 
  Java : 17
  maven : Apache Maven 3.9.6


DB Schema : 

CREATE DATABASE miniaspireapi;
use miniaspireapi;

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)

CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)

CREATE TABLE `user_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`),
  CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
)

CREATE TABLE `loan` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal(38,2) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `term` int NOT NULL,
  `user_id` bigint DEFAULT NULL,
  `loan_status` enum('PENDING','PAID','APPROVED') DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKxxm1yc1xty3qn1pthgj8ac4f` (`user_id`),
  CONSTRAINT `FKxxm1yc1xty3qn1pthgj8ac4f` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
)

CREATE TABLE `repayment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal(38,2) DEFAULT NULL,
  `due_date` date DEFAULT NULL,
  `status` enum('PENDING','PAID') DEFAULT NULL,
  `loan_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1sqpnbq9niyr1k2etknq49hvt` (`loan_id`),
  CONSTRAINT `FK1sqpnbq9niyr1k2etknq49hvt` FOREIGN KEY (`loan_id`) REFERENCES `loan` (`id`)
)






Api for signup 

curl --location 'http://localhost:8080/auth/register' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZXZlbmRlciIsImlhdCI6MTcxNjA0MDQyMCwiZXhwIjoxNzE2MDc2NDIwfQ.YcRJhbxm24yCz-9U7YeQOoWN-NGEySNLynD--e7-PvM' \
--data-raw '{
  "name": "Devender Chaudhary",
  "email": "devender@gmail.com",
  "username": "devender",
  "password": "password",
  "roles": [
    "USER", "ADMIN"
  ]
}'


Login api : 

curl --location 'http://localhost:8080/auth/login' \
--header 'Content-Type: application/json' \
--data '{
  "username": "devender",
  "password": "password"
}'


Create loan api

curl --location 'http://localhost:8080/loans' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYW5jaGl0YSIsImlhdCI6MTcxNjI5MDQ4NCwiZXhwIjoxNzE2Mjk0MDg0fQ.OJXck1XbOCNoGtsqGc_Z8FNwxQwakDlOIzg_XSEkJI8' \
--data '{
    "amount": 10000,
    "term": 3,
    "startDate": "2022-02-07"
}'


View loans api:

curl --location 'http://localhost:8080/customers/loans' \
--header 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7' \
--header 'Accept-Language: en-GB,en-US;q=0.9,en;q=0.8' \
--header 'Cache-Control: max-age=0' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZXZlbmRlciIsImlhdCI6MTcxNjI5NzU2MywiZXhwIjoxNzE2MzAxMTYzfQ.t2DzqBIfE8wJMJfH8EFxbT6PniHyo2o0eakrDhTNrS8'


View loan by loanId api : 

curl --location 'http://localhost:8080/customers/loans/1' \
--header 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7' \
--header 'Accept-Language: en-GB,en-US;q=0.9,en;q=0.8' \
--header 'Cache-Control: max-age=0' \
--header 'Connection: keep-alive' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZXZlbmRlciIsImlhdCI6MTcxNjI5NzU2MywiZXhwIjoxNzE2MzAxMTYzfQ.t2DzqBIfE8wJMJfH8EFxbT6PniHyo2o0eakrDhTNrS8'

Approve loan api : 

curl --location --request PUT 'http://localhost:8080/admin/loan/4/approve' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZXZlbmRlciIsImlhdCI6MTcxNjI5MDYyMSwiZXhwIjoxNzE2Mjk0MjIxfQ.I-Ol7lgavSKP6nKW5NN7-57O9aSvqbP4qkLIuqFDRX0'


Make repayment api : 

curl --location 'http://localhost:8080/loans/4/repayments' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYW5jaGl0YSIsImlhdCI6MTcxNjI5MDQ4NCwiZXhwIjoxNzE2Mjk0MDg0fQ.OJXck1XbOCNoGtsqGc_Z8FNwxQwakDlOIzg_XSEkJI8' \
--data '{
    "amount" :10000,
    "repaymentDate" : "2022-02-14"
}'
