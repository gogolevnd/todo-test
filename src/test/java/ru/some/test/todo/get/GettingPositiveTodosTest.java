package ru.some.test.todo.get;

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
@Feature("GET Todos")
@Story("Positive tests")
public class GettingPositiveTodosTest extends AbstractTest {
    @Autowired
    PrepareDataSteps prepareSteps;
    @Autowired
    AssertionSteps assertionSteps;
    private static final String OFFSET_KEY = "offset";
    private static final String LIMIT_KEY = "limit";

/*
    CheckList:
    1) Check if the adapter is able to adequately work with queryParams - offset and limit (Unit-tests)
    2) Check if the controller is able to adequately respond to different http-requests (Composite-tests(MVC))
        - right request with/without queryParams
    3) Integration-tests:
        - check if the controller is able to respond without todos
        - check if the controller is able to respond with list of todos
        - check if the controller is able to work without params
        - check if the controller is able to work with one param
*/

    @Test
    void processSuccessWithParamsGettingTodo(){

        Todo firstTodo = prepareSteps.prepareDefaultTodo(
            prepareSteps.prepareUnsignedId(),
            UUID.randomUUID().toString(),
            true
        );

        Todo secondTodo = prepareSteps.prepareDefaultTodo(
            prepareSteps.prepareUnsignedId(),
            UUID.randomUUID().toString(),
            false
        );

        assertionSteps.check(
            getAppAggregationSteps().getTodos(
                Map.of(),
                200
            ),
            appResponse -> assertionSteps.verifyTodosExistInGetResponse(
                appResponse,
                List.of(firstTodo.id(), secondTodo.id()),
                0)
        );

        getTodosList().addAll(List.of(firstTodo, secondTodo));

        getAppAggregationSteps().createTodo(firstTodo, "", 201);
        getAppAggregationSteps().createTodo(secondTodo, "", 201);

        assertionSteps.check(
            getAppAggregationSteps().getTodos(
                Map.of(),
                200
            ),
            appResponse -> assertionSteps.verifyTodosExistInGetResponse(
                appResponse,
                List.of(firstTodo.id(), secondTodo.id()),
                2)
        );

        assertionSteps.check(
            getAppAggregationSteps().getTodos(
                Map.of(OFFSET_KEY, 1,
                    LIMIT_KEY, 2),
                200),
            appResponse -> assertionSteps.verifyTodosExistInGetResponse(
                appResponse,
                List.of(firstTodo.id(), secondTodo.id()),
                2),
            appResponse -> assertionSteps.verifyTodo(appResponse, secondTodo)
        );

    }
}
