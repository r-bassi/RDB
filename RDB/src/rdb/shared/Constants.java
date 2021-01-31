package rdb.shared;

import java.io.InputStream;
import java.io.PrintStream;

public class Constants {
  public static final String APPLICATION_NAME = "RDB";
  public static final int CHAR_LEN = 64;
  public static final int STR_LEN = 255;
  public static final String DEL_CASCADE = "ON DELETE CASCADE";
  public static final String DEL_SET_NULL = "ON DELETE SET NULL";
  public static final int NUL_INT = Integer.MIN_VALUE;
  public static final double NUL_DOUBLE = Integer.MIN_VALUE;
  public static final String NUL_STRING = null;
  public static final String SPACE = " ";
  public static final InputStream STDINP = System.in;
  public static final PrintStream STDOUT = System.out;
  public static final PrintStream STDERR = System.err;
}
