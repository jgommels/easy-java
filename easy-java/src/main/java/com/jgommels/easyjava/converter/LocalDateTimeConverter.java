package com.jgommels.easyjava.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter extends StringToObjectConverter {
    private final DateTimeFormatter formatter;

    public LocalDateTimeConverter() {
        this.formatter = null;
    }

    public LocalDateTimeConverter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    protected <T> T convertString(Class<T> type, String value) {
        return type.cast(parse(value));
    }

    private LocalDateTime parse(String value) {
        if (formatter != null) {
            return LocalDateTime.parse(value, this.formatter);
        }

        return LocalDateTime.parse(value);
    }
}