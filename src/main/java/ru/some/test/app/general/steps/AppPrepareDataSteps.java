package ru.some.test.app.general.steps;

import io.qameta.allure.Step;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.some.test.app.general.model.Todo;

@Getter
@Component
public class AppPrepareDataSteps {

    @Step("Подготавливаем сущность todo[id = {id}, text = {text}, completed = {completed}]")
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
