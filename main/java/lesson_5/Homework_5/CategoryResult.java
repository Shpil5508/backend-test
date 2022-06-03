package lesson_5.Homework_5;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResult {
    private Long id;
    private String title;
    private List<Product> products;
}
