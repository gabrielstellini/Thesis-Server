CREATE DATABASE db_sehm;
USE db_sehm;
create user 'springuser'@'localhost' identified by 'ThePassword';
grant all on db_sehm.* to 'springuser'@'localhost';