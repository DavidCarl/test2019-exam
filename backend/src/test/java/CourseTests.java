import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

public class CourseTests {
    Topic topic;

    @BeforeEach
        //Here the repository would be initialized before each test.
    void topicSetup(){
        topic = new Topic("Language");

        Teacher mockedTeacher1 = mock(Teacher.class);
        when(mockedTeacher1.isEligible()).thenReturn(true);
        topic.addCourse("French", mockedTeacher1, "107", 100);
    }

    //Test if we can successfully add a course
    @Test
    void shouldUpdateListOfCoursesPerTopicWhenAdding() {
        // mock teacher creation
        Teacher mockedTeacher = mock(Teacher.class);

        when(mockedTeacher.isEligible()).thenReturn(true);

        topic.addCourse("English", mockedTeacher, "203", 100);

        assertEquals(2, topic.getCourses().size());
    }

    @Test
    void shouldNotAddCourseToNotEligibleTeacher() {
        // mock teachers creation
        Teacher mockedTeacher = mock(Teacher.class);

        when(mockedTeacher.isEligible()).thenReturn(false);

        boolean addedCourse = topic.addCourse("English", mockedTeacher, "203", 200);

        assertFalse(addedCourse);
    }

    @Test
    void shouldNotAddCoursesWithSameNameDiffCapitalization(){
        Teacher mockedTeacher = mock(Teacher.class);

        when(mockedTeacher.isEligible()).thenReturn(true);

        topic.addCourse("English", mockedTeacher, "203", 150);
        boolean addedCourse = topic.addCourse("eNGliSH", mockedTeacher, "203", 150);

        assertFalse(addedCourse);
    }

    //TODO Implement a test to verify the teacher's course count is incremented

    @Test
    void courseShouldBeReturnedIfCourseNameExists(){
        Course french = topic.getCourse("French");

        assertEquals("107", french.getRoomNr());
    }

    @Test
    void courseNameShouldBeCaseInsensitive(){

        Course french = topic.getCourse("FrEnCH");

        assertEquals("107", french.getRoomNr());
    }

    @Test
    void shouldThrowExceptionIfCourseDoesNotExist(){
        assertThrows(NoSuchElementException.class, () -> {
            topic.getCourse("Danish");
        });
    }

    @Test
    void shouldUpdateListOfCoursesPerTopicWhenDeleting(){
        int currentCoursesSize = topic.getCourses().size();

        topic.deleteCourse("French");

        assertEquals(currentCoursesSize - 1 , topic.getCourses().size());
    }

    @Test
    void shouldThrowExceptionIfCourseDoesNotExistWhenDeleting(){
        assertThrows(NoSuchElementException.class, () -> {
            topic.deleteCourse("Danish");
        });
    }

    @Test
    void courseNameShouldBeCaseInsensitiveWhenDeleting(){
        int currentCoursesSize = topic.getCourses().size();

        topic.deleteCourse("FreNCH");

        assertEquals(currentCoursesSize - 1 , topic.getCourses().size());
    }

    @Test
    void shouldThrowExceptionIfStudentAlreadyEnrolled(){
        Student student = main.registerStudent("Betty", "Smith", "13-09-1998", "bettysmith@gmail.com");

        Course french = topic.getCourse("French");
        french.enroll(student);

        assertThrows(IllegalStateException.class, () -> {
            french.enroll(student);
        });
    }

    @Test
    void shouldNotThrowExceptionIfStudentIsNotEnrolled(){
        Student student = main.registerStudent("Betty", "Smith", "13-09-1998", "bettysmith@gmail.com");

        Course french = topic.getCourse("French");

        assertDoesNotThrow(() -> {
            french.enroll(student);
        });
    }

    @Test
    void shouldThrowExceptionIfEnrollingStudentIsMinor(){
        Student student = main.registerStudent("Tom", "Jonsen", "13-04-2002", "tjonsen@gmail.com");

        Course french = topic.getCourse("French");

        assertAll("Test illegible age of enrolling student",
                () -> assertEquals(17, student.getAge()),
                () -> assertThrows(IllegalStateException.class, () -> {
                    french.enroll(student);
                })
        );
    }

    @Test
    void shouldNotThrowExceptionIfEnrollingStudentIsNotMinor(){
        Student student = main.registerStudent("Teddy", "Jones", "31-01-2001", "teddy96@gmail.com");

        Course french = topic.getCourse("French");

        assertAll("Test allowed age of enrolling student",
                () -> assertEquals(18, student.getAge()),
                () -> assertDoesNotThrow(() -> {
                    french.enroll(student);
                })
        );
    }

    @Test
    void studentShouldBeInCourseEnrollmentsList(){
        Student student = main.registerStudent("Teddy", "Jones", "31-01-2001", "teddy96@gmail.com");

        Course french = topic.getCourse("French");

        assertAll("Test enrolling student",
                () -> assertEquals(18, student.getAge()),
                () -> assertDoesNotThrow(() -> {
                    french.enroll(student);
                })
        );

        assertTrue(french.getCoursePayments().containsKey(student.getEmail()));
    }

    @Test
    void studentInitialPaymentIsZeroWhenEnrolling(){
        Student student = main.registerStudent("Teddy", "Jones", "31-01-2001", "teddy96@gmail.com");

        Course french = topic.getCourse("French");

        assertAll("Test enrolling student",
                () -> assertEquals(18, student.getAge()),
                () -> assertDoesNotThrow(() -> {
                    french.enroll(student);
                })
        );

        assertEquals(0, french.getCoursePayments().get(student.getEmail()));
    }
}
