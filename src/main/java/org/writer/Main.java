package org.writer;

import org.writer.model.Student;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CSVWriter csvWriter = new CSVWriter();
        Student student = Student.builder()
                .name("Леха")
                .score(List.of("1","2","3"))
                .build();
        Student student2 = Student.builder()
                .name("Леха2")
                .score(List.of("1","2","3"))
                .build();
        csvWriter.writeToFile(List.of(student,student2),"file.CSV");
    }
}