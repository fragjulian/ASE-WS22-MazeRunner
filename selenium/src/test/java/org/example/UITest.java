package org.example;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Contains all the UI tests for the project, which are run using selenium
 * By default tests are run on http://localhost:8080 (mvn clean test)
 * To do a custom test:         "mvn -DURL=<URL>: -DPORT=<PORT> clean test"
 * To test the deployed server: "mvn -DURL=https://ase.stulpinger.at -DPORT= clean test"
 *
 */
public class UITest {
  //@LocalServerPort
  private final String port = System.getProperty("PORT");
  private final String url = System.getProperty("URL");
  private WebDriver driver;

  @BeforeEach
  public void setUp() {
    // for manual testing with installed browser
    //System.setProperty("webdriver.gecko.driver", "C:\\Users\\willi\\OneDrive\\UNI\\UNI WS 2022_23\\Advanced Software Engineering\\test_project\\geckodriver.exe");
    //driver = new FirefoxDriver();

    // headless browser like in CI-CD pipeline
    WebDriverManager.firefoxdriver().setup();
    FirefoxOptions options = new FirefoxOptions();
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    options.addArguments("--headless");
    driver = new FirefoxDriver(options);
  }
  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void test1() {
    System.out.println(url);
    System.out.println(port);
    driver.get( url + port + "/");
    WebElement btn = driver.findElement(By.className("solve-maze-btn"));
    String btnText = btn.findElement(By.className("sr-only")).getText();
    // Test after click
    assertEquals("Solve Maze", btnText);
  }
  @Test
  public void test2() {
    assertTrue(true, "Success");
  }
}
