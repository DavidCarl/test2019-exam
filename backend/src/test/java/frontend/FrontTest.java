package frontend;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.*;

public class FrontTest {
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
    void main_index(){
        driver.get("http://localhost:8080/2/");
        WebDriverWait wait = new WebDriverWait(driver, 2);
        WebElement href;
        WebElement principal;
        href = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("teacherHref")));
        principal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("principalHref")));

        assertEquals("here!", href.getText());
        assertEquals("here!", href.getText());
        assertEquals("Lyngby Evening School", driver.getTitle());
    }
}
