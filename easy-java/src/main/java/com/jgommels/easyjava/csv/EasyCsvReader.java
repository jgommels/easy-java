package com.jgommels.easyjava.csv;

import org.apache.commons.beanutils.Converter;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.List;

public interface EasyCsvReader {

    /**
     * Reads a CSV file into an in-memory List.
     *
     * @param file the path of the CSV file to read
     * @param clazz the class of the type to read CSV contents to
     * @return a list of objects representing the records in the file
     * @throws IOException
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
     * @throws IOException
     */
    <T> List<T> read(InputStream is, Class<T> clazz);

    <T> List<T> tail(Path file, Class<T> clazz, int numLines);

    void setDefaultLocalDateTimeFormat(DateTimeFormatter format);

    void registerConverter(Converter converter, Class<?> clazz);
}