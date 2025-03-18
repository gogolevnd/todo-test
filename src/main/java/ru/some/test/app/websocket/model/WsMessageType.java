package ru.some.test.app.websocket.model;

import lombok.Getter;

@Getter
public enum WsMessageType {
    NEW_TODO("new_todo");

    private final String value;
    WsMessageType(String value) {
        this.value = value;
    }
}
