package frontend;

import backend.Course;
import backend.Student;
import backend.StudentRepository;
import backend.Teacher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentTest {

    WebDriver driver;

    @BeforeEach
    void setup() {
        System.setProperty("webdriver.gecko.driver", "geckodriver");
        driver = new FirefoxDriver();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void student_index() {
        driver.get("http://localhost:8080/2/student");
        WebDriverWait wait = new WebDriverWait(driver, 2);
        WebElement href;
        href = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signupHref")));

        assertEquals("here!", href.getText());
        assertEquals("Student section", driver.getTitle());

    }

    @Test
    void student_signup() {
        driver.get("http://localhost:8080/2/student/signup.jsp");
        WebDriverWait wait = new WebDriverWait(driver, 2);

        WebElement email;
        email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("emailField")));

        WebElement fname;
        fname = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstNameField")));

        WebElement lname;
        lname = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lastNameField")));

        WebElement button;
        button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signupbutton")));

        WebElement birthDate;
        birthDate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("birthdayField")));

        assertEquals("Student signup", driver.getTitle());

        String inputEmail = getAlphaNumericString(5) + "@testy.com";

        email.sendKeys(inputEmail);
        fname.sendKeys("testy");
        lname.sendKeys("McTesty");
        birthDate.click();
        birthDate.sendKeys("1996-10-04");
        button.click();

        WebElement status;
        status = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("messagediv")));

        assertEquals("SUCCESS", status.getText());
    }

    @Test
    void student_signup_missing_fname() {
        driver.get("http://localhost:8080/2/student/signup.jsp");
        WebDriverWait wait = new WebDriverWait(driver, 2);
        WebElement email;
        email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("emailField")));
        WebElement fname;
        fname = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstNameField")));
        WebElement lname;
        lname = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lastNameField")));
        WebElement birthDate;
        birthDate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("birthdayField")));
        WebElement button;
        button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signupbutton")));

        assertEquals("Student signup", driver.getTitle());

        String inputEmail = getAlphaNumericString(5) + "@testy.com";

        email.sendKeys(inputEmail);
        fname.sendKeys("");
        lname.sendKeys("McTesty");
        birthDate.click();
        birthDate.sendKeys("1996-10-04");
        button.click();

        WebElement status;
        status = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("messagediv")));

        assertEquals("MISSING first name!", status.getText());
    }

    @Test
    void student_signup_missing_lname() {
        driver.get("http://localhost:8080/2/student/signup.jsp");
        WebDriverWait wait = new WebDriverWait(driver, 2);
        WebElement email;
        email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("emailField")));
        WebElement fname;
        fname = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstNameField")));
        WebElement lname;
        lname = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lastNameField")));
        WebElement birthDate;
        birthDate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("birthdayField")));
        WebElement button;
        button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signupbutton")));

        assertEquals("Student signup", driver.getTitle());

        String inputEmail = getAlphaNumericString(5) + "@testy.com";

        email.sendKeys(inputEmail);
        fname.sendKeys("Test");
        lname.sendKeys("");
        birthDate.click();
        birthDate.sendKeys("1996-10-04");
        button.click();

        WebElement status;
        status = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("messagediv")));

        assertEquals("MISSING last name!", status.getText());
    }

    @Test
    void student_signup_missing_email() {
        driver.get("http://localhost:8080/2/student/signup.jsp");
        WebDriverWait wait = new WebDriverWait(driver, 2);
        WebElement email;
        email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("emailField")));
        WebElement fname;
        fname = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstNameField")));
        WebElement lname;
        lname = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lastNameField")));
        WebElement button;
        button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signupbutton")));
        WebElement birthDate;
        birthDate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("birthdayField")));

        assertEquals("Student signup", driver.getTitle());

        String inputEmail = "";

        email.sendKeys(inputEmail);
        fname.sendKeys("test");
        lname.sendKeys("McTesty");
        birthDate.click();
        birthDate.sendKeys("1996-10-04");
        button.click();

        WebElement status;
        status = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("messagediv")));

        assertEquals("MISSING email!", status.getText());
    }

    @Test
    void student_signup_missing_birthday() {
        driver.get("http://localhost:8080/2/student/signup.jsp");
        WebDriverWait wait = new WebDriverWait(driver, 2);
        WebElement email;
        email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("emailField")));
        WebElement fname;
        fname = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstNameField")));
        WebElement lname;
        lname = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lastNameField")));
        WebElement birthDate;
        birthDate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("birthdayField")));
        WebElement button;
        button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signupbutton")));

        assertEquals("Student signup", driver.getTitle());

        String inputEmail = getAlphaNumericString(5) + "@testy.com";

        email.sendKeys(inputEmail);
        fname.sendKeys("Test");
        lname.sendKeys("McTesty");
        button.click();

        WebElement status;
        status = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("messagediv")));

        assertEquals("MISSING birthday!", status.getText());
    }


    @Test
    void student_signup_duplicate() {
        driver.get("http://localhost:8080/2/student/signup.jsp");
        WebDriverWait wait = new WebDriverWait(driver, 2);
        WebElement email;
        email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("emailField")));
        WebElement fname;
        fname = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstNameField")));
        WebElement lname;
        lname = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lastNameField")));
        WebElement button;
        button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signupbutton")));

        WebElement birthDate;
        birthDate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("birthdayField")));

        assertEquals("Student signup", driver.getTitle());

        String inputEmail = getAlphaNumericString(5) + "@testy.com";

        email.sendKeys(inputEmail);
        fname.sendKeys("Test");
        lname.sendKeys("McTesty");
        birthDate.click();
        birthDate.sendKeys("1996-10-04");
        button.click();

        WebElement status;
        status = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("messagediv")));

        assertEquals("SUCCESS", status.getText());

        button.click();
        assertEquals("ERROR", status.getText());
    }

    private String apiCall(String endpoint) {
        String data = "";

        try {
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                data = output;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return data;
    }

    public String getAlphaNumericString(int n) {
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());
            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }

    @Test
    void studentPanelLoadCourses() {
        String studentEmail = getAlphaNumericString(5).toLowerCase() + "@testy.com";
        String studentFName = getAlphaNumericString(5).toLowerCase();
        String studentLName = getAlphaNumericString(5).toLowerCase();
        String studentBirthday = "10-04-1996";
        apiCall("http://localhost:8080/2/api/student/register/" + studentFName + "/" + studentLName + "/" + studentBirthday + "/" + studentEmail, "POST");

        String teacherEmail = getAlphaNumericString(5).toLowerCase() + "@testy.com";
        String teacherName = getAlphaNumericString(5).toLowerCase();
        String teacherEducation = getAlphaNumericString(5).toLowerCase();

        apiCall("http://localhost:8080/2/api/teacher/register/" + teacherName + "/" + teacherEmail + "/" + teacherEducation, "POST");

        String topic = getAlphaNumericString(5).toLowerCase();
        String courseName = getAlphaNumericString(5).toLowerCase();
        String courseRoom = getAlphaNumericString(5).toLowerCase();
        String price = "120";

        apiCall("http://localhost:8080/2/api/principal/register/addTopic/" + topic, "POST");
        apiCall("http://localhost:8080/2/api/principal/register/addCourse/" + courseName + "/" + topic + "/" + courseRoom + "/" + teacherEmail + "/" + price, "POST");
        apiCall("http://localhost:8080/2/api/student/enrol/" + studentEmail + "/" + courseName, "PUT");


        driver.get("http://localhost:8080/2/student/panel.jsp");
        WebDriverWait wait = new WebDriverWait(driver, 2);
        WebElement emailField;
        emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("emailField")));
        WebElement button;
        button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("coursesbutton")));

        emailField.sendKeys(studentEmail);
        button.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("course")));

        ArrayList<WebElement> courses;
        courses = (ArrayList<WebElement>) driver.findElements(By.className("course"));

        WebElement course = courses.get(0);
        assertEquals(courseName, course.findElement(By.className("_name")).getText());
        assertEquals(courseRoom, course.findElement(By.className("_roomNr")).getText());
        assertEquals(price, course.findElement(By.className("_price")).getText());

    }

    @Test
    void studentPanelLoadStudentInfo() {
        String inputEmail = getAlphaNumericString(5) + "@testy.com";
        String inputFName = getAlphaNumericString(5);
        String inputLName = getAlphaNumericString(5);
        String inputBirthday = "10-04-1996";

        apiCall("http://localhost:8080/2/api/student/register/" + inputFName + "/" + inputLName + "/" + inputBirthday + "/" + inputEmail, "POST");

        driver.get("http://localhost:8080/2/student/panel.jsp");
        WebDriverWait wait = new WebDriverWait(driver, 2);


        WebElement infoButton;
        infoButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("studentInfoButton")));

        WebElement emailField;
        emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("emailField")));


        emailField.sendKeys(inputEmail);
        infoButton.click();

        WebElement infoFName;
        infoFName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fName")));

        WebElement infoLName;
        infoLName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lName")));

        WebElement infoEmail;
        infoEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));

        WebElement infoBirthday;
        infoBirthday = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("birthday")));

        assertEquals("First name: " + inputFName, infoFName.getText());
        assertEquals("Last name: " + inputLName, infoLName.getText());
        assertEquals("Email: " + inputEmail, infoEmail.getText());
        assertEquals("Birthday: 10-4-1996", infoBirthday.getText());
    }

    @Test
    void studentPanelLoadStudentInfoStudentDoesNotExsist() {
        driver.get("http://localhost:8080/2/student/panel.jsp");
        WebDriverWait wait = new WebDriverWait(driver, 2);
        WebElement email;
        email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("emailField")));
        WebElement button;
        button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("studentInfoButton")));

        email.sendKeys("thisEmail@DoesNotExsist.com");
        button.click();

        WebElement error;
        error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));

        String expected = "Student with this email is not found!";
        assertEquals(expected, error.getText());
    }

    private String apiCall(String endpoint, String type) {
        String data = "";

        try {
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(type);
            conn.setRequestProperty("Accept", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                data = output;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return data;
    }
}
