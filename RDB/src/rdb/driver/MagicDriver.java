package rdb.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class MagicDriver {
  private static final String PROJECTION
      = "SELECT name, age, height, weight FROM Player";
  private static final String SELECTION
      = PROJECTION + " WHERE name LIKE ";
  private static final String JOIN
      = "SELECT p.name, p.age, y.teamCity, y.teamName FROM Player p INNER JOIN Plays y ON p.id = y.userId WHERE p.name LIKE ";
  private static final String GROUP
      = "SELECT c.name, c.rating, COUNT(*) FROM Coach c INNER JOIN Player p ON c.id = p.coachId GROUP BY c.name, c.rating";
  private static final String HAVING
      = "SELECT y.teamCity, y.teamName, MIN(p.age) FROM Plays y, Player p, Team t WHERE y.userId = p.id AND y.teamCity = t.city AND y.teamName = t.name AND t.homeCapacity >= 20000 GROUP BY y.teamCity, y.teamName HAVING COUNT(*) >= 2";
  private static final String NESTED
      = "SELECT l.division, AVG(t.payroll) FROM League l, Team t GROUP BY l.division HAVING 2 <= (SELECT COUNT(*) FROM Team t2 WHERE t2.division = l.division AND t2.homeCapacity >= 20000)";
  private static final String DIVISION
      = "SELECT p.name, p.age, p.height, p.weight FROM Player p WHERE NOT EXISTS ((SELECT t.name FROM Team t) MINUS (SELECT y.teamName FROM Plays y WHERE y.userID = p.id))";

  public static String[] doSelect(String term, Connection connection)
      throws SQLException {
    List<String> out = new LinkedList<>();
    try (Statement statement = connection.createStatement()) {
      ResultSet results = statement.executeQuery(SELECTION + "'%" + term + "%'");
      while (results.next()) out.add(stringifyPlayer(results));
      return out.toArray(new String[out.size()]);
    }
  }

  public static String[] doProject(Connection connection) throws SQLException {
    List<String> out = new LinkedList<>();
    try (Statement statement = connection.createStatement()) {
      ResultSet results = statement.executeQuery(PROJECTION);
      while (results.next()) out.add(stringifyPlayer(results));
      return out.toArray(new String[out.size()]);
    }
  }

  public static String[] doJoin(String term, Connection connection)
      throws SQLException {
    List<String> out = new LinkedList<>();
    try (Statement statement = connection.createStatement()) {
      ResultSet results = statement.executeQuery(JOIN + "'%" + term + "%'");
      while (results.next()) out.add(stringifyPlays(results));
      return out.toArray(new String[out.size()]);
    }
  }

  public static String[] doGroup(Connection connection)
      throws SQLException {
    List<String> out = new LinkedList<>();
    try (Statement statement = connection.createStatement()) {
      ResultSet results = statement.executeQuery(GROUP);
      while (results.next()) out.add(stringifyCoach(results));
      return out.toArray(new String[out.size()]);
    }
  }

  public static String[] doHaving(Connection connection)
      throws SQLException {
    List<String> out = new LinkedList<>();
    try (Statement statement = connection.createStatement()) {
      ResultSet results = statement.executeQuery(HAVING);
      while (results.next()) out.add(stringifyTeam(results));
      return out.toArray(new String[out.size()]);
    }
  }

  public static String[] doNested(Connection connection)
      throws SQLException {
    List<String> out = new LinkedList<>();
    try (Statement statement = connection.createStatement()) {
      ResultSet results = statement.executeQuery(NESTED);
      while (results.next()) out.add(stringifyLeague(results));
      return out.toArray(new String[out.size()]);
    }
  }

  public static String[] doDivide(Connection connection) throws SQLException {
    List<String> out = new LinkedList<>();
    try (Statement statement = connection.createStatement()) {
      ResultSet results = statement.executeQuery(DIVISION);
      while (results.next()) out.add(stringifyPlayer(results));
      return out.toArray(new String[out.size()]);
    }
  }

  private static String stringifyPlayer(ResultSet results) throws SQLException {
    return results.getString(1) + ", age "
        + results.getInt(2) + ", "
        + results.getDouble(3) + " m, "
        + results.getDouble(4) + " kg";
  }

  private static String stringifyPlays(ResultSet results) throws SQLException {
    return results.getString(1) + ", age "
        + results.getInt(2) + ", for "
        + results.getString(3) + " "
        + results.getString(4);
  }

  private static String stringifyCoach(ResultSet results) throws SQLException {
    return results.getString(1) + " ("
        + results.getInt(2) + "): "
        + results.getInt(3);
  }

  private static String stringifyTeam(ResultSet results) throws SQLException {
    return results.getString(1) + " "
        + results.getString(2) + ": "
        + results.getInt(3);
  }

  private static String stringifyLeague(ResultSet results) throws SQLException {
    return results.getString(1) + ": $"
        + results.getDouble(2) + " mil.";
  }
}
