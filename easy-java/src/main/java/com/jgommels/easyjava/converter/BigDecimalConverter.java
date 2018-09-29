package com.jgommels.easyjava.converter;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.converters.AbstractConverter;

import java.math.BigDecimal;

public class BigDecimalConverter extends AbstractConverter {

    @Override
    protected <T> T convertToType(Class<T> type, Object value) throws Throwable {
        if(! (value instanceof String)) {
            throw new ConversionException("Source object is of type " + value.getClass() + ",  and not a String.");
        }

        return type.cast(new BigDecimal((String)value));
    }

    @Override
    protected Class<?> getDefaultType() {
        return String.class;
    }
}