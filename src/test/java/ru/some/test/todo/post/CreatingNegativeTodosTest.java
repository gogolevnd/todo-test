package ru.some.test.todo.post;

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
@Feature("POST Todos")
@Story("Negative tests")
public class CreatingNegativeTodosTest extends AbstractTest {
    @Autowired
    AppPrepareDataSteps prepareSteps;
    @Autowired
    AppAssertionSteps assertionSteps;

/*
    CheckList:
    1) Check if the controller's mapper is able to adequately respond to different types of Todo-model (Unit-tests)
        - wrong fields` types
        - null
        - empty/null node
    2) Check if the controller is able to adequately respond to different http-requests (Composite-tests(MVC))
        - wrong request:
            - wrong HTTP-method
            - wrong Content-type of the request
            - wrong endpoint
            - wrong params
    3) Integration-tests:
        - check if the controller is able to adequately respond to duplicated id and not create todo-model
*/

    @Test
    void processFailCreatingTodoDueDuplicatedId(){
        long id = prepareSteps.prepareUnsignedId();
        Todo firstTodo = prepareSteps.prepareDefaultTodo(
            id,
            UUID.randomUUID().toString(),
            true
        );

        Todo secondTodo = prepareSteps.prepareDefaultTodo(
            id,
            UUID.randomUUID().toString(),
            true
        );

        getTodosList().add(firstTodo);

        getAppAggregationSteps().createTodo(firstTodo, "", 201);
        getAppAggregationSteps().createTodo(secondTodo, "", 400);

        assertionSteps.check(
            getAppAggregationSteps().getTodos(
                Map.of(),
                200
            ),
            appResponse -> assertionSteps.verifyTodosExistInGetResponse(
                appResponse,
                List.of(firstTodo.id(), secondTodo.id()),
                1
            ),
            appResponse -> assertionSteps.verifyTodo(appResponse, firstTodo)
        );
    }
}
