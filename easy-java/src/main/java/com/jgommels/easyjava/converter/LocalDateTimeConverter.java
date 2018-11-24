package com.jgommels.easyjava.converter;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.converters.AbstractConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter extends AbstractConverter {

    private final DateTimeFormatter formatter;

    public LocalDateTimeConverter() {
        this.formatter = null;
    }

    public LocalDateTimeConverter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    protected <T> T convertToType(Class<T> type, Object value) throws Throwable {
        if(! (value instanceof String)) {
            throw new ConversionException("Source object is of type " + value.getClass() + ",  and not a String.");
        }

        LocalDateTime dateTime;
        if(formatter != null) {
            dateTime = LocalDateTime.parse((String) value, formatter);
        }
        else {
            dateTime = LocalDateTime.parse((String) value);
        }

        return type.cast(dateTime);
    }

    @Override
    protected Class<?> getDefaultType() {
        return String.class;
    }
}
