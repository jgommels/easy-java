package com.jgommels.easyjava.csv;

import org.apache.commons.beanutils.Converter;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public interface EasyCsvReader {

    /**
     * Reads a CSV file into an in-memory List.
     *
     * @param file the path of the CSV file to read
     * @param clazz the class of the type to read CSV contents to
     * @return a list of objects representing the records in the file
     */
    default <T> List<T> read(Path file, Class<T> clazz) {
        try {
            return read(Files.newInputStream(file), clazz);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Reads an input stream that has contents in the form of a CSV.
     *
     * @param is an input stream with contents formatted as a CSV
     * @param clazz the class of the type to read CSV contents to
     * @return a list of objects representing the records in the file
     */
    <T> List<T> read(InputStream is, Class<T> clazz);

    /**
     * "Tails" the given number of lines at the end of a CSV file.
     *
     * @param file the path of the CSV file to read
     * @param clazz the class of the type to read CSV contents to
     * @param numLines the number of lines to read at the end of the file
     * @return a list of objects representing the records in the file
     */
    <T> List<T> tail(Path file, Class<T> clazz, int numLines);

    /**
     * Registers the converter to be used when reading CSV files.
     * @param converter the converter
     * @param clazz the class
     */
    void registerConverter(Converter converter, Class<?> clazz);

    /**
     * Convenience method that replaces the current {@link LocalDateTime} converter with one that will use the specified
     * time format when reading files.
     * @param format the time format for LocalDateTime
     */
    void registerLocalDateTimeConverterFormat(DateTimeFormatter format);
}