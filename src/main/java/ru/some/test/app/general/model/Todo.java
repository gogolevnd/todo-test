package ru.some.test.app.general.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public record Todo(
    long id,
    String text,
    boolean completed
) {

}
