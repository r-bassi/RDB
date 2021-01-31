package rdb.driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import rdb.model.League;
import rdb.model.Table;
import rdb.shared.MockKit;
import rdb.util.Statements;

public class LeagueDriver extends TableDriver {
  public static final LeagueDriver NUL = new LeagueDriver();
  private static final Table TABLE = League.NUL;
  private static final League[] MOCKS = MockKit.LEAGUES;

  public League get(League model, Connection connection) throws SQLException {
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

  public League[] list(Connection connection) throws SQLException {
    List<League> out = new LinkedList<>();
    try (Statement statement = connection.createStatement()) {
      ResultSet results = statement.executeQuery(Statements.makeSelect(TABLE));
      while (results.next()) {
        League model = new League();
        copyTuple(results, model, 1);
        out.add(model);
      }
    }
    return out.toArray(new League[out.size()]);
  }

  public int update(League model, Connection connection) throws SQLException {
    try (PreparedStatement statement
        = connection.prepareStatement(Statements.makeUpdate(TABLE))) {
      copyAllAndKey(model, statement, 1);
      return statement.executeUpdate();
    }
  }

  public int insert(League model, Connection connection) throws SQLException {
    try (PreparedStatement statement
        = connection.prepareStatement(Statements.makeInsertInto(TABLE))) {
      copyKeyAndAll(model, statement, 1);
      return statement.executeUpdate();
    }
  }

  public int delete(League model, Connection connection) throws SQLException {
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
      for (League model : MOCKS) {
        copyKeyAndAll(model, statement, 1);
        statement.executeUpdate();
      }
    }
  }

  private int copyKeyAndAll(League model, PreparedStatement statement, int index)
      throws SQLException {
    return copyAll(model, statement, copyKey(model, statement, index) + 1);
  }

  private int copyAllAndKey(League model, PreparedStatement statement, int index)
      throws SQLException {
    return copyKey(model, statement, copyAll(model, statement, index) + 1);
  }

  private int copyKey(League model, PreparedStatement statement, int index)
      throws SQLException {
    copyString(model.getDivision(), statement, index);
    return index;
  }

  private int copyAll(League model, PreparedStatement statement, int index)
      throws SQLException {
    copyDouble(model.getTvDeal(), statement, index);
    copyDouble(model.getSponsorship(), statement, ++index);
    copyDouble(model.getSalaryCap(), statement, ++index);
    copyString(model.getCommissioner(), statement, ++index);
    return index;
  }

  private int copyTuple(ResultSet results, League model, int index)
      throws SQLException {
    model.setDivision(getString(results, index));
    model.setTvDeal(getDouble(results, ++index));
    model.setSponsorship(getDouble(results, ++index));
    model.setSalaryCap(getDouble(results, ++index));
    model.setCommissioner(getString(results, ++index));
    return index;
  }
}
