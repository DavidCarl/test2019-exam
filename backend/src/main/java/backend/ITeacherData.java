package backend;

import java.util.Collection;

public interface ITeacherData {
    boolean add(Teacher teacher);

    default boolean add(String name, String email, String eduBackground) {
        Teacher teacher = new Teacher(name, email, eduBackground);
        return add(teacher);
    }

    Teacher get(String email);
    void remove(String email);
    int size();
    void empty();
    Collection<Teacher> getAllTeachers();
}
