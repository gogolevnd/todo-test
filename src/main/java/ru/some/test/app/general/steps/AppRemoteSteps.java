package ru.some.test.app.general.steps;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.some.test.app.general.model.Todo;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Getter
public class AppRemoteSteps {
    @Value("${app.url}")
    private String url;
    @Value("${app.port}")
    private String port;
    @Value("${app.endpoint}")
    private String appEndpoint;
    @Value("${app.username}")
    private String userName;
    @Value("${app.password}")
    private String password;

    @Step("Отправляем запрос на создание сущности {todo}")
    public Response prepareCreateRequest(Todo todo) {
        return RestAssured.with()
            .body(todo)
            .contentType(ContentType.JSON)
            .when()
            .request(Method.POST, String.format("http://%s:%s%s", url, port, appEndpoint));
    }

    @Step("Отправляем запрос на получение сущностей с параметрами '{queryParams}'")
    public <T> Response prepareGetRequests(Map<String, Integer> queryParams) {
        return RestAssured.with()
            .queryParams(queryParams)
            .contentType(ContentType.JSON)
            .when()
            .request(Method.GET, String.format("http://%s:%s%s", url, port, appEndpoint));
    }

    @Step("Отправляем запрос на обновление сущности с id = {value}")
    public <T> Response prepareUpdateRequest(T body,
                                             String key,
                                             long value) {
        return RestAssured.with()
            .body(body)
            .contentType(ContentType.JSON)
            .pathParam(key, value)
            .when()
            .request(Method.PUT, String.format("http://%s:%s%s/{%s}", url, port, appEndpoint, key));
    }

    @Step("Отправляем запрос на удаление сущности с id = {value}")
    public <T> Response prepareDeleteRequest(String key,
                                             long value) {
        return RestAssured.with()
            .contentType(ContentType.JSON)
            .pathParam(key, value)
            .auth().preemptive().basic(userName, password)
            .when()
            .request(Method.DELETE, String.format("http://%s:%s%s/{%s}", url, port, appEndpoint, key));
    }
}
