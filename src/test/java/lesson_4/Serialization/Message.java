package lesson_4.Serialization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data   //эта аннотация сформирует конструктор по-умолчанию, геттеры и сеттеры для полей createAt, text, author.
        //Они не будут явно прописаны в классе, но их можно вызвать из метода
@Builder    //аннотация для то, что бы не вызывать сеттеры
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {
    private Date createDate;
    private String text;
    private String author;
}
