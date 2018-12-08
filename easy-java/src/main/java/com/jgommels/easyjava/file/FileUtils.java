package com.jgommels.easyjava.file;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {

    public static List<Path> listFilesInDir(Path dir) {
        if(!Files.isDirectory(dir)) {
            throw new IllegalArgumentException(dir + " is not a directory.");
        }

        try(Stream<Path> stream = Files.list(dir)) {
            return stream.filter(Files::isRegularFile).collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}