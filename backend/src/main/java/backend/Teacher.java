package backend;

import java.util.Arrays;

public class Teacher {
    //Basic information about the teacher
    private String name;
    private String eduBackground;
    private String email;

    //Course related information
    private Course[] teachingCourses = new Course[3];
    private int psTeaching = 0; //Previous Semester Teaching

    public Teacher(String name, String email, String eduBackground) {
        this.name = name;
        this.email = email;
        this.eduBackground = eduBackground;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Course[] getTeachingCourses() {
        return teachingCourses;
    }

    public boolean addCourse(Course course){
        boolean spotsLeft = false;
        int spot = -1;
        for (int i = 0; i < teachingCourses.length; i++) {
            if(teachingCourses[i] == null){
                spotsLeft = true;
                spot = i;
                break;
            }
        }
        if(spotsLeft == false){
            System.out.println("Debug: No spot left for this teacher!");
            System.out.println("Debug: You tried to add " + course);
            for (int j = 0; j < teachingCourses.length; j++) {
                System.out.println("Debug: Current course " + j + " " + teachingCourses[j].getName());
            }
            System.out.println();
            return false;
        }
        teachingCourses[spot] = course;
        return true;
    }

    public boolean removeCourse(Course course){
        boolean found = false;
        int spot = -1;
        for (int i = 0; i < teachingCourses.length; i++) {
            if(teachingCourses[i] == course){
                found = true;
                spot = i;
            }
        }
        if(found == true){
            teachingCourses[spot] = null;
            return true;
        }
        System.out.println("Debug: No course by that name has been found!");
        return false;
    }

    public void printCourses(){
        System.out.println("Course " + Arrays.toString(teachingCourses));
    }

    public int getPsTeaching() {
        return psTeaching;
    }

    public void setPsTeaching(int psTeaching) {
        if(psTeaching < 0)
            psTeaching = 0; //Do something else here! Its not possible to have minus hour lectures.
        this.psTeaching = psTeaching;
    }

    public String getName() {
        return name;
    }

    public void setEduBackground(String eduBackground) {
        this.eduBackground = eduBackground;
    }

    public String getEduBackground() {
        return eduBackground;
    }

    public boolean getVoteRight(){
        if(psTeaching >= 20){
            return true;
        }
        return false;
    }

    public boolean isEligible(){
        // a teacher should have a proper education background and hours/courses shouldn't
        // exceed their maximum specifications

        return true;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                "email='" + email + '\'' +
                ", eduBackground='" + eduBackground + '\'' +
                ", teachingCourses=" + Arrays.toString(teachingCourses) +
                ", psTeaching=" + psTeaching +
                '}';
    }
}
