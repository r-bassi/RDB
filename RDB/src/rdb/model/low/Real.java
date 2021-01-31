package rdb.model.low;

public class Real extends Type {
  public static final Real DEFAULT = new Real();
  private static final int ID = 3;
  private static final String NAME = "REAL";

  public Real() {
    super(ID, NAME);
  }
}
