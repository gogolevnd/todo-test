package ru.some.test.app.websocket.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.some.test.app.websocket.model.AppWebSocketMessage;
import ru.some.test.utils.JacksonObjectMapperUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Getter
public class CustomWebsocketHandler extends TextWebSocketHandler {
    private final List<AppWebSocketMessage> list = new ArrayList<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        list.add(JacksonObjectMapperUtils.readValue(message.getPayload(), AppWebSocketMessage.class));
    }
}
