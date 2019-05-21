package backend;

import java.util.Collection;

public interface IStudentData {
    boolean add(Student student);
    boolean add(String fName, String lName, String birthday, String email);
    Student get(String email);
    void remove(String email);
    int size();
    void empty();
    Collection<Student> getAllStudents();
}
