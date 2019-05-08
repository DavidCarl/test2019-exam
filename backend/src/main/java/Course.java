import java.util.HashMap;
import java.util.NoSuchElementException;

public class Course {
    private static final int _MAXSTUDENTS = 20;

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

        if(_coursePayments.size() >= _MAXSTUDENTS)
            throw new IndexOutOfBoundsException();

        if(student.getAge() < 18)
            throw new IllegalStateException("Student is a minor");

        _coursePayments.put(student.getEmail(), 0);
    }

    public int acceptPayment(Student student, int payment){
        if(!_coursePayments.containsKey(student.getEmail()))
            throw new NoSuchElementException();

        if(payment <= 0)
            throw new IllegalArgumentException("Payment cannot be 0 or less");

        int currentPayment = _coursePayments.get(student.getEmail());
        int leftover = _price - (currentPayment + payment);

        if(leftover <= 0)
            _coursePayments.put(student.getEmail(), _price);
        else
            _coursePayments.put(student.getEmail(), currentPayment + payment);

        return leftover * -1;
    }
}