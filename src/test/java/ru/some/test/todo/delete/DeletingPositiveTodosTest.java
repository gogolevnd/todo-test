package ru.some.test.todo.delete;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.some.test.app.general.model.Todo;
import ru.some.test.app.general.steps.AssertionSteps;
import ru.some.test.app.general.steps.PrepareDataSteps;
import ru.some.test.todo.AbstractTest;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Epic("Todo-app")
@Feature("DELETE Todos")
@Story("Positive tests")
public class DeletingPositiveTodosTest extends AbstractTest {
    @Autowired
    PrepareDataSteps prepareSteps;
    @Autowired
    AssertionSteps assertionSteps;

/*
    CheckList:
    1) Check if the controller is able to adequately respond to different http-requests (Composite-tests(MVC))
        - right request
    2) Integration-tests:
        - check if the controller is able delete todo
*/

    @Test
    void processSuccessDeletingTodo(){

        Todo todo = prepareSteps.prepareDefaultTodo(
            prepareSteps.prepareUnsignedId(),
            UUID.randomUUID().toString(),
            true
        );

        assertionSteps.check(
            getAppAggregationSteps().getTodos(
                Map.of(),
                200
            ),
            appResponse -> assertionSteps.verifyTodosExistInGetResponse(
                appResponse,
                List.of(todo.id()),
                0)
        );

        getAppAggregationSteps().createTodo(todo, "", 201);

        assertionSteps.check(
            getAppAggregationSteps().getTodos(
                Map.of(),
                200
            ),
            appResponse -> assertionSteps.verifyTodosExistInGetResponse(
                appResponse,
                List.of(todo.id()),
                1)
        );

        getAppAggregationSteps().deleteTodo(
            todo,
            204);

        assertionSteps.check(
            getAppAggregationSteps().getTodos(
                Map.of(),
                200),
            appResponse -> assertionSteps.verifyTodosExistInGetResponse(
                appResponse,
                List.of(todo.id()),
                0)
        );
    }
}
