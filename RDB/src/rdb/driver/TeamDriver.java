package rdb.driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import rdb.model.Table;
import rdb.model.Team;
import rdb.shared.MockKit;
import rdb.util.Statements;

public class TeamDriver extends TableDriver {
  public static final TeamDriver NUL = new TeamDriver();
  private static final Table TABLE = Team.NUL;
  private static final Team[] MOCKS = MockKit.TEAMS;

  public Team get(Team model, Connection connection) throws SQLException {
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

  public Team[] list(Connection connection) throws SQLException {
    List<Team> out = new LinkedList<>();
    try (Statement statement = connection.createStatement()) {
      ResultSet results = statement.executeQuery(Statements.makeSelect(TABLE));
      while (results.next()) {
        Team model = new Team();
        copyTuple(results, model, 1);
        out.add(model);
      }
    }
    return out.toArray(new Team[out.size()]);
  }

  public int update(Team model, Connection connection) throws SQLException {
    try (PreparedStatement statement
        = connection.prepareStatement(Statements.makeUpdate(TABLE))) {
      copyAllAndKey(model, statement, 1);
      return statement.executeUpdate();
    }
  }

  public int insert(Team model, Connection connection) throws SQLException {
    try (PreparedStatement statement
        = connection.prepareStatement(Statements.makeInsertInto(TABLE))) {
      copyKeyAndAll(model, statement, 1);
      return statement.executeUpdate();
    }
  }

  public int delete(Team model, Connection connection) throws SQLException {
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
      for (Team model : MOCKS) {
        copyKeyAndAll(model, statement, 1);
        statement.executeUpdate();
      }
    }
  }

  private int copyKeyAndAll(Team model, PreparedStatement statement, int index)
      throws SQLException {
    return copyAll(model, statement, copyKey(model, statement, index) + 1);
  }

  private int copyAllAndKey(Team model, PreparedStatement statement, int index)
      throws SQLException {
    return copyKey(model, statement, copyAll(model, statement, index) + 1);
  }

  private int copyKey(Team model, PreparedStatement statement, int index)
      throws SQLException {
    copyString(model.getCity(), statement, index);
    copyString(model.getName(), statement, ++index);
    return index;
  }

  private int copyAll(Team model, PreparedStatement statement, int index)
      throws SQLException {
    copyInt(model.getHomeCapacity(), statement, index);
    copyDouble(model.getPayroll(), statement, ++index);
    copyDouble(model.getMerchandise(), statement, ++index);
    copyDouble(model.getTicketPrice(), statement, ++index);
    copyString(model.getDivision(), statement, ++index);
    copyString(model.getGmId(), statement, ++index);
    return index;
  }

  private int copyTuple(ResultSet results, Team model, int index)
      throws SQLException {
    model.setCity(getString(results, index));
    model.setName(getString(results, ++index));
    model.setHomeCapacity(getInt(results, ++index));
    model.setPayroll(getDouble(results, ++index));
    model.setMerchandise(getDouble(results, ++index));
    model.setTicketPrice(getDouble(results, ++index));
    model.setDivision(getString(results, ++index));
    model.setGmId(getString(results, ++index));
    return index;
  }
}
