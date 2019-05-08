import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Student {
    private String _fName;
    private String _lName;
    LocalDate _birthday;
    private String _email;
    DateTimeFormatter formatter;

    public Student(String firstName, String lastName, String birthday, String email) {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        _fName = firstName;
        _lName = lastName;
        _birthday = LocalDate.parse(birthday, formatter);
        _email = email;
    }

    public String getFName() {
        return _fName;
    }

    public String getLName() {
        return _lName;
    }

    public LocalDate getBirthday() {
        return _birthday;
    }

    public String getEmail() {
        return _email;
    }

    public int getAge(){
        return Period.between(_birthday, LocalDate.now()).getYears();
    }
}
