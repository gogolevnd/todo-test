package ru.some.test;

import lombok.Getter;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.some.test.app.model.Todo;
import ru.some.test.app.steps.AppAggregationSteps;
import ru.some.test.config.AppConfig;

import java.util.concurrent.CopyOnWriteArrayList;

@SpringJUnitConfig(classes = {
    AppConfig.class
})
@Getter
public class AbstractTest {
    @Autowired
    AppAggregationSteps appAggregationSteps;
    private CopyOnWriteArrayList<Todo> todos = new CopyOnWriteArrayList<>();

    @AfterEach
    void finish(){
        appAggregationSteps.deleteTodos(todos);
        todos.forEach(
            todo -> todos.remove(todo)
        );
    }

}
