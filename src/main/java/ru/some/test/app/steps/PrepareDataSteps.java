package ru.some.test.app.steps;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.some.test.app.model.Todo;

@Getter
@Component
public class PrepareDataSteps {

    public Todo prepareDefaultTodo(long id, String text, boolean completed){
        return Todo.builder()
            .id(id)
            .text(text)
            .completed(completed)
            .build();
    }

    public long prepareUnsignedId(){
        long leftLimit = 0L;
        long rightLimit = Long.MAX_VALUE;
        return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    }
}
