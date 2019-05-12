import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import backend.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

public class CourseTest {
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

    @Test
    void shouldNotAddCourseIfPriceBelowZero(){
        Teacher mockedTeacher = mock(Teacher.class);

        when(mockedTeacher.isEligible()).thenReturn(true);

        boolean addedCourse = topic.addCourse("English", mockedTeacher, "203", -1);

        assertFalse(addedCourse);
    }

    @Test
    void shouldAddCourseIfCourseIsFree(){
        Teacher mockedTeacher = mock(Teacher.class);

        when(mockedTeacher.isEligible()).thenReturn(true);

        boolean addedCourse = topic.addCourse("English", mockedTeacher, "203", 0);

        assertTrue(addedCourse);
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

        topic.enrollToCourse("French", student);

        assertThrows(IllegalStateException.class, () -> {
            topic.enrollToCourse("French", student);
        });
    }

    @Test
    void shouldNotThrowExceptionIfStudentIsNotEnrolled(){
        Student student = main.registerStudent("Betty", "Smith", "13-09-1998", "bettysmith@gmail.com");

        assertDoesNotThrow(() -> {
            topic.enrollToCourse("French", student);
        });
    }

    @Test
    void shouldThrowExceptionIfEnrollingStudentIsMinor(){
        Student student = main.registerStudent("Tom", "Jonsen", "13-04-2002", "tjonsen@gmail.com");

        assertAll("Test illegible age of enrolling student",
                () -> assertEquals(17, student.getAge()),
                () -> assertThrows(IllegalStateException.class, () -> {
                    topic.enrollToCourse("French", student);
                })
        );
    }

    @Test
    void shouldNotThrowExceptionIfEnrollingStudentIsNotMinor(){
        Student student = main.registerStudent("Teddy", "Jones", "31-01-2001", "teddy96@gmail.com");

        assertAll("Test allowed age of enrolling student",
                () -> assertEquals(18, student.getAge()),
                () -> assertDoesNotThrow(() -> {
                    topic.enrollToCourse("French", student);
                })
        );
    }

    @Test
    void studentShouldBeInCourseEnrollmentsList(){
        Student student = main.registerStudent("Teddy", "Jones", "31-01-2001", "teddy96@gmail.com");

        Course french = topic.getCourse("French");
        topic.enrollToCourse(french.getName(), student);

        assertTrue(french.getCoursePayments().containsKey(student.getEmail()));
    }

    @Test
    void studentInitialPaymentIsZeroWhenEnrolling(){
        Student student = main.registerStudent("Teddy", "Jones", "31-01-2001", "teddy96@gmail.com");

        Course french = topic.getCourse("French");
        topic.enrollToCourse(french.getName(), student);

        assertEquals(0, french.getCoursePayments().get(student.getEmail()));
    }

    @Test
    void shouldThrowExceptionIfNotEnrolledStudentWhenAcceptingPayment(){
        Student notEnrolled = main.registerStudent("Anna", "Unenrolled", "13-09-2000", "annaunenr@gmail.com");

        assertThrows(NoSuchElementException.class, () -> {
            topic.getCourse("French").acceptPayment(notEnrolled, 1);
        });
    }

    @Test
    void shouldThrowExceptionIfPaymentLessOrEqualTo0(){
        Student enrolled = main.registerStudent("Anna", "Enrolled", "13-09-2000", "annaunenr@gmail.com");

        Course french = topic.getCourse("French");
        topic.enrollToCourse(french.getName(), enrolled);

        assertThrows(IllegalArgumentException.class, () -> {
            french.acceptPayment(enrolled, 0);
        });
    }

    @Test
    void shouldReturnTheExtraPaymentAsPositive(){

        Teacher mockedTeacher = mock(Teacher.class);
        when(mockedTeacher.isEligible()).thenReturn(true);

        topic.addCourse("Chinese", mockedTeacher, "303", 200);
        Course chinese = topic.getCourse("Chinese");

        Student student = main.registerStudent("Sara", "Jackson", "25-03-1999", "sara99@gmail.com");

        topic.enrollToCourse(chinese.getName(), student);

        assertAll("Test positive returned price",
                () -> assertEquals(150, chinese.acceptPayment(student, 350)),
                () -> assertTrue(chinese.acceptPayment(student, 20) > 0)
        );
    }

    @Test
    void shouldReturnTheMissingPaymentAsNegative(){

        Teacher mockedTeacher = mock(Teacher.class);
        when(mockedTeacher.isEligible()).thenReturn(true);

        topic.addCourse("Chinese", mockedTeacher, "303", 200);
        Course chinese = topic.getCourse("Chinese");

        Student student = main.registerStudent("Sara", "Jackson", "25-03-1999", "sara99@gmail.com");

        topic.enrollToCourse(chinese.getName(), student);

        assertAll("Test positive returned price",
                () -> assertEquals(-50, chinese.acceptPayment(student, 150)),
                () -> assertTrue(chinese.acceptPayment(student, 10) < 0)
        );
    }

    @Test
    void shouldCreateNewCourseIfMaxStudentsReached(){
        for(int i = 0; i < 20; i++){
            Student student = main.registerStudent("Sara" + i, "Jackson", "25-03-1999", "sara99" + i + "@gmail.com");
            topic.enrollToCourse("French", student);
        }

        Student jane = main.registerStudent("Jane", "Jackson", "25-03-1999", "jaja@gmail.com");

        topic.enrollToCourse("French", jane);

        assertNotNull(topic.getCourse("French I"));
    }

    @Test
    void shouldThrowExceptionIfStudentLimitPassed(){
        Course french = topic.getCourse("French");

        for(int i = 0; i < 20; i++){
            Student student = main.registerStudent("Sara" + i, "Jackson", "25-03-1999", "sara99" + i + "@gmail.com");
            topic.enrollToCourse(french.getName(), student);
        }

        Student jane = main.registerStudent("Jane", "Jackson", "25-03-1999", "jaja@gmail.com");
        assertThrows(IndexOutOfBoundsException.class, () ->
        {
            french.enroll(jane);
        });
    }
}
