package com.jgommels.easyjava.csv;


import com.jgommels.easyjava.file.FileWriteMode;
import com.jgommels.easyjava.strings.CaseFormat;

import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.List;

public interface EasyCsvWriter {
    CaseFormat DEFAULT_HEADER_FORMAT = CaseFormat.LOWER_UNDERSCORE;
    FileWriteMode DEFAULT_WRITE_MODE = FileWriteMode.REPLACE;

    /**
     * Writes a CSV file using the given list of objects.
     *
     * @param file the path to write the file to
     * @param clazz the type of Java objects being written
     * @param records the list of records to write to the file
     */
    default <T> void write(Path file, Class<T> clazz, List<T> records) {
        write(file, clazz, records, DEFAULT_HEADER_FORMAT, DEFAULT_WRITE_MODE);
    }

    /**
     * Writes a CSV file using the given list of objects.
     *
     * @param file the path to write the file to
     * @param clazz the type of Java objects being written
     * @param records the list of records to write to the file
     * @param headerFormat the case format to apply to the header
     */
    default <T> void write(Path file, Class<T> clazz, List<T> records, CaseFormat headerFormat) {
        write(file, clazz, records, headerFormat, DEFAULT_WRITE_MODE);
    }

    /**
     * Writes a CSV file using the given list of objects.
     *
     * @param file the path to write the file to
     * @param clazz the type of Java objects being written
     * @param records the list of records to write to the file
     * @param writeMode the mode for writing the file (append or replace)
     */
    default <T> void write(Path file, Class<T> clazz, List<T> records, FileWriteMode writeMode) {
        write(file, clazz, records, DEFAULT_HEADER_FORMAT, writeMode);
    }

    /**
     * Writes a CSV file using the given list of objects.
     *
     * @param file the path to write the file to
     * @param clazz the type of Java objects being written
     * @param records the list of records to write to the file
     * @param headerFormat the case format to apply to the header
     * @param writeMode the mode for writing the file (append or replace)
     */
    <T> void write(Path file, Class<T> clazz, List<T> records, CaseFormat headerFormat, FileWriteMode writeMode);

    /**
     * Sets the decimal format to be used for writing numbers.
     *
     * @param decimalFormat the decimal format
     */
    void setDecimalFormat(DecimalFormat decimalFormat);
}