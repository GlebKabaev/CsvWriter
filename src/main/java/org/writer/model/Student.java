package org.writer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.writer.CsvColumn;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Student {
    @CsvColumn(name = "name")
    private String name;
    @CsvColumn(name = "score")
    private List<String> score;
}