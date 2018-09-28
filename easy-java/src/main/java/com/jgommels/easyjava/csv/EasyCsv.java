package com.jgommels.easyjava.csv;

import com.jgommels.easyjava.bean.BeanUtilsBeanFactory;
import com.jgommels.easyjava.file.FileWriteMode;
import com.jgommels.easyjava.strings.CaseFormat;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

/**
 * Convenience class for reading and writing CSV files.
 */
public class EasyCsv implements EasyCsvReader, EasyCsvWriter {

    private final EasyCsvReaderImpl reader;
    private final EasyCsvWriter writer;

    public EasyCsv() {
        this.reader = new EasyCsvReaderImpl(BeanUtilsBeanFactory.getBeanUtilsBean());
        this.writer = new EasyCsvWriterImpl();
    }

    @Override
    public <T> List<T> read(InputStream is, Class<T> clazz) {
        return this.reader.read(is, clazz);
    }

    @Override
    public <T> List<T> tail(Path file, Class<T> clazz, int numLines) {
        return this.reader.tail(file, clazz, numLines);
    }

    @Override
    public <T> void write(Path file, Class<T> clazz, List<T> records, CaseFormat headerFormat, FileWriteMode writeMode) {
        this.writer.write(file, clazz, records, headerFormat, writeMode);
    }
}