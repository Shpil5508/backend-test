package lesson_3;

import lombok.Data;
import java.util.List;

@Data
public class EquipmentResponse {
    private List<EquipmentItem> equipment;
}
