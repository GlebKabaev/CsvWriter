package org.writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class CSVWriter implements Writable {
    @Override
    public void writeToFile(List<?> data, String fileName) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Список пуст или равен null");
        }
        Class<?> dataClass = data.get(0).getClass();
        List<Field> csvFields = getFields(dataClass);
        if (csvFields.isEmpty()) {
            throw new IllegalStateException("Отсутствуют поля с аннотацией CsvColumn");
        }

        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(fileName+".CSV"))) {
            String header = getHeaders(csvFields);
            bw.write(header);
            bw.newLine();
            for (int i = 0; i < data.size(); i++) {
                csvFields = getFields(data.get(i).getClass());
                String info = getInfo(csvFields, data.get(i));
                bw.write(info);
                bw.newLine();
            }


        } catch (IOException e) {
            throw new RuntimeException("Файл не найден");
        }
    }

    private String getHeaders(List<Field> fields) {
        StringBuilder header = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            String columnName = field.getAnnotation(CsvColumn.class).name();

            columnName= formatForCsv(columnName);

            header.append(columnName);

            appendComma(i, fields.size(), header);
        }
        return header.toString();
    }

    private String getInfo(List<Field> fields, Object dataClass) {
        StringBuilder info = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(dataClass);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Невозможно получить доступ к полю");
            }

            value = formatForCsv(value.toString());
            info.append(value);

            appendComma(i, fields.size(), info);

        }

        return info.toString();
    }

    private List<Field> getFields(Class<?> dataClass) {
        return Arrays.stream(dataClass.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(CsvColumn.class))
                .toList();
    }

    private void appendComma(int i, int fieldsSize, StringBuilder csvString) {
        if (i < fieldsSize - 1) {
            csvString.append(",");
        }
    }

    private String formatForCsv(String raw) {
        if (raw.contains(",") || raw.contains("\"")) {
            raw = "\"" + raw.replace("\"", "\"\"") + "\"";
        }
        return raw;
    }
}
