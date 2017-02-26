package com.jgommels.easyjava.csv.exception;

public class CsvParseException extends RuntimeException {
    public CsvParseException(String message) {
        super(message);
    }

    public CsvParseException(String message, Throwable t) {
        super(message, t);
    }
}
