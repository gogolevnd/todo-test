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
@Story("Negative tests")
public class DeletingNegativeTodosTest extends AbstractTest {
    @Autowired
    PrepareDataSteps prepareSteps;
    @Autowired
    AssertionSteps assertionSteps;

/*
    CheckList:
    1) Check if the controller is able to adequately respond to different http-requests (Composite-tests(MVC))
        - wrong request:
            - wrong HTTP-method
            - wrong Content-type of the request
            - without/wrong auth
            - wrong credentials
            - wrong id data type
    2) Integration-tests:
        - check if the controller is able to adequately respond to delete-request not existing todo
*/

    @Test
    void processFailDeletingNotExistedTodo(){

        Todo notExistedTodo = prepareSteps.prepareDefaultTodo(
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
                List.of(notExistedTodo.id()),
                0)
        );

        getAppAggregationSteps().deleteTodo(
            notExistedTodo,
            404);
    }
}
