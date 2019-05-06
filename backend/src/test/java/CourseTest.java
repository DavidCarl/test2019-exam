import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        topic.addCourse("French", mockedTeacher1, "107");
    }

    //Test if we can successfully add a course
    @Test
    void shouldUpdateListOfCoursesPerTopicWhenAdding() {
        // mock teacher creation
        Teacher mockedTeacher = mock(Teacher.class);

        when(mockedTeacher.isEligible()).thenReturn(true);

        topic.addCourse("English", mockedTeacher, "203");

        assertEquals(2, topic.getCourses().size());
    }

    @Test
    void shouldNotAddCourseToNotEligibleTeacher() {
        // mock teachers creation
        Teacher mockedTeacher = mock(Teacher.class);

        when(mockedTeacher.isEligible()).thenReturn(false);

        boolean addedCourse = topic.addCourse("English", mockedTeacher, "203");

        assertFalse(addedCourse);
    }

    @Test
    void shouldNotAddCoursesWithSameNameDiffCapitalization(){
        Teacher mockedTeacher = mock(Teacher.class);

        when(mockedTeacher.isEligible()).thenReturn(true);

        topic.addCourse("English", mockedTeacher, "203");
        boolean addedCourse = topic.addCourse("eNGliSH", mockedTeacher, "203");

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
}
