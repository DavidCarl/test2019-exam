package frontend;

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
}
