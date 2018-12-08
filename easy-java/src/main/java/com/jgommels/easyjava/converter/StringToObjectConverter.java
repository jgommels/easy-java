package com.jgommels.easyjava.converter;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.converters.AbstractConverter;

abstract class StringToObjectConverter extends AbstractConverter {

    protected abstract <T> T convertString(Class<T> type, String value);

    @Override
    protected <T> T convertToType(Class<T> type, Object value) {
        if (!(value instanceof String)) {
            throw new ConversionException("Source object is of type " + value.getClass() + ",  and not a String.");
        }

        return convertString(type, (String)value);
    }

    @Override
    protected Class<?> getDefaultType() {
        return String.class;
    }
}