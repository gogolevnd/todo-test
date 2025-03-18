package ru.some.test.todo.ws;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.socket.WebSocketSession;
import ru.some.test.app.general.model.Todo;
import ru.some.test.app.general.steps.PrepareDataSteps;
import ru.some.test.app.websocket.model.WebSocketMessage;
import ru.some.test.app.websocket.model.WsMessageType;
import ru.some.test.app.websocket.steps.AppWebsocketAssertionSteps;
import ru.some.test.app.websocket.steps.AppWebsocketRemoteSteps;
import ru.some.test.config.AppWebSocketConfig;
import ru.some.test.todo.AbstractTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(classes = {
    AppWebSocketConfig.class
})
@Epic("Todo-app")
@Feature("WS Todos")
@Story("Positive tests")
public class WsPositiveTest extends AbstractTest {
    @Autowired
    AppWebsocketRemoteSteps remoteSteps;
    @Autowired
    PrepareDataSteps prepareDataSteps;
    @Autowired
    AppWebsocketAssertionSteps assertionSteps;

/*
    CheckList:
    1) Check if the controller's mapper is able to adequately work to different types of Todo-model (Unit-tests)
    2) Check if the controller is able to adequately respond to different http-requests (Composite-tests(MVC))
        - wrong schema
    3) Integration-tests:
        - check if the controller is able to adequately respond
*/

    @Test
    void processWsMessageSuccess() {
        WebSocketSession session = remoteSteps.createWsSession();
        Todo todo = prepareDataSteps.prepareDefaultTodo(
            prepareDataSteps.prepareUnsignedId(),
            UUID.randomUUID().toString(),
            false
        );
        getAppAggregationSteps().createTodo(todo, "", 201);

        getTodosList().add(todo);

        List<WebSocketMessage> list = remoteSteps.receiveMessages(
            messages -> messages.stream().anyMatch(
                el -> todo.equals(el.data())
            )
        );

        assertionSteps.check(
            list.getFirst(),
            message ->
                assertThat(WsMessageType.NEW_TODO.getValue().equals(message.type()))
                .as("Тип сообщения не соответствует ожидаемому")
                .isTrue()
        );

        remoteSteps.closeWsSession(session);
    }
}
