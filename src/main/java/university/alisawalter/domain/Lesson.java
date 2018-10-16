package university.alisawalter.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Lesson {
    private String lessonName;
    private int lessonId;
}
