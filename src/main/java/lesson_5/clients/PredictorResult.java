package lesson_5.clients;

import lombok.Data;

import java.util.List;

@Data
public class PredictorResult {

    /* См. документацию https://yandex.ru/dev/predictor/doc/dg/reference/complete.html:
     * "В JSON-интерфейсе вместо XML-элементов возвращаются JavaScript-объекты с теми же именами и семантикой:
     * {"endOfWord":false,"pos":-2,"text":["world"]}" */
    private Boolean endOfWord;
    private Integer pos;
    private List<String> text;
}
