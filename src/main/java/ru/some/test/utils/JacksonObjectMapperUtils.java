package ru.some.test.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@UtilityClass
public class JacksonObjectMapperUtils {

    private final ObjectMapper objectMapper = new ObjectMapper()
        .enable(ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
        .disable(WRITE_DATES_AS_TIMESTAMPS)
        .disable(FAIL_ON_UNKNOWN_PROPERTIES)
        .findAndRegisterModules()
        .setSerializationInclusion(JsonInclude.Include.USE_DEFAULTS);

    @SneakyThrows
    public <T> List<T> readValueAsList(String content, Class<T> valueType) {
        if (content == null) {
            return null;
        }
        return objectMapper.readerForListOf(valueType).readValue(content);
    }

    @SneakyThrows
    public <T> T readValue(String content, Class<T> valueType) {
        if (content == null) {
            return null;
        }
        return objectMapper.readValue(content, valueType);
    }
}
