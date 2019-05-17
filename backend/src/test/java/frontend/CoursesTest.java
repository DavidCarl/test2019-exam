package frontend;

import backend.TeacherRepository;
import backend.TopicRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CoursesTest {

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
        driver.get("http://localhost:8080/2/course");
        WebDriverWait wait = new WebDriverWait(driver, 2);
        WebElement listTitle;
        listTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("listTitle")));

        assertEquals("List of courses", listTitle.getText());
        assertEquals("Courses section", driver.getTitle());
    }

    @Test
    void course_list_refreshes() {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        List<WebElement> courses;

        // register new topic
        apiCall("http://localhost:8080/2/api/principal/register/addTopic/Arts", "POST");
        // register new teachers
        apiCall("http://localhost:8080/2/api/teacher/register/Lenard%20Lyndor/lylly@gmail.com/Teacher%20edu", "POST");
        apiCall("http://localhost:8080/2/api/teacher/register/Smith%20Stan/smt@gmail.com/Teacher%20edu", "POST");
        // register new courses
        apiCall("http://localhost:8080/2/api/principal/register/addCourse/Singing/Arts/342/lylly@gmail.com/300", "POST");
        apiCall("http://localhost:8080/2/api/principal/register/addCourse/Music/Arts/102/lylly@gmail.com/500", "POST");
        apiCall("http://localhost:8080/2/api/principal/register/addCourse/Drawing/Arts/204/lylly@gmail.com/200", "POST");

        driver.get("http://localhost:8080/2/course");
        courses = wait.until(ExpectedConditions.presenceOfNestedElementsLocatedBy(By.id("listOfCourses"), By.tagName("li")));

        int currentCoursesCount = courses.size();

        // register new course
        apiCall("http://localhost:8080/2/api/principal/register/addCourse/Painting/Arts/300/smt@gmail.com/200", "POST");

        driver.findElement(By.id("refreshBtn")).click();
        courses = wait.until(ExpectedConditions.presenceOfNestedElementsLocatedBy(By.id("listOfCourses"), By.tagName("li")));

        assertTrue(currentCoursesCount < courses.size());

    }

    private String apiCall(String endpoint, String method) {
        String data = "";

        try {
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
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
