package rdb.driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import rdb.model.Plays;
import rdb.model.Table;
import rdb.shared.MockKit;
import rdb.util.Statements;

public class PlaysDriver extends TableDriver {
  public static final PlaysDriver NUL = new PlaysDriver();
  private static final Table TABLE = Plays.NUL;
  private static final Plays[] MOCKS = MockKit.PLAYS;

  public Plays get(Plays model, Connection connection) throws SQLException {
    try (PreparedStatement statement
        = connection.prepareStatement(Statements.makeSelectWhere(TABLE))) {
      copyKey(model, statement, 1);
      ResultSet results = statement.executeQuery();
      if (results.next()) {
        copyTuple(results, model, 1);
        return model;
      }
      return null;
    }
  }

  public Plays[] list(Connection connection) throws SQLException {
    List<Plays> out = new LinkedList<>();
    try (Statement statement = connection.createStatement()) {
      ResultSet results = statement.executeQuery(Statements.makeSelect(TABLE));
      while (results.next()) {
        Plays model = new Plays();
        copyTuple(results, model, 1);
        out.add(model);
      }
    }
    return out.toArray(new Plays[out.size()]);
  }

  public int update(Plays model, Connection connection) throws SQLException {
    try (PreparedStatement statement
        = connection.prepareStatement(Statements.makeUpdate(TABLE))) {
      copyAllAndKey(model, statement, 1);
      return statement.executeUpdate();
    }
  }

  public int insert(Plays model, Connection connection) throws SQLException {
    try (PreparedStatement statement
        = connection.prepareStatement(Statements.makeInsertInto(TABLE))) {
      copyKeyAndAll(model, statement, 1);
      return statement.executeUpdate();
    }
  }

  public int delete(Plays model, Connection connection) throws SQLException {
    try (PreparedStatement statement
        = connection.prepareStatement(Statements.makeDeleteFrom(TABLE))) {
      copyKey(model, statement, 1);
      return statement.executeUpdate();
    }
  }

  @Override
  public void populate(Connection connection) throws SQLException {
    try (PreparedStatement statement
        = connection.prepareStatement(Statements.makeInsertInto(TABLE))) {
      for (Plays model : MOCKS) {
        copyKeyAndAll(model, statement, 1);
        statement.executeUpdate();
      }
    }
  }

  private int copyKeyAndAll(Plays model, PreparedStatement statement, int index)
      throws SQLException {
    return copyAll(model, statement, copyKey(model, statement, index) + 1);
  }

  private int copyAllAndKey(Plays model, PreparedStatement statement, int index)
      throws SQLException {
    return copyKey(model, statement, copyAll(model, statement, index) + 1);
  }

  private int copyKey(Plays model, PreparedStatement statement, int index)
      throws SQLException {
    copyString(model.getUserId(), statement, index);
    copyString(model.getTeamCity(), statement, ++index);
    copyString(model.getTeamName(), statement, ++index);
    return index;
  }

  private int copyAll(Plays model, PreparedStatement statement, int index)
      throws SQLException {
    return index;
  }

  private int copyTuple(ResultSet results, Plays model, int index)
      throws SQLException {
    model.setUserId(getString(results, index));
    model.setTeamCity(getString(results, ++index));
    model.setTeamName(getString(results, ++index));
    return index;
  }
}
