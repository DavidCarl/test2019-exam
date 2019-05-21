package backend;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;

public class TeacherRepository implements ITeacherData {
    HashMap<String, Teacher> teachers;
    private static final TeacherRepository SINGLE_INSTANCE = new TeacherRepository();

    private TeacherRepository() {
        teachers = new HashMap<String, Teacher>();
    }
    public static TeacherRepository getInstance() {
        return SINGLE_INSTANCE;
    }

    public boolean add(Teacher teacher) {
        if(teachers.containsKey(teacher.getEmail()))
            return false;

        teachers.put(teacher.getEmail(), teacher);
        return true;
    }

    public Teacher get(String email){
        if(!teachers.containsKey(email))
            throw new NoSuchElementException();

        return teachers.get(email);
    }

    public void remove(String email){
        if(!teachers.containsKey(email))
            throw new NoSuchElementException();

        teachers.remove(email);
    }

    public int size() {
        return teachers.size();
    }

    public void empty(){
        teachers.clear();
    }

    public Collection<Teacher> getAllTeachers() {
        return teachers.values();
    }
}
