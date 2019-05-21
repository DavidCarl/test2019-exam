package backend;

public class main {

    public static void main(String[] args) {
        Student student = new Student("Bent", "Smith","13-09-1998", "bettysmith@gmail.com");

        Teacher teach = new Teacher("lul", "lylly@gmail.com", "Teacher edu");
        Course course1 = new Course("Intro programming", teach, "101", 100);
        Course course2 = new Course("Advanced programming", teach, "102", 200);
        Course course3 = new Course("Expert programming", teach, "103", 300);

        student.addCourse(course1);
        student.addCourse(course2);
        student.addCourse(course3);


        IStudentData studentData = new MongoStudentCollection("test");
        studentData.add(student);

        //Student test = studentData.getTopic("monysmith@gmail.com");
       // System.out.println(test.getFName() + " " + test.getLName() + ", " + test.getBirthday() + ", " + test.getEmail());
    }

    public static Student registerStudent(String firstName, String lastName, String birthday, String email) {
        Student student = new Student(firstName, lastName, birthday, email);

        return student;
    }
}
