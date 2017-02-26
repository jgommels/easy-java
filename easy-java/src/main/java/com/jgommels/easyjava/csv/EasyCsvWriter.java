package com.jgommels.easyjava.csv;


import com.jgommels.easyjava.strings.CaseFormat;
import com.jgommels.easyjava.strings.CaseFormats;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple CSV writer that writes Java objects to a CSV file using reflection.
 */
public class EasyCsvWriter {


    public <T> void write(Path file, Class<T> clazz, List<T> records, CaseFormat caseFormat) throws IOException {


        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            Field[] fields = clazz.getDeclaredFields();

            List<String> columnHeaders = new ArrayList<>();

            for (Field field : fields) {
                field.setAccessible(true);
                String columnName = CaseFormats.convertCase(field.getName(), caseFormat);
                columnHeaders.add(columnName);
            }

            writer.write(columnHeaders.stream().collect(Collectors.joining(",")));

            for (T record : records) {
                writer.newLine();
                writeRecord(record, fields, writer);
            }
        }
    }

    private <T> void writeRecord(T record, Field[] fields, BufferedWriter writer) {
        try {
            for(int i=0; i<fields.length; i++) {
                if(i != 0)
                    writer.write(",");

                writer.write(fields[i].get(record).toString());
            }

        } catch (IllegalAccessException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
