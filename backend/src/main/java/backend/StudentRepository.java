package backend;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class StudentRepository {
    HashMap<String, Student> students;
    private static final StudentRepository SINGLE_INSTANCE = new StudentRepository();

    private StudentRepository() {
        students = new HashMap<String, Student>();
    }
    public static StudentRepository getInstance() {
        return SINGLE_INSTANCE;
    }

    public boolean add(Student student) {
        if(students.containsKey(student.getEmail()))
            return false;

        students.put(student.getEmail(), student);
        return true;
    }

    public boolean add(String fName, String lName, String birthday, String email) {
        Student student = new Student(fName, lName, birthday, email);

        return add(student);
    }

    public Student get(String email){
        if(!students.containsKey(email))
            throw new NoSuchElementException();

        return students.get(email);
    }

    public void remove(String email){
        if(!students.containsKey(email))
            throw new NoSuchElementException();

        students.remove(email);
    }

    public int size() {
        return students.size();
    }

    public void empty(){
        students.clear();
    }
}
