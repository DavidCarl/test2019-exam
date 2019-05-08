import java.util.HashMap;
import java.util.NoSuchElementException;

public class Course {

    private String _name;
    private Teacher _teacher;
    private String _roomNr;
    private int _price;
    private HashMap<String, Integer> _coursePayments;

    public Course(String name, Teacher teacher, String roomNr, int price) {
        _name = name;
        _teacher = teacher;
        _roomNr = roomNr;
        _price = price;
        _coursePayments = new HashMap<String, Integer>();
    }

    public String getName() {
        return _name;
    }

    public Teacher getTeacher() {
        return _teacher;
    }

    public String getRoomNr() {
        return _roomNr;
    }

    public int getCoursePrice() {
        return _price;
    }

    public HashMap<String, Integer> getCoursePayments() {
        return _coursePayments;
    }

    public void enroll(Student student){
        if(_coursePayments.containsKey(student.getEmail()))
            throw new IllegalStateException("Student already enrolled");

        if(student.getAge() < 18)
            throw new IllegalStateException("Student is a minor");

        _coursePayments.put(student.getEmail(), 0);
    }

}