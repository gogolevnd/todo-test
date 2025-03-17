package ru.some.test.app.steps;

import io.restassured.response.Response;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.some.test.app.model.Todo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@Component
@RequiredArgsConstructor
public class AppAggregationSteps {
    private final RemoteSteps remoteSteps;
    private final AssertionSteps assertionSteps;
    private static final String ID_KEY = "id";

    public void createTodo(Todo todo, String body, int statusCode) {
        Response response = remoteSteps.prepareCreateRequest(
            todo
        );
        assertionSteps.check(
            response,
            appResponse -> assertThat(appResponse.statusCode())
                .as("Код ответа эндпоинта не соответствует ожидаемому")
                .isEqualTo(statusCode),
            appResponse -> assertThat(appResponse.body().prettyPrint())
                .as("Тело ответа эндпоинта не соответствует ожидаемому")
                .isEqualTo(body)
        );
    }

    public Response getTodos(Map<String, Integer> params, int statusCode) {
        Response response = remoteSteps.prepareGetRequests(
            params
        );
        assertionSteps.check(
            response,
            appResponse -> assertThat(appResponse.statusCode())
                .as("Код ответа эндпоинта не соответствует ожидаемому")
                .isEqualTo(statusCode)
        );
        return response;
    }

    public void deleteTodos(List<Todo> todos) {
        todos.forEach(
            todo -> {
                Response response = remoteSteps.prepareDeleteRequest(
                    ID_KEY,
                    todo.id()
                );
                assertionSteps.check(
                    response,
                    appResponse -> assertThat(appResponse.statusCode())
                        .as("Ответ эндпоинта не соответствует ожидаемому")
                        .isEqualTo(204)
                );
            }
        );
    }
}
