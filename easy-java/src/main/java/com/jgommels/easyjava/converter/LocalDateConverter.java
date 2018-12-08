package com.jgommels.easyjava.converter;

import java.time.LocalDate;

public class LocalDateConverter extends StringToObjectConverter {

    @Override
    protected <T> T convertString(Class<T> type, String value) {
        return type.cast(LocalDate.parse(value));
    }
}