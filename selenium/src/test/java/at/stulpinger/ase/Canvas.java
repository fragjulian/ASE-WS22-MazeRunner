package at.stulpinger.ase;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class Canvas {

  private static final int STEP = 15;

  public static final Pixel CENTER_PIXEL = Pixel.of(0, 0);

  public static class Pixel {

    private final int x, y;

    private Pixel(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public static Pixel of(int x, int y) {
      return new Pixel(x, y);
    }

  }

  /**
   * Draws single pixels by clicking relative to the center of the canvas.
   */
  static void drawSinglePixels(final WebDriver driver, final WebElement canvas,
                               final Pixel... pixels) {

    final var actions = new Actions(driver);
    for (final Pixel pixel : pixels) {
      actions.moveToElement(canvas, pixel.x * STEP, pixel.y * STEP).click().perform();
    }
  }

  /**
   * Draws multiple pixels by click + hold from start to end pixel.
   */
  static void drawPixels(final WebDriver driver, final WebElement canvas,
                         final Line line) throws InterruptedException {

    final var actions = new Actions(driver);
    actions.moveToElement(canvas, line.start().x * STEP, line.start().y * STEP)
        .clickAndHold().perform();

    final Pixel offsetPixel;
    switch (line.direction()) {
      case LEFT -> offsetPixel = Pixel.of(-1, 0);
      case UP -> offsetPixel = Pixel.of(0, -1);
      case RIGHT -> offsetPixel = Pixel.of(1, 0);
      case BOTTOM -> offsetPixel = Pixel.of(0, 1);

      case LEFT_UP -> offsetPixel = Pixel.of(-1, -1);
      case RIGHT_UP -> offsetPixel = Pixel.of(1, -1);
      case LEFT_BOTTOM -> offsetPixel = Pixel.of(-1, 1);
      case RIGHT_BOTTOM -> offsetPixel = Pixel.of(1, 1);
      default -> throw new IllegalStateException(
          "direction %s not implemented".formatted(line.direction()));
    }

    for (int i = 0; i < line.steps(); i++) {
      actions.moveByOffset(offsetPixel.x * STEP, offsetPixel.y * STEP).perform();
      // wait one ms to slow down the cursor movement on the canvas
      Thread.sleep(1);
    }

    actions.release().perform();
  }

  static void verify(final WebDriver driver, final WebElement canvas,
                     final String expectedImagePath) throws IOException {

    final var expectedImage = FileUtil.encodeFileToBase64(
        WebApp.RESOURCES_FOLDER + expectedImagePath);

    final JavascriptExecutor js = (JavascriptExecutor) driver;
    final var drawnImage =
        (String) js.executeScript("return arguments[0].toDataURL('image/png').substring(22);",
            canvas);

    assertThat(drawnImage).isEqualTo(expectedImage);
  }


}
