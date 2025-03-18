package ru.some.test.todo;

import lombok.Getter;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.some.test.app.general.model.Todo;
import ru.some.test.app.general.steps.AppAggregationSteps;
import ru.some.test.config.AppConfig;

import java.util.concurrent.CopyOnWriteArrayList;

@SpringJUnitConfig(classes = {
    AppConfig.class
})
@Getter
public class AbstractTest {
    @Autowired
    private AppAggregationSteps appAggregationSteps;
    private CopyOnWriteArrayList<Todo> todosList = new CopyOnWriteArrayList<>();

    @AfterEach
    void finish(){
        appAggregationSteps.deleteTodos(todosList);
        todosList.forEach(
            todo -> todosList.remove(todo)
        );
    }

}
