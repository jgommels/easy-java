package com.jgommels.easyjava.csv;

import com.jgommels.easyjava.reflect.RelaxedFieldMatcher;
import com.jgommels.easyjava.csv.exception.CsvParseException;
import com.jgommels.easyjava.reflect.ReflectionUtils;
import org.apache.commons.beanutils.BeanUtilsBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple CSV reader that parses a CSV into Java objects using reflection.
 */
public class EasyCsvReader {

    private final BeanUtilsBean beanUtils;

    public EasyCsvReader(BeanUtilsBean beanUtils) {
        this.beanUtils = beanUtils;
    }

    public <T> List<T> read(Path file, Class<T> clazz) throws IOException {
        return read(Files.newInputStream(file), clazz);
    }


    public <T> List<T> read(InputStream is, Class<T> clazz) throws IOException {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(is))){
            String[] header = getRow(reader.readLine());
            RelaxedFieldMatcher mapping = RelaxedFieldMatcher.build(clazz.getDeclaredFields());

            List<T> rows = new ArrayList<>();

            String line;
            while((line = reader.readLine()) != null) {
                String[] row = getRow(line);
                rows.add(buildObject(header, row, clazz, mapping));
            }

            return rows;
        }
    }

    private <T> T buildObject(String[] header, String[] values, Class<T> clazz, RelaxedFieldMatcher fieldMatcher) {
        try {
            T instance = ReflectionUtils.getInstance(clazz);
            for (int i = 0; i < values.length; i++) {
                Field f = fieldMatcher.getMatchingField(header[i]);
                beanUtils.setProperty(instance, f.getName(), values[i]);
            }

            return instance;
        } catch (IllegalAccessException | InvocationTargetException e){
            throw new CsvParseException("Could not build object of type " + clazz.getName(), e);
        }
    }

    private static String[] getRow(String str) {
        String[] header = str.split(",");
        for(int i=0; i<header.length; i++) {
            header[i] = header[i].trim();
        }

        return header;
    }
}
