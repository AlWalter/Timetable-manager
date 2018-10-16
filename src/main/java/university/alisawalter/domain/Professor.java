package university.alisawalter.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Professor extends Person {
    private String department;

    public Professor(String firstName, String lastName, int personalID, String department) {
        super(firstName, lastName, personalID);
        this.department = department;
    }
}
