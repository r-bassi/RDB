package rdb.model.low;

public class Integer extends Type {
  public static final Integer DEFAULT = new Integer();
  private static final int ID = 1;
  private static final String NAME = "INTEGER";

  public Integer() {
    super(ID, NAME);
  }
}
