package lesson_3;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor  //НУЖЕН для сериализации, без него с JSON-ом работать не получится
public class EquipmentItem {
    private String image;
    private String name;
}
