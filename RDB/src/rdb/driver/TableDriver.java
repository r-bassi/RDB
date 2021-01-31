package rdb.driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.sql.Types.CHAR;
import static java.sql.Types.INTEGER;
import static java.sql.Types.REAL;
import static rdb.shared.Constants.NUL_DOUBLE;
import static rdb.shared.Constants.NUL_INT;
import static rdb.shared.Constants.NUL_STRING;

public abstract class TableDriver {

  protected static void copyInt(int i, PreparedStatement statement, int index)
      throws SQLException {
    if (i == NUL_INT) statement.setNull(index, INTEGER);
    else statement.setInt(index, i);
  }

  protected static void copyDouble(double d, PreparedStatement statement,
      int index) throws SQLException {
    if (d == NUL_DOUBLE) statement.setNull(index, REAL);
    else statement.setDouble(index, d);
  }

  protected static void copyString(String s, PreparedStatement statement,
      int index) throws SQLException {
    if (s == NUL_STRING) statement.setNull(index, CHAR);
    else statement.setString(index, s.trim());
  }

  protected static int getInt(ResultSet results, int index)
      throws SQLException {
    int i;
    i = results.getInt(index);
    return results.wasNull() ? NUL_INT : i;
  }

  protected static double getDouble(ResultSet results, int index)
      throws SQLException {
    double d;
    d = results.getDouble(index);
    return results.wasNull() ? NUL_DOUBLE : d;
  }

  protected static String getString(ResultSet results, int index)
      throws SQLException {
    String s;
    s = results.getString(index);
    return results.wasNull() ? NUL_STRING : s.trim();
  }

  public abstract void populate(Connection connection) throws SQLException;
}
