package at.stulpinger.ase;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Contains all the UI tests for the project, which are run using selenium
 * By default tests are run on http://localhost:8080 (mvn clean test)
 * To do a custom test:         "mvn -DURL=<URL>: -DPORT=<PORT> clean test"
 * To test the deployed server: "mvn -DURL=https://ase.stulpinger.at -DPORT= clean test"
 */
public class UITest {
  private final String HOME_PATH = "home";
  private final String ABOUT_PATH = "about";
  private final String PORT = System.getProperty("PORT");
  private final String URL = System.getProperty("URL");
  private WebDriver driver;

  @BeforeEach
  public void setUp() {
    // for manual testing with installed browser
//    WebDriverManager.chromedriver().setup();
//    driver = new ChromeDriver();

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

  private void openApplication() {
    final var homeUrl = URL + PORT + "/";
    driver.get(homeUrl);
  }

  private void openApplicationWithPath(final String path) {
    final var homeUrl = this.URL + PORT + "/" + path + "/";
    driver.get(homeUrl);
  }

  @Test
  public void headerBar_hasCorrectTitle() {
    openApplication();
    final var headerBarTitle = WebApp.findByTestId(driver, "app-title").getText();
    assertThat(headerBarTitle).isEqualTo("Maze Runner");
  }

  @Test
  public void footer_hasIconAttribution() {
    openApplication();
    final var footerCopyrightSection = WebApp.findByTestId(driver, "footer-copyright").getText();
    assertThat(footerCopyrightSection).contains("Maze icon created by Freepik");
  }

  @Test
  public void footer_linksToIconSource() {
    openApplication();
    final var footerIconSourceLink = WebApp.findByTestId(driver, "icon-source");
    assertThat(footerIconSourceLink.isDisplayed()).isTrue();
    assertThat(footerIconSourceLink.isEnabled()).isTrue();
    assertThat(footerIconSourceLink.getText()).isEqualTo("Freepik");
    assertThat(footerIconSourceLink.getAttribute("href")).isNotBlank();
  }

  @Test
  public void footer_hasAuthorInformation() {
    openApplication();
    final var footerAuthorSection = WebApp.findByTestId(driver, "footer-authors").getText();
    assertThat(footerAuthorSection).contains("University of Klagenfurt");
    assertThat(footerAuthorSection).contains("Informatics");
    assertThat(footerAuthorSection).contains("ASE");
  }

  @Test
  public void footer_linksToUniversityHomepage() {
    openApplication();
    final var footerUniversityLink = WebApp.findByTestId(driver, "uni-homepage");
    assertThat(footerUniversityLink.isDisplayed()).isTrue();
    assertThat(footerUniversityLink.isEnabled()).isTrue();
    assertThat(footerUniversityLink.getText()).isEqualTo("University of Klagenfurt");
    assertThat(footerUniversityLink.getAttribute("href")).contains("www.aau.at");
  }

  @Test
  public void solveButton_isDisabled() {
    openApplication();
    final var solveMazeButton = WebApp.findByTestId(driver, "solve-maze");
    assertThat(solveMazeButton.isDisplayed()).isTrue();
    assertThat(solveMazeButton.isEnabled()).isFalse();
    assertThat(solveMazeButton.getAttribute("value")).isEqualTo("Solve Maze");

  }

  @Test
  public void navigate_toAboutAndBack() {
    openApplication();
    final var navigateToAboutPage = WebApp.findByTestId(driver, "nav-about");
    navigateToAboutPage.click();
    final var aboutHeaderText = WebApp.findFirstByHtmlTag(driver, "h1").getText();
    assertThat(aboutHeaderText).isEqualTo("About Maze Runner");
    final var aboutText = WebApp.findFirstByHtmlTag(driver, "p").getText();
    assertThat(aboutText).isNotBlank();
    final var navigateToHomePage = WebApp.findByTestId(driver, "nav-home");
    navigateToHomePage.click();
  }

  @Test
  public void reload_about() {
    openApplicationWithPath(ABOUT_PATH);
    final var aboutHeaderTextBefore = WebApp.findFirstByHtmlTag(driver, "h1").getText();
    assertThat(aboutHeaderTextBefore).isEqualTo("About Maze Runner");
    driver.navigate().refresh();
    final var aboutHeaderTextAfter = WebApp.findFirstByHtmlTag(driver, "h1").getText();
    assertThat(aboutHeaderTextAfter).isEqualTo("About Maze Runner");
  }

  @Test
  public void reload_home() {
    openApplicationWithPath(HOME_PATH);
    WebApp.findByTestId(driver, "app-title");
    driver.navigate().refresh();
    WebApp.findByTestId(driver, "app-title");
  }

  @Test
  public void navigate_toHomeAndReload() {
    openApplication();
    final var navigateToHome = WebApp.findByTestId(driver, "nav-home");
    navigateToHome.click();
    WebApp.findByTestId(driver, "app-title");
    driver.navigate().refresh();
    WebApp.findByTestId(driver, "app-title");
  }

}
