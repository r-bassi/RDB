package rdb.driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import rdb.model.Coach;
import rdb.model.Table;
import rdb.shared.MockKit;
import rdb.util.Statements;

public class CoachDriver extends TableDriver {
  public static final CoachDriver NUL = new CoachDriver();
  private static final Table TABLE = Coach.NUL;
  private static final Coach[] MOCKS = MockKit.COACHES;

  public Coach get(Coach model, Connection connection) throws SQLException {
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

  public Coach[] list(Connection connection) throws SQLException {
    List<Coach> out = new LinkedList<>();
    try (Statement statement = connection.createStatement()) {
      ResultSet results = statement.executeQuery(Statements.makeSelect(TABLE));
      while (results.next()) {
        Coach model = new Coach();
        copyTuple(results, model, 1);
        out.add(model);
      }
    }
    return out.toArray(new Coach[out.size()]);
  }

  public int update(Coach model, Connection connection) throws SQLException {
    try (PreparedStatement statement
        = connection.prepareStatement(Statements.makeUpdate(TABLE))) {
      copyAllAndKey(model, statement, 1);
      return statement.executeUpdate();
    }
  }

  public int insert(Coach model, Connection connection) throws SQLException {
    try (PreparedStatement statement
        = connection.prepareStatement(Statements.makeInsertInto(TABLE))) {
      copyKeyAndAll(model, statement, 1);
      return statement.executeUpdate();
    }
  }

  public int delete(Coach model, Connection connection) throws SQLException {
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
      for (Coach model : MOCKS) {
        copyKeyAndAll(model, statement, 1);
        statement.executeUpdate();
      }
    }
  }

  private int copyKeyAndAll(Coach model, PreparedStatement statement, int index)
      throws SQLException {
    return copyAll(model, statement, copyKey(model, statement, index) + 1);
  }

  private int copyAllAndKey(Coach model, PreparedStatement statement, int index)
      throws SQLException {
    return copyKey(model, statement, copyAll(model, statement, index) + 1);
  }

  private int copyKey(Coach model, PreparedStatement statement, int index)
      throws SQLException {
    copyInt(model.getId(), statement, index);
    return index;
  }

  private int copyAll(Coach model, PreparedStatement statement, int index)
      throws SQLException {
    copyString(model.getName(), statement, index);
    copyInt(model.getRating(), statement, ++index);
    copyInt(model.getAgentId(), statement, ++index);
    return index;
  }

  private int copyTuple(ResultSet results, Coach model, int index)
      throws SQLException {
    model.setId(getInt(results, index));
    model.setName(getString(results, ++index));
    model.setRating(getInt(results, ++index));
    model.setAgentId(getInt(results, ++index));
    return index;
  }
}
