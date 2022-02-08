package ru.Katyanka8bit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilePathsTest {

    @org.junit.jupiter.api.Test
    void testGetListPaths() {
        assertEquals(null, FilePaths.getListPaths(null));
    }

    @org.junit.jupiter.api.Test
    void testGetListPaths2() {
        assertEquals(null, FilePaths.getListPaths("some_path"));
    }

}





