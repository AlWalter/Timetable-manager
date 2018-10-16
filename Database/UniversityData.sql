INSERT INTO groups VALUES
(1,'SR-01'),
(2,'SR-02'),
(3,'SR-03'),
(4,'SR-04'),
(5,'SR-05');

INSERT into lessons VALUES
(1,'Biology'),
(2,'Arts'),
(3,'Literatur'),
(4,'Math'),
(5,'Chemie'),
(6,'History');

INSERT INTO students VALUES
(165432, 'Petrov', 'Petr',1),
(111111, 'Ivanov', 'Ivan',1),
(121113, 'Lewis', 'Simon',1),
(123114, 'Blach', 'George',1),
(165431, 'Brown', 'Gregory',1),
(109876, 'Pink', 'Maria',1),
(176543, 'Son', 'Van',1),
(111119, 'Chan', 'Jun',1),
(100000, 'Karlov', 'Karl',1),
(110000, 'Solla', 'Irma',1),
(120000, 'Samson', 'Karina',1),
(130000, 'Ivanovich', 'Ivan',1),
(240000, 'Petrovenko', 'Petr',2),
(278754, 'Ivanovski', 'Ivan',2),
(244444, 'Lewisovski', 'Simon',2),
(255555, 'Blachenko', 'George',2),
(266666, 'Browniyky', 'Gregory',2),
(288888, 'Pinkovski', 'Maria',2),
(388888, 'Sonovski', 'Van',3),
(300000, 'Chankovskaya', 'Jun',3),
(377777, 'Karlovski', 'Karl',3),
(344444, 'Sollana', 'Irma',3),
(345454, 'Samsonova', 'Karina',3),
(488888, 'Ivanko', 'Ivan',4),
(500000, 'Malinova', 'Parka',4),
(511111, 'Kudin', 'Ivan',4),
(533333, 'Lemechko', 'Simona',4),
(566666, 'Blabla', 'Geona',5),
(577177, 'Browny', 'Gregory',5),
(570077, 'Parfonova', 'Maria',5),
(555555, 'Sonen', 'Vano',5),
(577777, 'Charov', 'Juh',5),
(577779, 'Karlenko', 'Karl',5),
(565432, 'Solnzeva', 'Irma',5),
(576543, 'Samostrova', 'Karina',5),
(512232, 'Ignatenko', 'Ivan',5);

INSERT INTO professors VALUES
(1, 'Simonov','Yakov','Mathemathik dept'),
(2, 'Green', 'George' ,'Philosophy dept'),
(3, 'Blake','Harris','Chemie dept'),
(4, 'Werner', 'Dirk','History dept'),
(5, 'Petrov', 'Andrei','Biology dept');

INSERT INTO rooms VALUES
(1,101,1,60),
(2,102,1,80),
(3,201,1,100),
(4,111,2,60),
(5,112,2,80);

INSERT INTO timetables VALUES
(1000,1,5,1,1,'2018-04-03 08:00:00', '2018-04-03 09:20:00'),
(1001,2,4,2,2,'2018-04-03 08:00:00', '2018-04-03 09:20:00'),
(1002,3,2,3,3,'2018-04-03 08:00:00', '2018-04-03 09:20:00'),
(1003,4,1,4,4,'2018-04-03 08:00:00', '2018-04-03 09:20:00'),
(1004,5,3,5,5,'2018-04-03 10:30:00', '2018-04-03 12:00:00'),
(1005,6,4,1,1,'2018-04-03 10:30:00', '2018-04-03 12:00:00'),
(1006,1,5,4,2,'2018-04-04 08:00:00', '2018-04-04 09:20:00'),
(1007,3,2,3,3,'2018-04-04 08:00:00', '2018-04-04 09:20:00'),
(1008,4,1,2,4,'2018-04-04 08:00:00', '2018-04-04 09:20:00'),
(1009,6,4,1,5,'2018-04-04 10:30:00', '2018-04-04 12:00:00'),
(1010,4,1,2,1,'2018-04-04 10:30:00', '2018-04-04 12:00:00'),
(1011,3,2,3,2,'2018-04-04 10:30:00', '2018-04-04 12:00:00'),
(1012,6,4,4,3,'2018-04-05 08:00:00', '2018-04-04 09:20:00'),
(1013,1,5,2,4,'2018-04-05 08:00:00', '2018-04-04 09:20:00'),
(1014,2,2,1,5,'2018-04-05 08:00:00', '2018-04-04 09:20:00'),
(1015,3,2,5,1,'2018-04-05 10:30:00', '2018-04-05 12:00:00'),
(1016,4,1,3,2,'2018-04-05 10:30:00', '2018-04-05 12:00:00'),
(1017,5,3,2,3,'2018-04-06 08:00:00', '2018-04-04 09:20:00'),
(1018,6,4,1,4,'2018-04-06 08:00:00', '2018-04-04 09:20:00'),
(1019,4,1,5,5,'2018-04-06 08:00:00', '2018-04-04 09:20:00'),
(1020,1,5,4,1,'2018-04-06 10:30:00', '2018-04-06 12:00:00'),
(1021,2,2,3,2,'2018-04-06 10:30:00', '2018-04-06 12:00:00'),
(1022,3,2,2,3,'2018-04-06 10:30:00', '2018-04-06 12:00:00'),
(1023,4,1,1,4,'2018-04-07 08:00:00', '2018-04-04 09:20:00'),
(1024,5,3,2,5,'2018-04-07 08:00:00', '2018-04-04 09:20:00'),
(1025,6,4,3,1,'2018-04-07 08:00:00', '2018-04-04 09:20:00'),
(1026,3,2,4,2,'2018-04-07 10:30:00', '2018-04-07 12:00:00'),
(1027,4,1,5,3,'2018-04-07 10:30:00', '2018-04-07 12:00:00'),
(1028,5,3,3,4,'2018-04-07 10:30:00', '2018-04-07 12:00:00'),
(1029,6,4,1,5,'2018-04-09 08:00:00', '2018-04-04 09:20:00'),
(1030,4,1,2,1,'2018-04-09 08:00:00', '2018-04-04 09:20:00'),
(1031,1,5,3,2,'2018-04-09 08:00:00', '2018-04-04 09:20:00'),
(1032,2,1,4,3,'2018-04-09 10:30:00', '2018-04-09 12:00:00'),
(1033,3,2,5,4,'2018-04-10 08:00:00', '2018-04-09 09:20:00'),
(1034,4,1,1,5,'2018-04-10 10:30:00', '2018-04-10 12:00:00'),
(1035,5,3,2,1,'2018-04-10 10:30:00', '2018-04-10 12:00:00'),
(1036,6,4,3,2,'2018-04-10 10:30:00', '2018-04-10 12:00:00');