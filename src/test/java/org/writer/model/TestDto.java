package org.writer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.writer.CsvColumn;

@Data
@AllArgsConstructor
public class TestDto {

    @CsvColumn(name = "Name")
    private String name;

    @CsvColumn(name = "Age")
    private int age;

}
