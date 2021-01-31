package rdb.model.low;

import static rdb.shared.Constants.CHAR_LEN;

public class Char extends Type {
  public static final Char DEFAULT = new Char(CHAR_LEN);
  private static final int ID = 2;
  private static final String NAME = "VARCHAR";

  public Char(int length) {
    super(ID, NAME + "(" + length + ")");
  }
}
