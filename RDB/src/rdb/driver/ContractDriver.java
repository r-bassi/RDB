package rdb.driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import rdb.model.Contract;
import rdb.model.Table;
import rdb.shared.MockKit;
import rdb.util.Statements;

public class ContractDriver extends TableDriver {
  public static final ContractDriver NUL = new ContractDriver();
  private static final Table TABLE = Contract.NUL;
  private static final Contract[] MOCKS = MockKit.CONTRACTS;

  public Contract get(Contract model, Connection connection) throws SQLException {
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

  public Contract[] list(Connection connection) throws SQLException {
    List<Contract> out = new LinkedList<>();
    try (Statement statement = connection.createStatement()) {
      ResultSet results = statement.executeQuery(Statements.makeSelect(TABLE));
      while (results.next()) {
        Contract model = new Contract();
        copyTuple(results, model, 1);
        out.add(model);
      }
    }
    return out.toArray(new Contract[out.size()]);
  }

  public int update(Contract model, Connection connection) throws SQLException {
    try (PreparedStatement statement
        = connection.prepareStatement(Statements.makeUpdate(TABLE))) {
      copyAllAndKey(model, statement, 1);
      return statement.executeUpdate();
    }
  }

  public int insert(Contract model, Connection connection) throws SQLException {
    try (PreparedStatement statement
        = connection.prepareStatement(Statements.makeInsertInto(TABLE))) {
      copyKeyAndAll(model, statement, 1);
      return statement.executeUpdate();
    }
  }

  public int delete(Contract model, Connection connection) throws SQLException {
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
      for (Contract model : MOCKS) {
        copyKeyAndAll(model, statement, 1);
        statement.executeUpdate();
      }
    }
  }

  private int copyKeyAndAll(Contract model,
      PreparedStatement statement, int index) throws SQLException {
    return copyAll(model, statement, copyKey(model, statement, index) + 1);
  }

  private int copyAllAndKey(Contract model,
      PreparedStatement statement, int index) throws SQLException {
    return copyKey(model, statement, copyAll(model, statement, index) + 1);
  }

  private int copyKey(Contract model,
      PreparedStatement statement, int index) throws SQLException {
    copyInt(model.getId(), statement, index);
    return index;
  }

  private int copyAll(Contract model,
      PreparedStatement statement, int index) throws SQLException {
    copyDouble(model.getSalary(), statement, index);
    copyInt(model.getDuration(), statement, ++index);
    copyString(model.getGmId(), statement, ++index);
    return index;
  }

  private int copyTuple(ResultSet results, Contract model, int index)
      throws SQLException {
    model.setId(getInt(results, index));
    model.setSalary(getDouble(results, ++index));
    model.setDuration(getInt(results, ++index));
    model.setGmId(getString(results, ++index));
    return index;
  }
}
