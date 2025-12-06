package org.writer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.writer.model.TestDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CSVWriterTest {
    private CSVWriter writer;
    private Path tempFile;

    @BeforeEach
    void setUp() throws IOException {
        writer = new CSVWriter();
        tempFile = Files.createTempFile("csv_test_", "");
        Files.deleteIfExists(Path.of(tempFile.toString() + ".CSV"));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(tempFile.toString() + ".CSV"));
    }

    @Test
    void testWriteToFile_success() throws IOException {
        List<TestDto> data = List.of(
                new TestDto("John", 25),
                new TestDto("Anna", 30)
        );

        writer.writeToFile(data, tempFile.toString());

        List<String> lines = Files.readAllLines(Path.of(tempFile.toString() + ".CSV"));

        assertEquals(3, lines.size());
        assertEquals("Name,Age", lines.get(0));
        assertEquals("John,25", lines.get(1));
        assertEquals("Anna,30", lines.get(2));
    }

    @Test
    void testWriteToFile_escapingSpecialCharacters() throws IOException {
        List<TestDto> data = List.of(
                new TestDto("John, Jr.", 25),
                new TestDto("He said \"Hi\"", 20)
        );

        writer.writeToFile(data, tempFile.toString());

        List<String> lines = Files.readAllLines(Path.of(tempFile.toString() + ".CSV"));

        assertEquals("Name,Age", lines.get(0));
        assertEquals("\"John, Jr.\",25", lines.get(1));
        assertEquals("\"He said \"\"Hi\"\"\",20", lines.get(2));
    }

    @Test
    void testWriteToFile_noAnnotatedFields() {
        List<Object> data = List.of(new Object());

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> writer.writeToFile(data, tempFile.toString())
        );

        assertEquals("Отсутствуют поля с аннотацией CsvColumn", ex.getMessage());
    }

    @Test
    void testWriteToFile_emptyListThrows() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> writer.writeToFile(List.of(), tempFile.toString())
        );

        assertEquals("Список пуст или равен null", ex.getMessage());
    }

    @Test
    void testWriteToFile_nullListThrows() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> writer.writeToFile(null, tempFile.toString())
        );

        assertEquals("Список пуст или равен null", ex.getMessage());
    }
}
