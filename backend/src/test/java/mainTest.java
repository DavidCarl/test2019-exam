import backend.Student;
import backend.main;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class mainTest {

    @Test
    public void shouldCreateStudentObject(){

        Student student = main.registerStudent("John", "Andersen", "18-05-1991", "jand@gmail.com");

        assertNotNull(student);
    }

    @Test
    public void shouldGetStudentDetails(){
        Student student = main.registerStudent("Thomas", "Cook", "18-09-1987", "thcook@gmail.com");

        assertAll("Test if you can getTopic backend.Student details",
                () -> assertEquals("Thomas", student.getFName()),
                () -> assertEquals("Cook", student.getLName()),
                () -> assertEquals(LocalDate.of(1987, 9, 18), student.getBirthday()),
                () -> assertEquals("thcook@gmail.com", student.getEmail()),
                () -> assertEquals(31, student.getAge())
        );
    }
}