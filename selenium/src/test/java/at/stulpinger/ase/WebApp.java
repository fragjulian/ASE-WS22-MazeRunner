package at.stulpinger.ase;

import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebApp {

  public static final String RESOURCES_FOLDER = "src/test/resources/";

  public static WebElement findByTestId(final WebDriver driver, final String testId) {
    final var xpath = By.xpath("//*[@uitestid='%s']".formatted(testId));
    final var matchingElements = driver.findElements(xpath);
    if (matchingElements.isEmpty()) {
      fail("Could not find any matching HTML elements with attribute uitestid=%s"
          .formatted(testId));
    }
    return matchingElements.get(0);
  }

  public static WebElement findFirstByHtmlTag(final WebDriver driver, final String htmlTag) {
    final var matchingElements = driver.findElements(By.tagName(htmlTag));
    if (matchingElements.isEmpty()) {
      fail("Could not find any matching HTML elements with tag %s".formatted(htmlTag));
    }
    return matchingElements.get(0);
  }

  public static void assertIsClickable(final WebDriver driver, final WebElement webElement) {
    final var wait = new WebDriverWait(driver, Duration.of(1, ChronoUnit.SECONDS));
    try {
      wait.until(ExpectedConditions.elementToBeClickable(webElement));
    } catch (TimeoutException e) {
      fail("Could not click element with content %s.".formatted(webElement.getText()));
    }
  }

  static void navigate(final WebDriver driver, final Page page) {
    final var navigateToAboutPage = findByTestId(driver, page.getTestId());
    navigateToAboutPage.click();
  }

  static List<WebElement> findAllByTagName(final WebDriver driver, final String tagName) {
    return driver.findElements(By.ByTagName.tagName(tagName));
  }

  static void clickButton(final WebDriver driver, final String testId) {
    findByTestId(driver, testId).click();
  }

  static void pickColor(final WebDriver driver, final Color color) {
    final var saveColor = findByTestId(driver, color.getTestId());
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].click();", saveColor);
  }
}
