package ru.some.test.app.websocket.model;

import ru.some.test.app.general.model.Todo;

public record AppWebSocketMessage(
    String type,
    Todo data
) {
}
