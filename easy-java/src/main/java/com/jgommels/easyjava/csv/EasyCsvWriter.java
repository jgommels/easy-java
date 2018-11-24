package com.jgommels.easyjava.csv;


import com.jgommels.easyjava.file.FileWriteMode;
import com.jgommels.easyjava.strings.CaseFormat;

import java.io.IOException;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.List;

public interface EasyCsvWriter {

    /**
     * Writes a CSV file using the given list of objects.
     *
     * @param file the path to write the file to
     * @param clazz the type of Java objects being written
     * @param records the list of records to write to the file
     * @throws IOException
     */
    default <T> void write(Path file, Class<T> clazz, List<T> records) {
        write(file, clazz, records, getDefaultHeaderFormat(), getDefaultWriteMode());
    }

    /**
     * Writes a CSV file using the given list of objects.
     *
     * @param file the path to write the file to
     * @param clazz the type of Java objects being written
     * @param records the list of records to write to the file
     * @param headerFormat the case format to apply to the header
     * @throws IOException
     */
    default <T> void write(Path file, Class<T> clazz, List<T> records, CaseFormat headerFormat) {
        write(file, clazz, records, headerFormat, getDefaultWriteMode());
    }

    default <T> void write(Path file, Class<T> clazz, List<T> records, FileWriteMode writeMode) {
        write(file, clazz, records, getDefaultHeaderFormat(), writeMode);
    }

    <T> void write(Path file, Class<T> clazz, List<T> records, CaseFormat headerFormat, FileWriteMode writeMode);

    void setDecimalFormat(DecimalFormat decimalFormat);

    private CaseFormat getDefaultHeaderFormat() {
        return CaseFormat.LOWER_UNDERSCORE;
    }

    private FileWriteMode getDefaultWriteMode() {
        return FileWriteMode.REPLACE;
    }
}