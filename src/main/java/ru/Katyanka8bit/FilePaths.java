package ru.Katyanka8bit;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilePaths {
    private FilePaths() {
    }

    public static List<File> getListPaths(String directory) {
        if (directory != null) {
            try(Stream<Path> stream = Files.walk(Paths.get(directory))) {
                return  stream
                        .filter(Files::isRegularFile)
                        .map(Path::toFile)
                        .collect(Collectors.toList());
            } catch (Exception e) {
                return Collections.emptyList();
            }
        }
        return Collections.emptyList();
    }
}