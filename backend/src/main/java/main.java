public class main {

    public static void main(String[] args) {
    }

    public static Student registerStudent(String firstName, String lastName, String birthday, String email) {
        Student student = new Student(firstName, lastName, birthday, email);

        return student;
    }
}
