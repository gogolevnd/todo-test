package ru.some.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.some.test.app.model.Todo;
import ru.some.test.app.steps.AssertionSteps;
import ru.some.test.app.steps.PrepareDataSteps;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdatingTodosTest extends AbstractTest{
    @Autowired
    PrepareDataSteps prepareSteps;
    @Autowired
    AssertionSteps assertionSteps;
    private static final String OFFSET_KEY = "offset";
    private static final String LIMIT_KEY = "limit";

/*
    CheckList:
    1) Check if the controller is able to adequately respond to different http-requests (Composite-tests(MVC))
        - wrong request:
            - wrong HTTP-method
            - wrong Content-type of the request
            - wrong/without body
     2) Integration-tests:
        - check if the controller is able to update todo
        - check if the controller is able to adequately respond by updating not existing todo
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
            appAggregationSteps.getTodos(
                Map.of(),
                200
            ),
            appResponse -> assertionSteps.verifyTodosExistInGetResponse(
                appResponse,
                List.of(firstTodo.id(), secondTodo.id()),
                0)
        );

        getTodos().addAll(List.of(firstTodo, secondTodo));

        appAggregationSteps.createTodo(firstTodo, "", 201);
        appAggregationSteps.createTodo(secondTodo, "", 201);

        assertionSteps.check(
            appAggregationSteps.getTodos(
                Map.of(),
                200
            ),
            appResponse -> assertionSteps.verifyTodosExistInGetResponse(
                appResponse,
                List.of(firstTodo.id(), secondTodo.id()),
                2)
        );

        assertionSteps.check(
            appAggregationSteps.getTodos(
                Map.of(OFFSET_KEY, 1,
                    LIMIT_KEY, 2),
                200),
            appResponse -> assertionSteps.verifyTodosExistInGetResponse(
                appResponse,
                List.of(firstTodo.id(), secondTodo.id()),
                1),
            appResponse -> assertionSteps.verifyTodo(appResponse, secondTodo)
        );

    }

    @Test
    void processWrongLimitValueGettingTodo(){
        assertionSteps.check(
            appAggregationSteps.getTodos(
                Map.of(LIMIT_KEY, -1),
                400
            ),
            appResponse -> assertThat(appResponse.body().prettyPrint())
                .as("Тело ответа эндпоинта не соответствует ожидаемому")
                .isEqualTo("Invalid query string")
        );
    }

    @Test
    void processWrongOffsetValueGettingTodo(){
        assertionSteps.check(
            appAggregationSteps.getTodos(
                Map.of(OFFSET_KEY, -1),
                400
            ),
            appResponse -> assertThat(appResponse.body().prettyPrint())
                .as("Тело ответа эндпоинта не соответствует ожидаемому")
                .isEqualTo("Invalid query string")
        );
    }
}
