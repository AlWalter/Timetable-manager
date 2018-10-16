package university.alisawalter.domain;


import lombok.extern.log4j.Log4j;
import university.alisawalter.DAO.DAOException;
import university.alisawalter.DAO.postgres.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Log4j
public class University {
    private Map<LocalDateTime, ArrayList<TimeTable>> course = new TreeMap<>();
    private List<Group> groups = new ArrayList<>();
    private List<Professor> professors = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private List<Lesson> lessons = new ArrayList<>();
    PostgressTimeTableDAO postgressTimeTableDAO = new PostgressTimeTableDAO();
    PostgresProfessorDAO postgresProfessorDAO = new PostgresProfessorDAO();
    PostgresRoomDAO postgresRoomDAO = new PostgresRoomDAO();
    PostgresGroupDAO postgresGroupDAO = new PostgresGroupDAO();
    PostgresLessonDAO postgresLessonDAO = new PostgresLessonDAO();


    public Map<LocalDateTime, TimeTable> showProfDayPlan(LocalDate date, Professor professor) {
        Map<LocalDateTime, TimeTable> plan = new TreeMap<>();
        List<TimeTable> lessonsInPeriod = new ArrayList<>();
        try {
            lessonsInPeriod = postgressTimeTableDAO.getPeriod(date.atStartOfDay(),
                    date.atTime(23, 00, 00));
        } catch (DAOException e) {
            log.error("An error occured when getting plan for date " + date.toString() + " professor id "
                    + professor.getPersonalID(),e);
        }
        for (TimeTable lesson : lessonsInPeriod) {
            if (lesson.getProfessor().equals(professor)) {
                plan.put(lesson.getTimeBegin(), lesson);
            }
        }
        return plan;
    }

    public Map<LocalDateTime, TimeTable> showProfMonthPlan(YearMonth month, Professor professor) {
        Map<LocalDateTime, TimeTable> plan = new TreeMap<>();
        List<TimeTable> lessonsInPeriod = new ArrayList<>();
        try {
            lessonsInPeriod = postgressTimeTableDAO.getPeriod(
                    LocalDateTime.of(month.getYear(), month.getMonth(), 1, 0, 0),
                    LocalDateTime.of(month.getYear(), month.getMonth(), month.lengthOfMonth(), 23, 0));
        } catch (DAOException e) {
            log.error("An error occured when getting month plan for month " + month.toString() + "Professor id "
                    + professor.getPersonalID(),e);
        }
        for (TimeTable lesson : lessonsInPeriod) {
            if (lesson.getProfessor().equals(professor)) {
                plan.put(lesson.getTimeBegin(), lesson);
            }
        }
        return plan;
    }

    public Map<LocalDateTime, TimeTable> showStudDayPlan(LocalDate date, Group group) {
        Map<LocalDateTime, TimeTable> plan = new TreeMap<>();
        List<TimeTable> lessonsInPeriod = new ArrayList<>();
        try {
            lessonsInPeriod = postgressTimeTableDAO.getPeriod(date.atStartOfDay(),
                    date.atTime(23, 00, 00));
        } catch (DAOException e) {
            log.error("An error occured when getting Day plan for " + date.toString() + " for group id "
                    + group.getId(),e);
        }
        for (TimeTable lesson : lessonsInPeriod) {
            if (lesson.getGroup().equals(group)) {
                plan.put(lesson.getTimeBegin(), lesson);
            }
        }
        return plan;
    }

    public Map<LocalDateTime, TimeTable> showStudMonthPlan(YearMonth month, Group group) {
        Map<LocalDateTime, TimeTable> plan = new TreeMap<>();
        List<TimeTable> lessonsInPeriod = new ArrayList<>();
        try {
            lessonsInPeriod = postgressTimeTableDAO.getPeriod(
                    LocalDateTime.of(month.getYear(), month.getMonth(), 1, 0, 0),
                    LocalDateTime.of(month.getYear(), month.getMonth(), month.lengthOfMonth(), 23, 0));
        } catch (DAOException e) {
            log.error("An error occured when getting mont plan for " + month.toString() + " Group id "
                    + group.getId(),e);
        }
        for (TimeTable lesson : lessonsInPeriod) {
            if (lesson.getGroup().equals(group)) {
                plan.put(lesson.getTimeBegin(), lesson);
            }
        }
        return plan;
    }

    public List<Group> getGroups() {
        return postgresGroupDAO.getAll();
    }

    public List<Professor> getProfessors() {
        return postgresProfessorDAO.getAll();
    }

    public List<Room> getRooms() {
        return postgresRoomDAO.getAll();
    }

    public List<Lesson> getLessons() {
        return postgresLessonDAO.getAll();
    }

    public void addCourse(TimeTable timeTable) {
        postgressTimeTableDAO.add(timeTable);
    }

    public void removeCourse(TimeTable timeTable) {
        postgressTimeTableDAO.remove(timeTable);
    }

    public void addGroups(Group group) {
        postgresGroupDAO.add(group);
    }

    public void removeGroup(Group group) {
        postgresGroupDAO.remove(group);
    }

    public void addProfessor(Professor professor) {
        postgresProfessorDAO.add(professor);
    }

    public void removeProfessor(Professor professor) {
        postgresProfessorDAO.remove(professor);
    }

    public void addRoom(Room room) {
        postgresRoomDAO.add(room);
    }

    public void removeRoom(Room room) {
        postgresRoomDAO.remove(room);
    }

    public void addLesson(Lesson lesson) {
        postgresLessonDAO.add(lesson);
    }

    public void removeLesson(Lesson lesson) {
        postgresLessonDAO.remove(lesson);
    }
}
