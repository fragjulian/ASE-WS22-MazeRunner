package at.stulpinger.ase;

import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebApp {

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
}
