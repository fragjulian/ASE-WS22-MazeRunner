package at.stulpinger.ase;

public enum Color {

  GREEN("set-green"),
  RED("set-red")
  ;

  private final String testId;

  Color(final String testId) {
    this.testId = testId;
  }

  public String getTestId() {
    return testId;
  }
}
