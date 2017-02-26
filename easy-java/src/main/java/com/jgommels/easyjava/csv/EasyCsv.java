package com.jgommels.easyjava.csv;

import com.jgommels.easyjava.strings.CaseFormat;
import com.jgommels.easyjava.bean.BeanUtilsBeanFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

/**
 * Convenience class for reading and writing CSV files.
 */
public class EasyCsv {

    private final EasyCsvReader reader;
    private final EasyCsvWriter writer;

    public EasyCsv() {
        this.reader = new EasyCsvReader(BeanUtilsBeanFactory.getBeanUtilsBean());
        this.writer = new EasyCsvWriter();
    }

    /**
     * Reads a CSV file.
     *
     * @param file the path of the CSV file to read
     * @param clazz the class of the type to read CSV contents to
     * @return a list of objects representing the records in the file
     * @throws IOException
     */
    public <T> List<T> read(Path file, Class<T> clazz) throws IOException {
        return this.reader.read(file, clazz);
    }

    /**
     * Reads an input stream that has contents in the form of a CSV.
     *
     * @param is an input stream with contents formatted as a CSV
     * @param clazz the class of the type to read CSV contents to
     * @return a list of objects representing the records in the file
     * @throws IOException
     */
    public <T> List<T> read(InputStream is, Class<T> clazz) throws IOException {
        return this.reader.read(is, clazz);
    }

    /**
     * Writes a CSV file using the given list of objects. Defaults to formatting the header names with snake case.
     *
     * @param file the path to write the file to
     * @param clazz the type of Java objects being written
     * @param records the list of records to write to the file
     * @throws IOException
     */
    public <T> void write(Path file, Class<T> clazz, List<T> records) throws IOException {
        this.writer.write(file, clazz, records, CaseFormat.LOWER_UNDERSCORE);
    }

    /**
     * Writes a CSV file using the given list of objects. Formats the header using the given case format.
     *
     * @param file the path to write the file to
     * @param clazz the type of Java objects being written
     * @param records the list of records to write to the file
     * @param caseFormat the case format to apply to the header
     * @throws IOException
     */
    public <T> void write(Path file, Class<T> clazz, List<T> records, CaseFormat caseFormat) throws IOException {
        this.writer.write(file, clazz, records, caseFormat);
    }
}
