package backend;

import java.util.Collection;
import java.util.NoSuchElementException;

public interface IStudentData {
    boolean add(Student student);

    default boolean add(String fName, String lName, String birthday, String email) {
        Student student = new Student(fName, lName, birthday, email);
        return add(student);
    }

    Student get(String email) throws NoSuchElementException;
    void remove(String email);
    int size();
    void empty();
    Collection<Student> getAllStudents();
}
