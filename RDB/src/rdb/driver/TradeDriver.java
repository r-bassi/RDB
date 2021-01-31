package rdb.driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import rdb.model.Table;
import rdb.model.Trade;
import rdb.shared.MockKit;
import rdb.util.Statements;

public class TradeDriver extends TableDriver {
  public static final TradeDriver NUL = new TradeDriver();
  private static final Table TABLE = Trade.NUL;
  private static final Trade[] MOCKS = MockKit.TRADES;

  public Trade get(Trade model, Connection connection) throws SQLException {
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

  public Trade[] list(Connection connection) throws SQLException {
    List<Trade> out = new LinkedList<>();
    try (Statement statement = connection.createStatement()) {
      ResultSet results = statement.executeQuery(Statements.makeSelect(TABLE));
      while (results.next()) {
        Trade model = new Trade();
        copyTuple(results, model, 1);
        out.add(model);
      }
    }
    return out.toArray(new Trade[out.size()]);
  }

  public int update(Trade model, Connection connection) throws SQLException {
    try (PreparedStatement statement
        = connection.prepareStatement(Statements.makeUpdate(TABLE))) {
      copyAllAndKey(model, statement, 1);
      return statement.executeUpdate();
    }
  }

  public int insert(Trade model, Connection connection) throws SQLException {
    try (PreparedStatement statement
        = connection.prepareStatement(Statements.makeInsertInto(TABLE))) {
      copyKeyAndAll(model, statement, 1);
      return statement.executeUpdate();
    }
  }

  public int delete(Trade model, Connection connection) throws SQLException {
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
      for (Trade model : MOCKS) {
        copyKeyAndAll(model, statement, 1);
        statement.executeUpdate();
      }
    }
  }

  private int copyKeyAndAll(Trade model, PreparedStatement statement, int index)
      throws SQLException {
    return copyAll(model, statement, copyKey(model, statement, index) + 1);
  }

  private int copyAllAndKey(Trade model, PreparedStatement statement, int index)
      throws SQLException {
    return copyKey(model, statement, copyAll(model, statement, index) + 1);
  }

  private int copyKey(Trade model, PreparedStatement statement, int index)
      throws SQLException {
    copyInt(model.getId(), statement, index);
    return index;
  }

  private int copyAll(Trade model, PreparedStatement statement, int index)
      throws SQLException {
    copyString(model.getPlayerInId(), statement, index);
    copyString(model.getPlayerOutId(), statement, ++index);
    copyDouble(model.getWinshareDiff(), statement, ++index);
    copyDouble(model.getSalaryDiff(), statement, ++index);
    copyString(model.getGmId(), statement, ++index);
    copyString(model.getDivision(), statement, ++index);
    return index;
  }

  private int copyTuple(ResultSet results, Trade model, int index)
      throws SQLException {
    model.setId(getInt(results, index));
    model.setPlayerInId(getString(results, ++index));
    model.setPlayerOutId(getString(results, ++index));
    model.setWinshareDiff(getDouble(results, ++index));
    model.setSalaryDiff(getDouble(results, ++index));
    model.setGmId(getString(results, ++index));
    model.setDivision(getString(results, ++index));
    return index;
  }
}
