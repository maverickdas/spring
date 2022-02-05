package sp.practice.helloworld.student;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        Student ram = new Student(
                    // 1L,
                    "ram",
                    "ram@gmail.com",
                    // 21,
                    LocalDate.of(2000, Month.JANUARY, 10)),
                shyam = new Student(
                    // 2L,
                    "shyam",
                    "shyam@gmail.com",
                    // 20,
                    LocalDate.of(2010, Month.MAY, 13)),
                prim = new Student(
                    "prim",
                    "prim@gmail.com",
                    // 50,
                    LocalDate.of(1990, Month.JULY, 9));
        return args -> {
            studentRepository.saveAll(
                List.of(ram, shyam, prim)
            );
        };
    }
}
