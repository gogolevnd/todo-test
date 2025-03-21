package ru.some.test.app.websocket.steps;

import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import ru.some.test.app.websocket.model.AppWebSocketMessage;
import ru.some.test.app.websocket.service.AppWebsocketService;
import ru.some.test.app.websocket.service.CustomWebsocketHandler;

import java.util.List;
import java.util.function.Predicate;

import static org.awaitility.Awaitility.await;

@Component
@RequiredArgsConstructor
public class AppWebsocketRemoteSteps {
    private final AppWebsocketService service;
    private final CustomWebsocketHandler handler;

    @Step("Открываем сессию для подключения к ws")
    public WebSocketSession createWsSession() {
        return service.openSession(handler);
    }

    @Step("Закрываем сессию ws")
    public void closeWsSession(WebSocketSession session) {
        service.closeSession(session);
    }

    @Step("Получаем список сообщений из ws")
    public List<AppWebSocketMessage> receiveMessages(Predicate<List<AppWebSocketMessage>> predicate) {
        return await().until(
            handler::getList,
            predicate
            );
    }
}
