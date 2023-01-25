package at.stulpinger.ase;

import java.util.Objects;

public enum Page {

  HOME("nav-home"),
  BUILDER("nav-builder"),
  COLOR_PICKER("nav-colorpicker"),
  ABOUT("nav-about")
  ;

  private final String testId;

  Page(String testId) {
    this.testId = Objects.requireNonNull(testId, "testId");
  }

  public String getTestId() {
    return testId;
  }
}
