package university.alisawalter.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Room {
    private int roomId;
    private int roomNumber;
    private int buildingNumber;
    private int numberOfPlaces;
}
