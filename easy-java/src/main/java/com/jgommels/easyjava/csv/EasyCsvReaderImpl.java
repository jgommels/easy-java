package com.jgommels.easyjava.csv;

import com.jgommels.easyjava.converter.LocalDateTimeConverter;
import com.jgommels.easyjava.csv.exception.CsvParseException;
import com.jgommels.easyjava.reflect.ReflectionUtils;
import com.jgommels.easyjava.reflect.RelaxedFieldMatcher;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple CSV reader that parses a CSV into Java objects using reflection.
 */
public class EasyCsvReaderImpl implements EasyCsvReader{

    private final BeanUtilsBean beanUtils;

    public EasyCsvReaderImpl(BeanUtilsBean beanUtils) {
        this.beanUtils = beanUtils;
    }

    @Override
    public <T> List<T> read(InputStream is, Class<T> clazz) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            return doRead(reader, clazz);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public <T> List<T> tail(Path file, Class<T> clazz, int numLines) {
        if(numLines < 1) {
            throw new IllegalArgumentException("numLines must be positive.");
        }

        String headerRaw;
        String[] header;
        RelaxedFieldMatcher mapping;
        try(BufferedReader reader = Files.newBufferedReader(file)) {
            headerRaw = reader.readLine();
            header = getRow(headerRaw);
            mapping = RelaxedFieldMatcher.build(clazz.getDeclaredFields());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        List<T> rows = new ArrayList<>();
        try(ReversedLinesFileReader reversedFileReader = new ReversedLinesFileReader(file.toFile(), StandardCharsets.UTF_8)) {
            for(int i=0; i<numLines; i++) {
                String line = reversedFileReader.readLine();
                if(line == null || Objects.equals(headerRaw, line)) {
                    break;
                }

                rows.add(0, buildObject(header, getRow(line), clazz, mapping));
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return rows;
    }

    @Override
    public void registerConverter(Converter converter, Class<?> clazz) {
        this.beanUtils.getConvertUtils().register(converter, clazz);
    }

    @Override
    public void registerLocalDateTimeConverterFormat(DateTimeFormatter format) {
        registerConverter(new LocalDateTimeConverter(format), LocalDateTime.class);
    }

    private <T> List<T> doRead(BufferedReader reader, Class<T> clazz) throws IOException {
        String[] header = getRow(reader.readLine());
        RelaxedFieldMatcher mapping = RelaxedFieldMatcher.build(clazz.getDeclaredFields());

        List<T> rows = new ArrayList<>();

        String line;
        while((line = reader.readLine()) != null) {
            rows.add(buildObject(header, getRow(line), clazz, mapping));
        }

        return rows;
    }

    private <T> T buildObject(String[] header, String[] values, Class<T> clazz, RelaxedFieldMatcher fieldMatcher) {
        try {
            T instance = ReflectionUtils.getInstance(clazz);
            for (int i = 0; i < values.length; i++) {
                Field f = fieldMatcher.getMatchingField(header[i]);
                if(f != null) {
                    String fieldName = f.getName();
                    String value = values[i];

                    if(value != null && !value.isBlank()) {
                        try {
                            beanUtils.setProperty(instance, fieldName, value);
                        }
                        catch(Exception e) {
                            throw new CsvParseException(String.format("Error occurred while attempting to set field [%s] " +
                                    "with value [%s]", fieldName, value), e);
                        }
                    }
                }
            }

            return instance;
        } catch (Exception e){
            if(e instanceof CsvParseException) {
                throw e;
            }

            throw new CsvParseException("Could not build object of type " + clazz.getName(), e);
        }
    }

    private String[] getRow(String str) {
        String[] header = str.split(",");
        for(int i=0; i<header.length; i++) {
            header[i] = header[i].trim();
        }

        return header;
    }
}
