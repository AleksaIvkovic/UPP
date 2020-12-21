INSERT INTO genre(name) VALUES ('Comedy');
INSERT INTO genre(name) VALUES ('Drama');
INSERT INTO genre(name) VALUES ('Romance');
INSERT INTO genre(name) VALUES ('Kids');
INSERT INTO genre(name) VALUES ('Horror');
INSERT INTO genre(name) VALUES ('History');

INSERT INTO sys_user
([city],[country],[email],[firstname],[is_active],[is_beta],[is_confirmed],[lastname],[password],[username])
VALUES
('Novi Sad','Serbia','upppomocninalog@gmail.com','Aleksa','true',null,'true','Ivkovic','pass','editor1')

INSERT INTO sys_user
([city],[country],[email],[firstname],[is_active],[is_beta],[is_confirmed],[lastname],[password],[username])
VALUES
('Novi Sad','Serbia','upppomocninalog@gmail.com','Aleksa','true',null,'true','Ivkovic','pass','editor2')

INSERT INTO sys_user
([city],[country],[email],[firstname],[is_active],[is_beta],[is_confirmed],[lastname],[password],[username])
VALUES
('Novi Sad','Serbia','upppomocninalog@gmail.com','Aleksa','true',null,'true','Ivkovic','pass','committee1')

INSERT INTO sys_user
([city],[country],[email],[firstname],[is_active],[is_beta],[is_confirmed],[lastname],[password],[username])
VALUES
('Novi Sad','Serbia','upppomocninalog@gmail.com','Aleksa','true',null,'true','Ivkovic','pass','committee2')

INSERT INTO sys_user
([city],[country],[email],[firstname],[is_active],[is_beta],[is_confirmed],[lastname],[password],[username])
VALUES
('Novi Sad','Serbia','upppomocninalog@gmail.com','Aleksa','true',null,'true','Ivkovic','pass','committee3')

INSERT INTO sys_user
([city],[country],[email],[firstname],[is_active],[is_beta],[is_confirmed],[lastname],[password],[username])
VALUES
('Novi Sad','Serbia','upppomocninalog@gmail.com','Aleksa','true',null,'true','Ivkovic','pass','lector')

INSERT INTO sys_user
([city],[country],[email],[firstname],[is_active],[is_beta],[is_confirmed],[lastname],[password],[username])
VALUES
('Novi Sad','Serbia','upppomocninalog@gmail.com','Aleksa','true',null,'true','Ivkovic','pass','headCommittee')

INSERT INTO sys_user
([city],[country],[email],[firstname],[is_active],[is_beta],[is_confirmed],[lastname],[password],[username])
VALUES
('Novi Sad','Serbia','upppomocninalog@gmail.com','Aleksa','true',null,'true','Ivkovic','pass','sysAdmin')

INSERT INTO authority ([name]) values ('READER');
INSERT INTO authority ([name]) values ('BETA-READER');
INSERT INTO authority ([name]) values ('WRITER');
INSERT INTO authority ([name]) values ('EDITOR');
INSERT INTO authority ([name]) values ('COMMITTEE');
INSERT INTO authority ([name]) values ('LECTOR');
INSERT INTO authority ([name]) values ('HEAD-COMMITTEE');
INSERT INTO authority ([name]) values ('SYS-ADMIN');