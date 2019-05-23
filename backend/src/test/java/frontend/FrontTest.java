package frontend;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FrontTest {
    WebDriver driver;

    @BeforeEach
    void setup(){
        System.setProperty("webdriver.gecko.driver", "geckodriver");
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        driver = new FirefoxDriver(options);
    }

    @AfterEach
    void teardown(){
        driver.quit();
    }

    @Test
    void main_index(){
        driver.get("http://localhost:8080/2/");
        WebDriverWait wait = new WebDriverWait(driver, 2);

        WebElement href;
        WebElement principal;
        WebElement studentHref;
        WebElement teacherHref;
        href = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("teacherHref")));
        principal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("principalHref")));
        studentHref = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("studentHref")));
        teacherHref = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("teacherHref")));

        assertEquals("here!", href.getText());
        assertEquals("here!", principal.getText());
        assertEquals("here!", studentHref.getText());
        assertEquals("here!", teacherHref.getText());

        assertEquals("Lyngby Evening School", driver.getTitle());
    }

    @Test
    void principal_overview_teacher(){
        String email = getAlphaNumericString(10) + "@teacher.com";
        String student_email = getAlphaNumericString(10) + "@student.com";
        String course = getAlphaNumericString(10);
        String price = getNumericString(3);
        String roomNr = getNumericString(3);

        //Create teacher
        apiCallPost("http://localhost:8080/2/api/teacher/register/TestName/" + email + "/Edu_2");
        //Create topic
        apiCallPost("http://localhost:8080/2/api/principal/register/addTopic/programming");
        //Create course
        apiCallPost("http://localhost:8080/2/api/principal/register/addCourse/" + course + "/programming/" + roomNr + "/" + email + "/" + price);
        //Create student
        apiCallPost("http://localhost:8080/2/api/student/register/test/test/15-02-1989/" + student_email);

        driver.get("http://localhost:8080/2/");
        WebDriverWait wait = new WebDriverWait(driver, 2);

        WebElement table;
        table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("teacherTable")));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        for (WebElement row: rows) {
            List<WebElement> th = row.findElements(By.tagName("th"));
            if(th.size() > 0){
                assertEquals(th.get(0).getText(), "Teacher name");
                assertEquals(th.get(1).getText(), "Teacher email");
                assertEquals(th.get(2).getText(), "Teacher education");
            }
        }
        for (WebElement row : rows) {
            List<WebElement> td = row.findElements(By.tagName("td"));
            if(td.size() > 0){
                if(td.get(1).getText() == email){
                    assertEquals(td.get(0).getText(), "TestName");
                    assertEquals(td.get(1).getText(), email);
                    assertEquals(td.get(2).getText(), "Edu_2");
                }
            }
        }
    }

    @Test
    void principal_overview_course(){
        String email = getAlphaNumericString(10) + "@teacher.com";
        String student_email = getAlphaNumericString(10) + "@student.com";
        String course = getAlphaNumericString(10);
        String price = getNumericString(3);
        String roomNr = getNumericString(3);

        //Create teacher
        apiCallPost("http://localhost:8080/2/api/teacher/register/TestName/" + email + "/Edu_2");
        //Create topic
        apiCallPost("http://localhost:8080/2/api/principal/register/addTopic/programming");
        //Create course
        apiCallPost("http://localhost:8080/2/api/principal/register/addCourse/" + course + "/programming/" + roomNr + "/" + email + "/" + price);
        //Create student
        apiCallPost("http://localhost:8080/2/api/student/register/test/test/15-02-1989/" + student_email);

        driver.get("http://localhost:8080/2/");
        WebDriverWait wait = new WebDriverWait(driver, 2);

        WebElement table;
        table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("courseTable")));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        for (WebElement row: rows) {
            List<WebElement> th = row.findElements(By.tagName("th"));
            if(th.size() > 0){
                assertEquals(th.get(0).getText(), "Course name");
                assertEquals(th.get(1).getText(), "Course room");
                assertEquals(th.get(2).getText(), "Course price");
            }
        }
        for (WebElement row : rows) {
            List<WebElement> td = row.findElements(By.tagName("td"));
            if(td.size() > 0){
                if(td.get(0).getText() == course){
                    assertEquals(td.get(0).getText(), course);
                    assertEquals(td.get(1).getText(), roomNr);
                    assertEquals(td.get(2).getText(), price);
                }
            }
        }
    }

    @Test
    void principal_overview_student() {
        String email = getAlphaNumericString(10) + "@teacher.com";
        String student_email = getAlphaNumericString(10) + "@student.com";
        String course = getAlphaNumericString(10);
        String price = getNumericString(3);
        String roomNr = getNumericString(3);

        //Create teacher
        apiCallPost("http://localhost:8080/2/api/teacher/register/TestName/" + email + "/Edu_2");
        //Create topic
        apiCallPost("http://localhost:8080/2/api/principal/register/addTopic/programming");
        //Create course
        apiCallPost("http://localhost:8080/2/api/principal/register/addCourse/" + course + "/programming/" + roomNr + "/" + email + "/" + price);
        //Create student
        apiCallPost("http://localhost:8080/2/api/student/register/test/test/15-02-1989/" + student_email);

        driver.get("http://localhost:8080/2/");
        WebDriverWait wait = new WebDriverWait(driver, 2);

        WebElement studentTable;
        studentTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("studentTable")));
        List<WebElement> studentRows = studentTable.findElements(By.tagName("tr"));
        for (WebElement row: studentRows) {
            List<WebElement> th = row.findElements(By.tagName("th"));
            if(th.size() > 0){
                assertEquals(th.get(0).getText(), "Student name");
                assertEquals(th.get(1).getText(), "Student email");
                assertEquals(th.get(2).getText(), "Birthday");
            }
        }
        for (WebElement row : studentRows) {
            List<WebElement> td = row.findElements(By.tagName("td"));
            if(td.size() > 0){
                if(td.get(1).getText() == student_email){
                    assertEquals(td.get(0).getText(), "test test");
                    assertEquals(td.get(1).getText(), student_email);
                    assertEquals(td.get(2).getText(), "15-02-1989");
                }
            }
        }
    }

    private String apiCallPost(String endpoint) {
        String data = "";

        try {
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
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
        String AlphaNumericString = "123456789"
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

    public String getNumericString(int n) {
        // chose a Character random from this String
        String AlphaNumericString = "123456789";
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

}
