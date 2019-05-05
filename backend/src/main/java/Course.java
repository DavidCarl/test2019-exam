public class Course {

    private String _name;
    private Teacher _teacher;
    private String _roomNr;

    public Course(String name, Teacher teacher, String roomNr) {
        _name = name;
        _teacher = teacher;
        _roomNr = roomNr;
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
}
