package ru.some.test.app.steps;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.some.test.app.model.Todo;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Getter
public class RemoteSteps {
    @Value("${app.url}")
    private String url;
    @Value("${app.endpoint}")
    private String appEndpoint;
    @Value("${app.username}")
    private String userName;
    @Value("${app.password}")
    private String password;

    public Response prepareCreateRequest(Todo todo) {
        return RestAssured.with()
            .body(todo)
            .contentType(ContentType.JSON)
            .when()
            .request(Method.POST, String.format("%s%s", url, appEndpoint));
    }

    public <T> Response prepareGetRequests(Map<String, Integer> queryParams) {
        return RestAssured.with()
            .queryParams(queryParams)
            .contentType(ContentType.JSON)
            .when()
            .request(Method.GET, String.format("%s%s", url, appEndpoint));
    }

    public <T> Response prepareUpdateRequest(T body,
                                             ContentType contentType,
                                             String key,
                                             long value) {
        return RestAssured.with()
            .body(body)
            .contentType(contentType)
            .pathParam(key, value)
            .when()
            .request(Method.PUT, String.format("%s%s/{%s}", url, appEndpoint, key));
    }

    public <T> Response prepareDeleteRequest(String key,
                                             long value) {
        return RestAssured.with()
            .contentType(ContentType.JSON)
            .pathParam(key, value)
            .auth().preemptive().basic(userName, password)
            .when()
            .request(Method.DELETE, String.format("%s%s/{%s}", url, appEndpoint, key));
    }

    public Response prepareRandomRequest(Response response){
        return response;
    }
}
