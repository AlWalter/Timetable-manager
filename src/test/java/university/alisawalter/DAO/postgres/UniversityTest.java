package university.alisawalter.DAO.postgres;

import org.junit.Before;
import university.alisawalter.DAO.ConnectionFactory;
import university.alisawalter.domain.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Map;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UniversityTest {
    private University university;
    Student student1;
    Student student2;
    Student student3;
    Group groupA;
    Group groupB;
    Professor profA;
    Professor profB;
    Room room1;
    Room room2;
    Lesson math;
    Lesson history;
    Lesson art;
    Lesson phylosophy;
    Lesson geography;
    TimeTable timeTable1;
    TimeTable timeTable2;
    TimeTable timeTable3;
    TimeTable timeTable4;
    TimeTable timeTable5;

    @Before
    public void setUp() throws SQLException {
        setupDatabase();
        university = new University();
        addAll(university);
    }

    private void setupDatabase() throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM timetables;" +
                "DELETE FROM lessons;" +
                "DELETE FROM professors;" +
                "DELETE FROM students;" +
                "DELETE FROM groups;" +
                "DELETE FROM rooms");
        ConnectionFactory.getInstance().closeConnection(connection);
    }

    private void addAll(University university) {
        groupA = new Group("TK-21", 1);
        groupB = new Group("TK-22", 2);
        profA = new Professor("Gregory", "Petrov", 1234, "Mathematik");
        profB = new Professor("Alexey", "Sudarikov", 2541, "art");
        room1 = new Room(1, 1, 1, 50);
        room2 = new Room(2, 2, 2, 50);
        math = new Lesson("Math", 1);
        history = new Lesson("History", 2);
        art = new Lesson("Art", 3);
        phylosophy = new Lesson("Phylosophy", 4);
        geography = new Lesson("Geography", 5);
        timeTable1 = new TimeTable(1, math, profA, groupA, room1, LocalDateTime.parse("2018-04-01T08:00:00", ISO_LOCAL_DATE_TIME),
                LocalDateTime.parse("2018-04-01T10:30:00", ISO_LOCAL_DATE_TIME));
        timeTable2 = new TimeTable(2, art, profB, groupB, room2, LocalDateTime.parse("2018-04-01T08:00:00", ISO_LOCAL_DATE_TIME),
                LocalDateTime.parse("2018-04-01T10:30:00", ISO_LOCAL_DATE_TIME));
        timeTable3 = new TimeTable(3, math, profA, groupB, room1, LocalDateTime.parse("2018-04-01T10:30:00", ISO_LOCAL_DATE_TIME),
                LocalDateTime.parse("2018-04-01T12:00:00", ISO_LOCAL_DATE_TIME));
        timeTable4 = new TimeTable(4, art, profB, groupA, room2, LocalDateTime.parse("2018-04-02T10:30:00", ISO_LOCAL_DATE_TIME)
                , LocalDateTime.parse("2018-04-02T12:00:00", ISO_LOCAL_DATE_TIME));
        timeTable5 = new TimeTable(5, phylosophy, profA, groupB, room1, LocalDateTime.parse("2018-04-02T10:30:00", ISO_LOCAL_DATE_TIME)
                , LocalDateTime.parse("2018-04-02T12:00:00", ISO_LOCAL_DATE_TIME));
        student1 = new Student("Ivan", "Ivanov", 12, groupA);
        student2 = new Student("Sidor", "Sidorov", 13, groupA);
        student3 = new Student("Petr", "Petrov", 17, groupA);
        university.addGroups(groupA);
        university.addGroups(groupB);
        university.addProfessor(profA);
        university.addProfessor(profB);
        university.addLesson(math);
        university.addLesson(history);
        university.addLesson(art);
        university.addLesson(phylosophy);
        university.addLesson(geography);
        university.addRoom(room1);
        university.addRoom(room2);
        university.addCourse(timeTable1);
        university.addCourse(timeTable2);
        university.addCourse(timeTable3);
        university.addCourse(timeTable4);
        university.addCourse(timeTable5);
    }

    @org.junit.Test
    public void showProfDayPlan_ShouldreturnTwoWalues() {
        Map<LocalDateTime, TimeTable> result = university.showProfDayPlan(timeTable1.getTimeBegin().toLocalDate(), profA);
        assertEquals(2, result.size());
    }

    @org.junit.Test
    public void showProfDayPlan_ShouldcontainTwoKeysTimetable1BeginTimeAndTimeTable3BeginTime() {
        Map<LocalDateTime, TimeTable> result = university.showProfDayPlan(timeTable1.getTimeBegin().toLocalDate(), profA);
        LocalDateTime key1 = timeTable1.getTimeBegin();
        LocalDateTime key2 = timeTable3.getTimeBegin();
        assertTrue(result.containsKey(key1));
        assertTrue(result.containsKey(key2));
    }

    @org.junit.Test
    public void showProfMonthPlan_ShouldreturnThreeWalues() {
        Map<LocalDateTime, TimeTable> result = university.showProfMonthPlan(YearMonth.from(timeTable1.getTimeBegin()), profA);
        assertEquals(3, result.size());
    }

    @org.junit.Test
    public void showProfMonthPlan_ShouldcontainTtreeKeysTimetable1BeginTimeAndTimeTable3BeginTimeAndTimetable5BeginTime() {
        Map<LocalDateTime, TimeTable> result = university.showProfMonthPlan(YearMonth.from(timeTable1.getTimeBegin()), profA);
        LocalDateTime key1 = timeTable1.getTimeBegin();
        LocalDateTime key2 = timeTable3.getTimeBegin();
        LocalDateTime key3 = timeTable5.getTimeBegin();
        assertTrue(result.containsKey(key1));
        assertTrue(result.containsKey(key2));
        assertTrue(result.containsKey(key3));
    }

    @org.junit.Test
    public void showStudDayPlan_ShouldreturnTwoWalues() {
        Map<LocalDateTime, TimeTable> result = university.showStudDayPlan(timeTable2.getTimeBegin().toLocalDate(), groupB);
        assertEquals(2, result.size());
    }

    @org.junit.Test
    public void showStudDayPlan_ShouldcontainTwoKeysTimetable2BeginTimeAndTimeTable3BeginTime() {
        Map<LocalDateTime, TimeTable> result = university.showStudDayPlan(timeTable2.getTimeBegin().toLocalDate(), groupB);
        LocalDateTime key1 = timeTable2.getTimeBegin();
        LocalDateTime key2 = timeTable3.getTimeBegin();
        assertTrue(result.containsKey(key1));
        assertTrue(result.containsKey(key2));
    }

    @org.junit.Test
    public void showStudMonthPlan_ShouldreturnThreeWalues() {
        Map<LocalDateTime, TimeTable> result = university.showStudMonthPlan(YearMonth.from(timeTable2.getTimeBegin()), groupB);
        assertEquals(3, result.size());
    }

    @org.junit.Test
    public void showStudMonthPlan_ShouldcontainTtreeKeysTimetable2BeginTimeAndTimeTable3BeginTimeAndTimetable5BeginTime() {
        Map<LocalDateTime, TimeTable> result = university.showStudMonthPlan(YearMonth.from(timeTable1.getTimeBegin()), groupB);
        LocalDateTime key1 = timeTable2.getTimeBegin();
        LocalDateTime key2 = timeTable3.getTimeBegin();
        LocalDateTime key3 = timeTable5.getTimeBegin();
        assertTrue(result.containsKey(key1));
        assertTrue(result.containsKey(key2));
        assertTrue(result.containsKey(key3));
    }
}