package rdb.util;

import java.util.Objects;
import rdb.exception.InputEmptyException;
import rdb.exception.InputFormatException;
import rdb.exception.InputOverflowException;
import rdb.model.Table;
import rdb.model.low.Column;
import static rdb.shared.Constants.CHAR_LEN;
import static rdb.shared.Constants.NUL_DOUBLE;
import static rdb.shared.Constants.NUL_INT;
import static rdb.shared.Constants.NUL_STRING;

public class Transcriber {
  public static String toField(int value) {
    return value == NUL_INT ? "" : Objects.toString(value);
  }

  public static String toField(double value) {
    return value == NUL_DOUBLE ? "" : Objects.toString(value);
  }

  public static String toField(String value) {
    return value == NUL_STRING ? "" : value;
  }

  public static int toInt(String string) {
    return toInt(string, true);
  }

  public static double toDouble(String string) {
    return toDouble(string, true);
  }

  public static String toString(String string) {
    return toString(string, true);
  }

  public static int parseInt(String string, boolean allowEmpty) {
    try {
      int out = toInt(string, false);
      if (out == NUL_INT && !allowEmpty) throw new InputEmptyException();
      return out;
    } catch (NumberFormatException exception) {
      throw new InputFormatException(exception);
    }
  }

  public static double parseDouble(String string, boolean allowEmpty) {
    try {
      double out = toDouble(string, false);
      if (out == NUL_DOUBLE && !allowEmpty) throw new InputEmptyException();
      return out;
    } catch (NumberFormatException exception) {
      throw new InputFormatException(exception);
    }
  }

  public static String parseString(String string, boolean allowEmpty) {
    String out = toString(string, false);
    if (out == NUL_STRING && !allowEmpty) throw new InputEmptyException();
    return string;
  }

  public static boolean isEmptyAllowed(Table table, Column column) {
    return !column.isNotNull() && !table.isPrimaryKey(column);
  }

  private static int toInt(String string, boolean ignoreErrors) {
    try {
      return string.isEmpty() ? NUL_INT : Integer.parseInt(string);
    } catch (NumberFormatException exception) {
      if (ignoreErrors) return NUL_INT;
      throw exception;
    }
  }

  private static double toDouble(String string, boolean ignoreErrors) {
    try {
      return string.isEmpty() ? NUL_DOUBLE : Double.parseDouble(string);
    } catch (NumberFormatException exception) {
      if (ignoreErrors) return NUL_DOUBLE;
      throw exception;
    }
  }

  private static String toString(String string, boolean ignoreErrors) {
    if (string.isEmpty()) return NUL_STRING;
    if (string.length() > CHAR_LEN) {
      if (ignoreErrors) return string.substring(0, CHAR_LEN);
      throw new InputOverflowException();
    }
    return string;
  }
}
