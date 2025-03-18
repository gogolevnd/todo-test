package ru.some.test.todo.put;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.some.test.app.general.model.Todo;
import ru.some.test.app.general.steps.AppAssertionSteps;
import ru.some.test.app.general.steps.AppPrepareDataSteps;
import ru.some.test.todo.AbstractTest;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Epic("Todo-app")
@Feature("PUT Todos")
@Story("Positive tests")
public class UpdatingPositiveTodosTest extends AbstractTest {
    @Autowired
    AppPrepareDataSteps prepareSteps;
    @Autowired
    AppAssertionSteps assertionSteps;

/*
    CheckList:
    1) Check if the controller is able to adequately respond to different http-requests (Composite-tests(MVC))
        - right request
     2) Integration-tests:
        - check if the controller is able to update todo
*/

    @Test
    void processSuccessUpdatingTodo(){
        long id = prepareSteps.prepareUnsignedId();;

        Todo todo = prepareSteps.prepareDefaultTodo(
            id,
            UUID.randomUUID().toString(),
            true
        );

        Todo todoToUpdate = prepareSteps.prepareDefaultTodo(
            id,
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

        getTodosList().add(todo);

        getAppAggregationSteps().createTodo(todo, "", 201);

        assertionSteps.check(
            getAppAggregationSteps().getTodos(
                Map.of(),
                200
            ),
            appResponse -> assertionSteps.verifyTodosExistInGetResponse(
                appResponse,
                List.of(id),
                1)
        );

        getAppAggregationSteps().updateTodo(
            todoToUpdate,
            200);

        assertionSteps.check(
            getAppAggregationSteps().getTodos(
                Map.of(),
                200),
            appResponse -> assertionSteps.verifyTodosExistInGetResponse(
                appResponse,
                List.of(id),
                1),
            appResponse -> assertionSteps.verifyTodo(appResponse, todoToUpdate)
        );
    }
}
