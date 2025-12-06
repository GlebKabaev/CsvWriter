package org.writer;

import net.datafaker.Faker;
import org.writer.model.Months;
import org.writer.model.Person;
import org.writer.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Faker faker = new Faker();
        Random random = new Random();

        List<Person> people = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            people.add(Person.builder()
                    .firstName(faker.minecraft().monsterName())
                    .lastName(faker.name().lastName())
                    .dayOfBirth(random.nextInt(30))
                    .yearOfBirth(1970 + random.nextInt(50))
                    .monthOfBirth(Months.APRIL)
                    .build());

            students.add(Student.builder()
                    .name(faker.funnyName().name())
                    .score(Stream.generate(() -> random.nextInt(2, 5)).map(String::valueOf).limit(random.nextInt(10)).toList())
                    .build());
        }
        Writable writable = new CSVWriter();
        writable.writeToFile(people, "file");
        writable.writeToFile(students, "file2");


    }
}