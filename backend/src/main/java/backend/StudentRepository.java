package backend;

import java.util.Collection;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class StudentRepository implements IStudentData {
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

    public Collection<Student> getAllStudents() {
        return students.values();
    }
}
