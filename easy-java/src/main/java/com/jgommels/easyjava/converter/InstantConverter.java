package com.jgommels.easyjava.converter;

import java.time.Instant;

public class InstantConverter extends StringToObjectConverter {

    @Override
    protected <T> T convertString(Class<T> type, String value) {
        return type.cast(Instant.parse(value));
    }
}