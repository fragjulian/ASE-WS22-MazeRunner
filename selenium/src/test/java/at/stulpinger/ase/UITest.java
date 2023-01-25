package at.stulpinger.ase;

import static org.assertj.core.api.Assertions.assertThat;

import at.stulpinger.ase.Canvas.Pixel;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.IOException;
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
class UITest {
  private static final String HOME_PATH = "home";
  private static final String ABOUT_PATH = "about";
  private final String PORT = System.getProperty("PORT");
  private final String URL = System.getProperty("URL");
  private WebDriver driver;

  @BeforeEach
  public void setUp() {
    // headless browser like in CI-CD pipeline
    WebDriverManager.firefoxdriver().setup();
    FirefoxOptions options = new FirefoxOptions();
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    options.addArguments("--headless");
    options.addArguments("--width=1920");
    options.addArguments("--height=1080");
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
  void headerBar_hasCorrectTitle() {
    openApplication();
    final var headerBarTitle = WebApp.findByTestId(driver, "app-title").getText();
    assertThat(headerBarTitle).isEqualTo("Maze Runner");
  }

  @Test
  void footer_hasIconAttribution() {
    openApplication();
    final var footerCopyrightSection = WebApp.findByTestId(driver, "footer-copyright").getText();
    assertThat(footerCopyrightSection).contains("Maze icon created by Freepik");
  }

  @Test
  void footer_linksToIconSource() {
    openApplication();
    final var footerIconSourceLink = WebApp.findByTestId(driver, "icon-source");
    assertThat(footerIconSourceLink.isDisplayed()).isTrue();
    assertThat(footerIconSourceLink.isEnabled()).isTrue();
    assertThat(footerIconSourceLink.getText()).isEqualTo("Freepik");
    assertThat(footerIconSourceLink.getAttribute("href")).isNotBlank();
  }

  @Test
  void footer_hasAuthorInformation() {
    openApplication();
    final var footerAuthorSection = WebApp.findByTestId(driver, "footer-authors").getText();
    assertThat(footerAuthorSection).contains("University of Klagenfurt");
    assertThat(footerAuthorSection).contains("Informatics");
    assertThat(footerAuthorSection).contains("ASE");
  }

  @Test
  void footer_linksToUniversityHomepage() {
    openApplication();
    final var footerUniversityLink = WebApp.findByTestId(driver, "uni-homepage");
    assertThat(footerUniversityLink.isDisplayed()).isTrue();
    assertThat(footerUniversityLink.isEnabled()).isTrue();
    assertThat(footerUniversityLink.getText()).isEqualTo("University of Klagenfurt");
    assertThat(footerUniversityLink.getAttribute("href")).contains("www.aau.at");
  }

  @Test
  void solveButton_isDisabled() {
    openApplication();
    final var solveMazeButton = WebApp.findByTestId(driver, "solve-maze");
    assertThat(solveMazeButton.isDisplayed()).isTrue();
    assertThat(solveMazeButton.isEnabled()).isFalse();
    assertThat(solveMazeButton.getAttribute("value")).isEqualTo("Solve Maze");

  }

  @Test
  void navigate_toAboutAndBack() {
    openApplication();
    WebApp.navigate(driver, Page.ABOUT);
    final var aboutHeaderText = WebApp.findFirstByHtmlTag(driver, "h1").getText();
    assertThat(aboutHeaderText).isEqualTo("About Maze Runner");
    final var aboutText = WebApp.findFirstByHtmlTag(driver, "p").getText();
    assertThat(aboutText).isNotBlank();
    WebApp.navigate(driver, Page.HOME);
  }

  @Test
  void reload_about() {
    openApplicationWithPath(ABOUT_PATH);
    final var aboutHeaderTextBefore = WebApp.findFirstByHtmlTag(driver, "h1").getText();
    assertThat(aboutHeaderTextBefore).isEqualTo("About Maze Runner");
    driver.navigate().refresh();
    final var aboutHeaderTextAfter = WebApp.findFirstByHtmlTag(driver, "h1").getText();
    assertThat(aboutHeaderTextAfter).isEqualTo("About Maze Runner");
  }

  @Test
  void reload_home() {
    openApplicationWithPath(HOME_PATH);
    WebApp.findByTestId(driver, "app-title");
    driver.navigate().refresh();
    WebApp.findByTestId(driver, "app-title");
  }

  @Test
  void navigate_toHomeAndReload() {
    openApplication();
    WebApp.navigate(driver, Page.HOME);
    WebApp.findByTestId(driver, "app-title");
    driver.navigate().refresh();
    WebApp.findByTestId(driver, "app-title");
  }

  @Test
  void aboutPage_hasContent() {
    openApplication();
    WebApp.navigate(driver, Page.ABOUT);

    final var header = WebApp.findByTestId(driver, "about-header");
    assertThat(header.getText()).isEqualTo("About Maze Runner");

    final var paragraph = WebApp.findByTestId(driver, "about-paragraph");
    assertThat(paragraph.getText()).isNotBlank();

    final var iconSourceLinks = WebApp.findAllByTagName(driver, "a");
    assertThat(iconSourceLinks).hasSizeGreaterThanOrEqualTo(5);
  }

  @Test
  void builder_hasCorrectHeader() {
    openApplication();
    WebApp.navigate(driver, Page.BUILDER);

    final var header = WebApp.findByTestId(driver, "builder-header");
    assertThat(header.getText()).isEqualTo("Draw your maze here");
  }

  @Test
  void builder_drawBorder() throws IOException {
    openApplication();
    WebApp.navigate(driver, Page.BUILDER);

    final var canvas = WebApp.findByTestId(driver, "maze-canvas");
    Canvas.verify(driver, canvas, "empty-maze.png");

    WebApp.clickButton(driver, "draw-border");
    Canvas.verify(driver, canvas, "black-border.png");

    WebApp.clickButton(driver, "clear-maze");
    Canvas.verify(driver, canvas, "empty-maze.png");
  }

  @Test
  void builder_pickColor_drawBorder() throws IOException {
    openApplication();
    WebApp.navigate(driver, Page.BUILDER);

    final var canvas = WebApp.findByTestId(driver, "maze-canvas");
    Canvas.verify(driver, canvas, "empty-maze.png");

    WebApp.pickColor(driver, Color.GREEN);
    WebApp.clickButton(driver, "draw-border");
    Canvas.verify(driver, canvas, "green-border.png");

    WebApp.pickColor(driver, Color.RED);
    WebApp.clickButton(driver, "draw-border");
    Canvas.verify(driver, canvas, "red-border.png");

    WebApp.clickButton(driver, "clear-maze");
    Canvas.verify(driver, canvas, "empty-maze.png");
  }

  @Test
  void builder_drawSinglePixels() throws IOException {
    openApplication();
    WebApp.navigate(driver, Page.BUILDER);

    final var canvas = WebApp.findByTestId(driver, "maze-canvas");
    Canvas.drawSinglePixels(
        driver, canvas,
        Canvas.CENTER_PIXEL,
        Pixel.of(3, 0),
        Pixel.of(-3, 0)
    );
    Canvas.verify(driver, canvas, "three-pixels.png");

    WebApp.clickButton(driver, "clear-maze");
    Canvas.verify(driver, canvas, "empty-maze.png");
  }

  @Test
  void builder_drawLines() throws IOException, InterruptedException {
    openApplication();
    WebApp.navigate(driver, Page.BUILDER);
    final var canvas = WebApp.findByTestId(driver, "maze-canvas");

    Canvas.drawPixels(driver, canvas,
        new Line(
            Pixel.of(-10, 0),
            Line.Direction.RIGHT,
            19
        )
    );

    Canvas.drawPixels(driver, canvas,
        new Line(
            Pixel.of(0, -10),
            Line.Direction.BOTTOM,
            19
        )
    );

    WebApp.pickColor(driver, Color.GREEN);
    Canvas.drawPixels(driver, canvas,
        new Line(
            Pixel.of(-10, -10),
            Line.Direction.RIGHT_BOTTOM,
            19
        )
    );

    WebApp.pickColor(driver, Color.RED);
    Canvas.drawPixels(driver, canvas,
        new Line(
            Pixel.of(10, -10),
            Line.Direction.LEFT_BOTTOM,
            19
        )
    );

    Canvas.verify(driver, canvas, "star-shape.png");

    WebApp.clickButton(driver, "clear-maze");
    Canvas.verify(driver, canvas, "empty-maze.png");
  }

  @Test
  void builder_solveWithoutWalls() throws IOException, InterruptedException {
    openApplication();
    WebApp.navigate(driver, Page.BUILDER);
    final var canvas = WebApp.findByTestId(driver, "maze-canvas");

    WebApp.clickButton(driver, "set-start");
    Canvas.drawSinglePixels(driver, canvas, Pixel.of(-10, -15));

    WebApp.clickButton(driver, "set-goal");
    Canvas.drawSinglePixels(driver, canvas, Pixel.of(10, 15));

    Thread.sleep(1_000); // give server time to respond
    Canvas.verify(driver, canvas, "solved-maze-no-walls.png");

    WebApp.clickButton(driver, "clear-maze");
    Canvas.verify(driver, canvas, "empty-maze.png");
  }

  @Test
  void builder_simpleWalls() throws IOException, InterruptedException {
    openApplication();
    WebApp.navigate(driver, Page.BUILDER);
    final var canvas = WebApp.findByTestId(driver, "maze-canvas");

    WebApp.pickColor(driver, Color.GREEN);
    WebApp.clickButton(driver, "draw-border");

    Canvas.drawPixels(driver, canvas,
        new Line(
            Pixel.of(-10, 10),
            Line.Direction.UP,
            25
        )
    );

    Canvas.drawPixels(driver, canvas,
        new Line(
            Pixel.of(10, -12),
            Line.Direction.BOTTOM,
            20
        )
    );

    WebApp.clickButton(driver, "set-start");
    Canvas.drawSinglePixels(driver, canvas, Pixel.of(-15, -15));

    WebApp.clickButton(driver, "set-goal");
    Canvas.drawSinglePixels(driver, canvas, Pixel.of(15, 5));

    Thread.sleep(1_000); // give server time to respond
    Canvas.verify(driver, canvas, "solved-maze-simple-walls.png");

    WebApp.clickButton(driver, "clear-maze");
    Canvas.verify(driver, canvas, "empty-maze.png");
  }

  @Test
  void builder_advancedMaze() throws InterruptedException, IOException {
    openApplication();
    WebApp.navigate(driver, Page.BUILDER);
    final var canvas = WebApp.findByTestId(driver, "maze-canvas");

    WebApp.pickColor(driver, Color.GREEN);
    WebApp.clickButton(driver, "draw-border");

    Canvas.drawPixels(driver, canvas,
        new Line(
            Pixel.of(-37, -10),
            Line.Direction.RIGHT,
            25
        )
    );

    Canvas.drawPixels(driver, canvas,
        new Line(
            Pixel.of(-5, -20),
            Line.Direction.BOTTOM,
            20
        )
    );

    Canvas.drawPixels(driver, canvas,
        new Line(
            Pixel.of(10, 20),
            Line.Direction.UP,
            30
        )
    );

    Canvas.drawPixels(driver, canvas,
        new Line(
            Pixel.of(0, 0),
            Line.Direction.RIGHT,
            19
        )
    );

    Canvas.drawPixels(driver, canvas,
        new Line(
            Pixel.of(-15, 8),
            Line.Direction.RIGHT,
            17
        )
    );

    WebApp.clickButton(driver, "set-start");
    Canvas.drawSinglePixels(driver, canvas, Pixel.of(-35, -15));

    WebApp.clickButton(driver, "set-goal");
    Canvas.drawSinglePixels(driver, canvas, Pixel.of(35, 16));

    Thread.sleep(1_000); // give server time to respond
    Canvas.verify(driver, canvas, "solved-maze-advanced.png");

    WebApp.clickButton(driver, "clear-maze");
    Canvas.verify(driver, canvas, "empty-maze.png");
  }

}
