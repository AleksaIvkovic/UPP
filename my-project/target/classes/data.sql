INSERT INTO genre(name) VALUES ('Comedy');
INSERT INTO genre(name) VALUES ('Drama');
INSERT INTO genre(name) VALUES ('Romance');
INSERT INTO genre(name) VALUES ('Kids');
INSERT INTO genre(name) VALUES ('Horror');
INSERT INTO genre(name) VALUES ('History');

INSERT INTO sys_user
([city],[country],[email],[firstname],[is_active],[is_beta],[is_confirmed],[lastname],[password],[username])
VALUES
('Novi Sad','Serbia','upppomocninalog@gmail.com','Marko','true','false','true','Markovic','pass','editor1')

INSERT INTO sys_user
([city],[country],[email],[firstname],[is_active],[is_beta],[is_confirmed],[lastname],[password],[username])
VALUES
('Novi Sad','Serbia','upppomocninalog@gmail.com','Nikola','true','false','true','Nikolic','pass','editor2')

INSERT INTO sys_user
([city],[country],[email],[firstname],[is_active],[is_beta],[is_confirmed],[lastname],[password],[username])
VALUES
('Novi Sad','Serbia','upppomocninalog@gmail.com','Pera','true','false','true','Peric','pass','committee1')

INSERT INTO sys_user
([city],[country],[email],[firstname],[is_active],[is_beta],[is_confirmed],[lastname],[password],[username])
VALUES
('Novi Sad','Serbia','upppomocninalog@gmail.com','Aleksa','true','false','true','Aleksic','pass','committee2')

INSERT INTO sys_user
([city],[country],[email],[firstname],[is_active],[is_beta],[is_confirmed],[lastname],[password],[username])
VALUES
('Novi Sad','Serbia','upppomocninalog@gmail.com','Sara','true','false','true','Saric','pass','committee3')

INSERT INTO sys_user
([city],[country],[email],[firstname],[is_active],[is_beta],[is_confirmed],[lastname],[password],[username])
VALUES
('Novi Sad','Serbia','upppomocninalog@gmail.com','Zeljko','true','false','true','Zeljkovic','pass','lector')

INSERT INTO sys_user
([city],[country],[email],[firstname],[is_active],[is_beta],[is_confirmed],[lastname],[password],[username])
VALUES
('Novi Sad','Serbia','upppomocninalog@gmail.com','Mitar','true','false','true','Mitrovic','pass','headCommittee')

INSERT INTO sys_user
([city],[country],[email],[firstname],[is_active],[is_beta],[is_confirmed],[lastname],[password],[username])
VALUES
('Novi Sad','Serbia','upppomocninalog@gmail.com','Jelena','true','false','true','Jelenic','pass','sysAdmin')

INSERT INTO authority ([name]) values ('READER');
INSERT INTO authority ([name]) values ('BETA-READER');
INSERT INTO authority ([name]) values ('WRITER');
INSERT INTO authority ([name]) values ('EDITOR');
INSERT INTO authority ([name]) values ('COMMITTEE');
INSERT INTO authority ([name]) values ('LECTOR');
INSERT INTO authority ([name]) values ('HEAD-COMMITTEE');
INSERT INTO authority ([name]) values ('SYS-ADMIN');

INSERT INTO user_authority ([user_id], authority_id) values (1, 4); --editor
INSERT INTO user_authority ([user_id], authority_id) values (2, 4); --editor
INSERT INTO user_authority ([user_id], authority_id) values (3, 5); --commitee
INSERT INTO user_authority ([user_id], authority_id) values (4, 5); --commitee
INSERT INTO user_authority ([user_id], authority_id) values (5, 5); --commitee
INSERT INTO user_authority ([user_id], authority_id) values (6, 6); --lector
INSERT INTO user_authority ([user_id], authority_id) values (7, 7); --headCommitee
INSERT INTO user_authority ([user_id], authority_id) values (8, 8); --sysAdmin