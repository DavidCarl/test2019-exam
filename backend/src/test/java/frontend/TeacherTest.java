package frontend;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.*;

public class TeacherTest {

    WebDriver driver;

    @BeforeEach
    void setup(){
        System.setProperty("webdriver.gecko.driver", "geckodriver");
        driver = new FirefoxDriver();
    }

    @AfterEach
    void teardown(){
        driver.quit();
    }

    @Test
    void teacher_index(){
        driver.get("http://localhost:8080/2/teacher");
        WebDriverWait wait = new WebDriverWait(driver, 2);
        WebElement href;
        href = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signupHref")));

        assertEquals("here!", href.getText());
        assertEquals("Teacher section", driver.getTitle());

    }

    @Test
    void teacher_signup(){
        driver.get("http://localhost:8080/2/teacher/signup.jsp");
        WebDriverWait wait = new WebDriverWait(driver, 2);
        WebElement email;
        email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("emailField")));
        WebElement name;
        name = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameField")));
        Select edu = new Select(driver.findElement(By.id("eduField")));
        WebElement button;
        button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signupbutton")));
        //edu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("eduField")));

        assertEquals("Teacher signup", driver.getTitle());

        email.sendKeys("test@testy.com");
        name.sendKeys("test");
        edu.selectByIndex(1);
        button.click();

        //We should check if the user is being created - Can be done with the following API call
        //http://localhost:8080/2/api/teacher/status/{email}

    }
}
