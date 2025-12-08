package org.writer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.writer.CsvColumn;

@Data
@Builder
@AllArgsConstructor
public class Person {
    @CsvColumn(name = "firstName")
    private String firstName;
    @CsvColumn(name = "lastName")
    private String lastName;
    @CsvColumn(name = "dayOfBirth")
    private int dayOfBirth;
    @CsvColumn(name = "monthOfBirth")
    private Months monthOfBirth;
    @CsvColumn(name = "yearOfBirth")
    private int yearOfBirth;

}
