package hellocucumber;

import backend.Course;
import backend.Student;
import backend.Teacher;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

import java.util.HashSet;

import static org.junit.Assert.*;

class canIVote {
    static String getStatus(int hours) {
        Teacher teacher = new Teacher("Test", "d@d.dk", "edu 1");
        teacher.setPsTeaching(hours);
        String voteStatus = "No";
        if(teacher.getVoteRight()){
            voteStatus = "Yes";
        }
        return voteStatus;
    }
}

public class Stepdefs {
    private int work_hours;
    private String actualAnswer;
    private Teacher t1;
    private Course[] course_list;

    private Student s1;
    private HashSet<Course> attendingCourses;

    @Given("I worked {string} hours")
    public void i_worked_hours(String string) {
        work_hours = Integer.parseInt(string);
    }

    @When("I ask whether I can vote")
    public void i_ask_whether_I_can_vote() {
        actualAnswer = canIVote.getStatus(work_hours);
    }

    @Then("I should be told {string}")
    public void i_should_be_told(String string) {
        assertEquals(string, actualAnswer);
    }

    @Given("My name is {string}, email is {string} and my background is {string}")
    public void my_name_is_email_is_and_my_background_is(String string, String string2, String string3) {
        t1 = new Teacher(string, string2, string3);
    }

    @When("When I check my course list")
    public void when_I_check_my_course_list() {
        course_list = t1.getTeachingCourses();
    }

    @Then("I should have {int} courses")
    public void i_should_have_courses(Integer int1) {
        int courses = 0;

        for (int i = 0; i < course_list.length; i++) {
            if(course_list[i] != null){
                courses++;
            }
        }

        assertEquals(new Integer(courses), int1);
    }

    @Given("My first name is {string}, last name is {string}. My email is {string} and my birthday is {string}")
    public void my_first_name_is_last_name_is_My_email_is_and_my_birthday_is(String string, String string2, String string3, String string4) {
        s1 = new Student(string, string2, string4, string3);
    }

    @When("I check my attending courses")
    public void i_check_my_attending_courses() {
        attendingCourses = s1.getCourses();
    }

    @Then("I should have {int} attending courses")
    public void i_should_have_attending_courses(Integer int1) {
        assertEquals(new Integer(attendingCourses.size()), int1);
    }

}