package backend;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

public class Student {
    private String _fName;
    private String _lName;
    private LocalDate _birthday;
    private String _email;
    private DateTimeFormatter _formatter;
    private HashSet<Course> _courses;

    public Student(String firstName, String lastName, String birthday, String email) {
        _formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        _fName = firstName;
        _lName = lastName;
        _birthday = LocalDate.parse(birthday, _formatter);
        _email = email;
        _courses = new HashSet<Course>();

    }

    public String getFName() {
        return _fName;
    }

    public String getLName() {
        return _lName;
    }

    public LocalDate getBirthday() {
        return _birthday;
    }

    public String getEmail() {
        return _email;
    }

    public int getAge(){
        return Period.between(_birthday, LocalDate.now()).getYears();
    }

    public HashSet<Course> getCourses() {
        return _courses;
    }

    public void addCourse(Course course){
        _courses.add(course);
    }
}
