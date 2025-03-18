package ru.some.test.todo.post;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.some.test.app.general.steps.AppAssertionSteps;
import ru.some.test.app.general.steps.AppPrepareDataSteps;
import ru.some.test.todo.AbstractTest;
import ru.some.test.app.general.model.Todo;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Epic("Todo-app")
@Feature("POST Todos")
@Story("Positive tests")
public class CreatingPositiveTodosTest extends AbstractTest {
    @Autowired
    AppPrepareDataSteps prepareSteps;
    @Autowired
    AppAssertionSteps assertionSteps;

/*
    CheckList:
    1) Check if the controller's mapper is able to adequately respond to different types of Todo-model (Unit-tests)
        - right fields` types
    2) Check if the controller is able to adequately respond to different http-requests (Composite-tests(MVC))
        - right request
    3) Integration-tests:
        - check if the controller is able to adequately respond to duplicated id and not create todo-model
*/

    @Test
    void processSuccessCreatingTodo(){
        Todo todo = prepareSteps.prepareDefaultTodo(
            prepareSteps.prepareUnsignedId(),
            UUID.randomUUID().toString(),
            true
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
                List.of(todo.id()),
                1),
            appResponse -> assertionSteps.verifyTodo(appResponse, todo)
        );
    }
}
