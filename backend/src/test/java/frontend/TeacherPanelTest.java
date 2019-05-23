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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeacherPanelTest {

    WebDriver driver;

    @BeforeEach
    void setup() {
        System.setProperty("webdriver.gecko.driver", "geckodriver");
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        driver = new FirefoxDriver(options);
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void teacher_index() {
        driver.get("http://localhost:8080/2/teacher");
        WebDriverWait wait = new WebDriverWait(driver, 2);
        WebElement href;
        href = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("panelHref")));

        assertEquals("here!", href.getText());
        assertEquals("Teacher section", driver.getTitle());
    }

    @Test
    void teacher_table_course(){
        String email = getAlphaNumericString(10) + "@test.com";
        String course = getAlphaNumericString(10);
        String price = getNumericString(3);
        String roomNr = getNumericString(3);

        //Create teacher
        apiCallPost("http://localhost:8080/2/api/teacher/register/TestName/" + email + "/Edu_2");
        //Create topic
        apiCallPost("http://localhost:8080/2/api/principal/register/addTopic/programming");
        //Create course
        apiCallPost("http://localhost:8080/2/api/principal/register/addCourse/" + course + "/programming/" + roomNr + "/" + email + "/" + price);

        driver.get("http://localhost:8080/2/teacher/panel.jsp");
        WebDriverWait wait = new WebDriverWait(driver, 2);

        WebElement emailField;
        emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("emailField")));
        WebElement button;
        button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("infobutton")));

        emailField.sendKeys(email);
        button.click();

        WebElement table;
        table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("table")));
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
                assertEquals(td.get(0).getText(), course);
                assertEquals(td.get(1).getText(), roomNr);
                assertEquals(td.get(2).getText(), price);
            }
        }
    }

    @Test
    void teacher_table_courses() {
        String email = getAlphaNumericString(10) + "@test.com";
        String course = getAlphaNumericString(10);
        String price = getNumericString(3);
        String roomNr = getNumericString(3);
        String priceb = getNumericString(3);
        String roomNrb = getNumericString(3);

        //Create teacher
        apiCallPost("http://localhost:8080/2/api/teacher/register/TestName/" + email + "/Edu_2");
        //Create topic
        apiCallPost("http://localhost:8080/2/api/principal/register/addTopic/programming");
        //Create multiple courses
        apiCallPost("http://localhost:8080/2/api/principal/register/addCourse/intro_" + course + "/programming/" + roomNr + "/" + email + "/" + price);
        apiCallPost("http://localhost:8080/2/api/principal/register/addCourse/adv_" + course + "/programming/" + roomNrb + "/" + email + "/" + priceb);

        driver.get("http://localhost:8080/2/teacher/panel.jsp");
        WebDriverWait wait = new WebDriverWait(driver, 2);

        WebElement emailField;
        emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("emailField")));
        WebElement button;
        button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("infobutton")));

        emailField.sendKeys(email);
        button.click();

        WebElement table;
        table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("table")));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        for (WebElement row : rows) {
            List<WebElement> th = row.findElements(By.tagName("th"));
            if (th.size() > 0) {
                assertEquals(th.get(0).getText(), "Course name");
                assertEquals(th.get(1).getText(), "Course room");
                assertEquals(th.get(2).getText(), "Course price");
            }
        }
        for (WebElement row : rows) {
            List<WebElement> td = row.findElements(By.tagName("td"));
            if (td.size() > 0) {
                if (td.get(0).getText().equals("intro_" + course)) {
                    assertEquals(td.get(0).getText(), "intro_" + course);
                    assertEquals(td.get(1).getText(), roomNr);
                    assertEquals(td.get(2).getText(), price);
                } else {
                    assertEquals(td.get(0).getText(), "adv_" + course);
                    assertEquals(td.get(1).getText(), roomNrb);
                    assertEquals(td.get(2).getText(), priceb);
                }
            }
        }
    }

    @Test
    void javascript_api_call_correct_email(){
        String email = getAlphaNumericString(10) + "@test.com";
        String course = getAlphaNumericString(10);
        String price = getNumericString(3);
        String roomNr = getNumericString(3);

        //Create teacher
        apiCallPost("http://localhost:8080/2/api/teacher/register/TestName/" + email + "/Edu_2");
        //Create topic
        apiCallPost("http://localhost:8080/2/api/principal/register/addTopic/programming");
        //Create course
        apiCallPost("http://localhost:8080/2/api/principal/register/addCourse/" + course + "/programming/" + roomNr + "/" + email + "/" + price);

        driver.get("http://localhost:8080/2/teacher/panel.jsp");
        WebDriverWait wait = new WebDriverWait(driver, 2);

        WebElement emailField;
        emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("emailField")));
        WebElement button;
        button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("infobutton")));

        emailField.sendKeys(email);
        button.click();

        WebElement status;
        status = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("messagediv")));

        assertEquals("SUCCESS", status.getText());
    }

    @Test
    void javascript_api_call_wrong_email(){
        String email = getAlphaNumericString(10) + "@test.com";

        driver.get("http://localhost:8080/2/teacher/panel.jsp");
        WebDriverWait wait = new WebDriverWait(driver, 2);

        WebElement emailField;
        emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("emailField")));
        WebElement button;
        button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("infobutton")));

        emailField.sendKeys(email);
        button.click();

        WebElement status;
        status = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("messagediv")));

        assertEquals("ERROR", status.getText());
    }

    @Test
    void teacher_name(){
        String email = getAlphaNumericString(10) + "@test.com";

        driver.get("http://localhost:8080/2/teacher/panel.jsp");
        WebDriverWait wait = new WebDriverWait(driver, 2);

        //Create teacher
        apiCallPost("http://localhost:8080/2/api/teacher/register/TestName/" + email + "/Edu_2");

        WebElement emailField;
        emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("emailField")));
        WebElement button;
        button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("infobutton")));

        emailField.sendKeys(email);
        button.click();

        WebElement name;
        name = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameDiv")));

        assertEquals("TestName", name.getText());
    }

    @Test
    void eligible_status(){
        String email = getAlphaNumericString(10) + "@test.com";
        String course = getAlphaNumericString(10);
        String price = getNumericString(3);
        String roomNr = getNumericString(3);

        //Create teacher
        apiCallPost("http://localhost:8080/2/api/teacher/register/TestName/" + email + "/Edu_2");
        //Create topic
        apiCallPost("http://localhost:8080/2/api/principal/register/addTopic/programming");
        //Create course
        apiCallPost("http://localhost:8080/2/api/principal/register/addCourse/" + course + "/programming/" + roomNr + "/" + email + "/" + price);

        driver.get("http://localhost:8080/2/teacher/panel.jsp");
        WebDriverWait wait = new WebDriverWait(driver, 2);

        WebElement emailField;
        emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("emailField")));
        WebElement button;
        button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("infobutton")));

        emailField.sendKeys(email);
        button.click();

        WebElement eligibleDiv;
        eligibleDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("eligibleDiv")));

        assertEquals("You are eligible to vote!", eligibleDiv.getText());
    }

    private String apiCallGet(String endpoint) {
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
