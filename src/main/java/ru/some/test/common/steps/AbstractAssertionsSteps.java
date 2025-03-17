package ru.some.test.common.steps;

import org.junit.jupiter.api.function.Executable;

import java.util.Arrays;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertAll;

public abstract class AbstractAssertionsSteps {

    @SafeVarargs
    public final <T> void check(T response,
                                 Consumer<T>... assertions) {
        var assertionsList = Arrays.stream(assertions).map(
            assertion -> (Executable) () -> assertion.accept(response)
        );
        assertAll(assertionsList);
    }
}
