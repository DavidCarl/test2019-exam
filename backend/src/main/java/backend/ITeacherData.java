package backend;

import java.util.Collection;

public interface ITeacherData {
    boolean add(Teacher teacher);
    boolean add(String name, String email, String eduBackground);
    Teacher get(String email);
    void remove(String email);
    int size();
    void empty();
    Collection<Teacher> getAllTeachers();
}
