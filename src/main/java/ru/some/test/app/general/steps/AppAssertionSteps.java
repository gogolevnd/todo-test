package ru.some.test.app.general.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;
import ru.some.test.app.general.model.Todo;
import ru.some.test.common.steps.AbstractAssertionsSteps;
import ru.some.test.utils.JacksonObjectMapperUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Component
public class AppAssertionSteps extends AbstractAssertionsSteps {

    @Step("Проверяем поля сущности {todo}")
    public void verifyTodo(Response getResponse, Todo todo) {
        List<Todo> todos = getTodos(getResponse);

        if (todos.isEmpty()) {
            throw new AssertionError("В ответе эндпоинта не содержится необходимых сущностей");
        }

        todos.stream().filter(
            todoFromList -> todo.id() == todoFromList.id()
        ).forEach(
            filteredTodo -> assertAll(
                () -> assertThat(filteredTodo.id())
                    .as("Поле id не соответствует ожидаемому")
                    .isEqualTo(todo.id()),
                () -> assertThat(filteredTodo.text())
                    .as("Поле id не соответствует ожидаемому")
                    .isEqualTo(todo.text()),
                () -> assertThat(filteredTodo.completed())
                    .as("Поле id не соответствует ожидаемому")
                    .isEqualTo(todo.completed())
            )
        );
    }

    @Step("Проверяем, что сущности присутствуют в системе")
    public void verifyTodosExistInGetResponse(Response getResponse, List<Long> ids, int size) {
        int awaitedCount = getTodos(getResponse).stream().filter(
            todo -> ids.stream().anyMatch(id -> todo.id() == id)
        ).toList().size();
        assertThat(awaitedCount)
            .as("Количество сущностей в системе не соответствует ожидаемому")
            .isEqualTo(size);
    }

    private List<Todo> getTodos(Response getResponse) {
        return JacksonObjectMapperUtils.readValueAsList(
            getResponse.body().asPrettyString(),
            Todo.class
        );
    }
}
