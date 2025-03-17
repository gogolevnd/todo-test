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

public class CreatingTodosTest extends AbstractTest{
    @Autowired
    PrepareDataSteps prepareSteps;
    @Autowired
    AssertionSteps assertionSteps;

/*
    CheckList:
    1) Check if the controller's mapper is able to adequately respond to different types of Todo-model (Unit-tests)
        - right fields` types
        - wrong fields` types
        - null
        - empty/null node
    2) Check if the controller is able to adequately respond to different http-requests (Composite-tests(MVC))
        - right request
        - wrong request:
            - wrong HTTP-method
            - wrong Content-type of the request
            - wrong endpoint
            - wrong params

    3) Integration-tests:
        - check if the controller is able to adequately create todo-model
        - check if the controller is able to adequately respond to duplicated id and not create todo-model
*/

    @Test
    void processSuccessCreatingTodo(){
        Todo todo = prepareSteps.prepareDefaultTodo(
            prepareSteps.prepareUnsignedId(),
            UUID.randomUUID().toString(),
            true
        );

        getTodos().add(todo);

        appAggregationSteps.createTodo(todo, "", 201);

        assertionSteps.check(
            appAggregationSteps.getTodos(
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

        getTodos().add(firstTodo);

        appAggregationSteps.createTodo(firstTodo, "", 201);
        appAggregationSteps.createTodo(secondTodo, "", 400);

        assertionSteps.check(
            appAggregationSteps.getTodos(
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
