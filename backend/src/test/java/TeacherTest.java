import backend.Course;
import backend.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {
    Teacher teach;

    @BeforeEach //This is to isolate the different test, this way we reset our teacher object before evey test
    void teacherSetup(){
        teach = new Teacher("lul", "lylly@gmail.com", "Teacher edu");
    }

    // In this section we are testing the Course array the teacher have.
    // Here we test if the stuff gets added correctly as we want it to
    @Test
    void getTeachingCourses() {
        Course[] emptyArray = new Course[3];

        Course course1 = new Course("Intro programming", teach, "101", 100);
        Course course2 = new Course("Advanced programming", teach, "102", 200);
        Course course3 = new Course("Expert programming", teach, "103", 300);

        assertEquals(teach.getTeachingCourses().length, 3);
        assertArrayEquals(teach.getTeachingCourses(), emptyArray);
        emptyArray[0] = course1;
        teach.addCourse(course1);
        assertArrayEquals(teach.getTeachingCourses(), emptyArray);
        emptyArray[1] = course2;
        teach.addCourse(course2);
        assertArrayEquals(teach.getTeachingCourses(), emptyArray);
        emptyArray[2] = course3;
        teach.addCourse(course3);
        assertArrayEquals(teach.getTeachingCourses(), emptyArray);
    }

    // Here we test the limiter on the amount of courses the teacher can have.
    @Test
    void addCourse() {
        Course course1 = new Course("Intro programming", teach, "101", 100);
        Course course2 = new Course("Advanced programming", teach, "102", 200);
        Course course3 = new Course("Expert programming", teach, "103", 300);
        Course course4 = new Course("Baking", teach, "201", 400);

        assertAll("Test Course limit on teacher",
                () -> assertTrue(teach.addCourse(course1)),
                () -> assertTrue(teach.addCourse(course2)),
                () -> assertTrue(teach.addCourse(course3)),
                () -> assertFalse(teach.addCourse(course4)),
                () -> assertTrue(teach.removeCourse(course3)),
                () -> assertTrue(teach.addCourse(course4))
        );
    }

    // Here we test if the removal af a added course does actually work.
    @Test
    void removeCourse() {
        Course[] testArray = new Course[3];

        Course course1 = new Course("Intro programming", teach, "101", 100);

        assertArrayEquals(teach.getTeachingCourses(), testArray);
        teach.addCourse(course1);
        testArray[0] = course1;
        assertArrayEquals(teach.getTeachingCourses(), testArray);
        teach.removeCourse(course1);
        testArray[0] = null;
        assertArrayEquals(teach.getTeachingCourses(), testArray);
    }

    // Here we test if its possible to screw up the array by trying to remove a course before anything has been added.
    @Test
    void removeFromEmptyCourse(){
        Course[] testArray = new Course[3];

        Course course1 = new Course("Intro programming", teach, "101", 500);

        assertArrayEquals(teach.getTeachingCourses(), testArray);
        assertFalse(teach.removeCourse(course1));
    }

    // In this section we are testing the amount of worked hours from the last semester.
    // Here we test the hours worked last semester and if they have voting rights based on that.

    @ParameterizedTest
    @ValueSource(ints={-10, -20})
    void setPsTeachingNegative(int hours) {
        assertEquals(teach.getPsTeaching(), 0);
        teach.setPsTeaching(hours);
        assertEquals(teach.getPsTeaching(), 0);
    }

    @ParameterizedTest
    @ValueSource(ints={19, 5})
    void voteRightNotEligible(int hours){
        assertFalse(teach.getVoteRight());
        teach.setPsTeaching(hours);
        assertFalse(teach.getVoteRight());
    }

    @ParameterizedTest
    @ValueSource(ints={20, 21})
    void voteRightEligible(int hours){
        assertFalse(teach.getVoteRight());
        teach.setPsTeaching(hours);
        assertTrue(teach.getVoteRight());
    }

    @Test
    void isEligible(){
        assertTrue(teach.isEligible());
    }
}