import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {
    Teacher teach;

    @BeforeEach //This is to isolate the different test, this way we reset our teacher object before evey test
    void teacherSetup(){
        teach = new Teacher("lul", "Teacher edu");
    }

    // In this section we are testing the Course array the teacher have.
    // Here we test if the stuff gets added correctly as we want it to
    @Test
    void getTeachingCourses() {
        String[] emptyArray = new String[3];

        assertEquals(teach.getTeachingCourses().length, 3);
        assertArrayEquals(teach.getTeachingCourses(), emptyArray);
        emptyArray[0] = "Test 1";
        teach.addCourse("Test 1");
        assertArrayEquals(teach.getTeachingCourses(), emptyArray);
        emptyArray[1] = "Test 2";
        teach.addCourse("Test 2");
        assertArrayEquals(teach.getTeachingCourses(), emptyArray);
        emptyArray[2] = "Test 3";
        teach.addCourse("Test 3");
        assertArrayEquals(teach.getTeachingCourses(), emptyArray);
    }

    // Here we test the limiter on the amount of courses the teacher can have.
    @Test
    void addCourse() {
        assertAll("Test Course limit on teacher",
                () -> assertTrue(teach.addCourse("Test 0")),
                () -> assertTrue(teach.addCourse("Test 1")),
                () -> assertTrue(teach.addCourse("Test 2")),
                () -> assertFalse(teach.addCourse("Test 3")),
                () -> assertTrue(teach.removeCourse("Test 1")),
                () -> assertTrue(teach.addCourse("Test 4"))
        );
    }

    // Here we test if the removal af a added course does actually work.
    @Test
    void removeCourse() {
        String[] testArray = new String[3];

        assertArrayEquals(teach.getTeachingCourses(), testArray);
        teach.addCourse("Test 1");
        testArray[0] = "Test 1";
        assertArrayEquals(teach.getTeachingCourses(), testArray);
        teach.removeCourse("Test 1");
        testArray[0] = null;
        assertArrayEquals(teach.getTeachingCourses(), testArray);
    }

    // Here we test if its possible to screw up the array by trying to remove a course before anything has been added.
    @Test
    void removeFromEmptyCourse(){
        String[] testArray = new String[3];

        assertArrayEquals(teach.getTeachingCourses(), testArray);
        assertFalse(teach.removeCourse("Test 1"));
    }

    // In this section we are testing the amount of worked hours from the last semester.
    // Here we test the hours worked last semester and if they have voting rights based on that.
    @Test
    void setPsTeachingNegative() {
        assertEquals(teach.getPsTeaching(), 0);
        teach.setPsTeaching(-10);
        assertEquals(teach.getPsTeaching(), 0);
    }

    @Test
    void voteRight19Hours(){
        assertFalse(teach.getVoteRight());
        teach.setPsTeaching(19);
        assertFalse(teach.getVoteRight());
    }

    @Test
    void voteRight21Hours(){
        assertFalse(teach.getVoteRight());
        teach.setPsTeaching(21);
        assertTrue(teach.getVoteRight());
    }

    @Test
    void voteRight20Hours() {
        assertFalse(teach.getVoteRight());
        teach.setPsTeaching(20);
        assertTrue(teach.getVoteRight());
    }
}