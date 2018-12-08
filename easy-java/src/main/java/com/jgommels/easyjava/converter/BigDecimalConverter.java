package com.jgommels.easyjava.converter;

import java.math.BigDecimal;

public class BigDecimalConverter extends StringToObjectConverter {

    @Override
    protected <T> T convertString(Class<T> type, String value) {
        return type.cast(new BigDecimal(value));
    }
}