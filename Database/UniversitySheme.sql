CREATE DATABASE University;

CREATE TABLE groups(
	group_id INT PRIMARY KEY NOT NULL,
	name VARCHAR(30)
);

CREATE TABLE lessons(
	lesson_id INT PRIMARY KEY NOT NULL,
	name VARCHAR(30)
);

CREATE TABLE professors(
	professor_id INT PRIMARY KEY NOT NULL,
	lname VARCHAR(30),
	fname VARCHAR(30),
	department VARCHAR(30)
);

CREATE TABLE rooms(
	room_id INT PRIMARY KEY NOT NULL,
	room_number INT,
	building_number INT,
	number_of_places INT
);


CREATE TABLE students(
	student_id INT NOT NULL PRIMARY KEY,
	lname VARCHAR(30),
	fname VARCHAR(30),
	group_id int,
	FOREIGN KEY (group_id) REFERENCES groups(group_id)
);

CREATE TABLE timetables(
	timetable_id INT NOT NULL PRIMARY KEY,
	lesson_id INT NOT NULL,
	professor_id INT NOT NULL,
	group_id INT NOT NULL,
	room_id INT NOT NULL,
	time_begin TIMESTAMP NOT NULL,
	time_end TIMESTAMP NOT NULL,
	FOREIGN KEY (lesson_id) REFERENCES lessons(lesson_id),
	FOREIGN KEY (professor_id) REFERENCES professors(professor_id),
	FOREIGN KEY (group_id) REFERENCES groups(group_id),
	FOREIGN KEY (room_id) REFERENCES rooms(room_id),
	UNIQUE(group_id, time_begin)
);