import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class Topic {

    private String _name;
    private HashMap<String, Course> courses;

    public Topic(String name) {
        _name = name;
        courses = new HashMap<String, Course>();
    }

    public String getName() {
        return _name;
    }

    public boolean addCourse(String name, Teacher teacher, String roomNr, int price) {
        if(courses.containsKey(name.toLowerCase()) || !teacher.isEligible() || price < 0)
            return false;

        Course newCourse = new Course(name, teacher, roomNr, price);
        courses.put(name.toLowerCase(), newCourse);
        // ex: TopicName.CourseName
        //teacher.addCourse(_name + "." + name);
        teacher.addCourse(newCourse);
        return true;
    }

    public ArrayList<Course> getCourses() {
        return new ArrayList<Course>(courses.values());
    }

    public Course getCourse(String courseName) {
        if(courses.containsKey(courseName.toLowerCase()))
            return courses.get(courseName.toLowerCase());

        throw new NoSuchElementException();
    }

    public void deleteCourse(String courseName){
        if(courses.containsKey(courseName.toLowerCase()))
            courses.remove(courseName.toLowerCase());
        else
            throw new NoSuchElementException();
    }

    public void enrollToCourse(String courseName, Student student) {
        Course course = getCourse(courseName);
        try
        {
            course.enroll(student);
        }
        catch (IndexOutOfBoundsException exception)
        {
            String newCourseName = courseName + " I";
            addCourse(newCourseName,course.getTeacher(),course.getRoomNr()+1,course.getCoursePrice());

            enrollToCourse(newCourseName, student);
        }
    }
}