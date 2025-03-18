package ru.some.test.todo.get;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.some.test.app.general.steps.AssertionSteps;
import ru.some.test.app.general.steps.PrepareDataSteps;
import ru.some.test.todo.AbstractTest;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Todo-app")
@Feature("GET Todos")
@Story("Negative tests")
public class GettingNegativeTodosTest extends AbstractTest {
    @Autowired
    PrepareDataSteps prepareSteps;
    @Autowired
    AssertionSteps assertionSteps;
    private static final String OFFSET_KEY = "offset";
    private static final String LIMIT_KEY = "limit";

/*
    CheckList:
    1) Check if the adapter is able to adequately work with queryParams - offset and limit (Unit-tests)
        - wrong data-types
    2) Check if the controller is able to adequately respond to different http-requests (Composite-tests(MVC))
        - wrong request:
            - wrong HTTP-method
            - wrong Content-type of the request
            - wrong endpoint
            - wrong params
    3) Integration-tests:
        - check if the controller is able to respond with not documented params' values
*/

    @Test
    void processWrongLimitValueGettingTodo(){
        assertionSteps.check(
            getAppAggregationSteps().getTodos(
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
            getAppAggregationSteps().getTodos(
                Map.of(OFFSET_KEY, -1),
                400
            ),
            appResponse -> assertThat(appResponse.body().prettyPrint())
                .as("Тело ответа эндпоинта не соответствует ожидаемому")
                .isEqualTo("Invalid query string")
        );
    }
}
