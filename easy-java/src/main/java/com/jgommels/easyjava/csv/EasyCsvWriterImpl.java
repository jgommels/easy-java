package com.jgommels.easyjava.csv;

import com.jgommels.easyjava.file.FileWriteMode;
import com.jgommels.easyjava.strings.CaseFormat;
import com.jgommels.easyjava.strings.CaseFormats;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class EasyCsvWriterImpl implements EasyCsvWriter {

    private DecimalFormat decimalFormat = null;

    @Override
    public <T> void write(Path file, Class<T> clazz, List<T> records, CaseFormat headerFormat, FileWriteMode writeMode) {
        boolean fileAlreadyExists = Files.exists(file);

        try (BufferedWriter writer = buildBufferedWriter(file, writeMode)) {
            Field[] fields = clazz.getDeclaredFields();

            if(!(fileAlreadyExists && FileWriteMode.APPEND.equals(writeMode))) {
                writer.write(buildHeader(headerFormat, fields));
            }

            for (T record : records) {
                writer.newLine();
                writeRecord(record, fields, writer);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void setDecimalFormat(DecimalFormat decimalFormat) {
        this.decimalFormat = decimalFormat;
    }

    private BufferedWriter buildBufferedWriter(Path file, FileWriteMode writeMode) {
        try {
            //TODO Handle inconsistent record lengths, header ordering, etc.
            if(FileWriteMode.APPEND.equals(writeMode)) {
                return Files.newBufferedWriter(file, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            }
            else if(FileWriteMode.REPLACE.equals(writeMode)) {
                return Files.newBufferedWriter(file, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        throw new IllegalArgumentException("Unknown " + FileWriteMode.class.getSimpleName() + ": " + writeMode);
    }

    private String buildHeader(CaseFormat headerFormat, Field[] fields) {
        List<String> columnHeaders = new ArrayList<>();

        for (Field field : fields) {
            field.setAccessible(true);
            String columnName = CaseFormats.convertCase(field.getName(), headerFormat);
            columnHeaders.add(columnName);
        }

        return String.join(",", columnHeaders);
    }

    private <T> void writeRecord(T record, Field[] fields, BufferedWriter writer) {
        try {
            for(int i=0; i<fields.length; i++) {
                if(i != 0)
                    writer.write(",");

                writer.write(toValue(record, fields, i));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> String toValue(T record, Field[] fields, int index) {
        try {
            Field field = fields[index];
            field.setAccessible(true);
            return valueToString(field.get(record));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private String valueToString(Object object) {
        if(object == null) {
            return "";
        }

        if(this.decimalFormat != null) {
            Class<?> clazz = object.getClass();
            if(Double.class.equals(clazz) || BigDecimal.class.equals(clazz) || Float.class.equals(clazz))
            return this.decimalFormat.format(object);
        }

        return object.toString();
    }
}
