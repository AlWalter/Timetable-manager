package university.alisawalter.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimeTable {
    private int id;
    private Lesson lesson;
    private Professor professor;
    private Group group;
    private Room room;
    private LocalDateTime timeBegin;
    private LocalDateTime timeEnd;
}
