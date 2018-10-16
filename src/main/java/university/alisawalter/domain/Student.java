package university.alisawalter.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Student extends Person {
    private Group group;

    public Student(String firstName, String lastName, int personalID, Group group) {
        super(firstName, lastName, personalID);
        this.group = group;
        group.addStudent(this);
    }
}
