package ru.some.test.app.websocket.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Service
@RequiredArgsConstructor
public class AppWebsocketService {
    @Value("${app.url}")
    private String url;
    @Value("${app.port}")
    private String port;
    @Value("${app.websocket.endpoint}")
    private String wsEndpoint;

    @SneakyThrows
    public WebSocketSession openSession(WebSocketHandler handler){
        return new StandardWebSocketClient()
            .execute(handler, String.format("ws://%s:%s%s", url, port, wsEndpoint))
            .get();
    }

    @SneakyThrows
    public void closeSession(WebSocketSession session){
        session.close();
    }

}
