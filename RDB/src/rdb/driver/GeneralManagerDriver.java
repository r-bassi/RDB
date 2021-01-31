package rdb.driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import rdb.model.GeneralManager;
import rdb.model.Table;
import rdb.shared.MockKit;
import rdb.util.Statements;

public class GeneralManagerDriver extends TableDriver {
  public static final GeneralManagerDriver NUL = new GeneralManagerDriver();
  private static final Table TABLE = GeneralManager.NUL;
  private static final GeneralManager[] MOCKS = MockKit.GENERALMANAGERS;

  public GeneralManager get(GeneralManager model, Connection connection)
      throws SQLException {
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

  public GeneralManager[] list(Connection connection) throws SQLException {
    List<GeneralManager> out = new LinkedList<>();
    try (Statement statement = connection.createStatement()) {
      ResultSet results = statement.executeQuery(Statements.makeSelect(TABLE));
      while (results.next()) {
        GeneralManager model = new GeneralManager();
        copyTuple(results, model, 1);
        out.add(model);
      }
    }
    return out.toArray(new GeneralManager[out.size()]);
  }

  public int update(GeneralManager model, Connection connection)
      throws SQLException {
    try (PreparedStatement statement
        = connection.prepareStatement(Statements.makeUpdate(TABLE))) {
      copyAllAndKey(model, statement, 1);
      return statement.executeUpdate();
    }
  }

  public int insert(GeneralManager model, Connection connection)
      throws SQLException {
    try (PreparedStatement statement
        = connection.prepareStatement(Statements.makeInsertInto(TABLE))) {
      copyKeyAndAll(model, statement, 1);
      return statement.executeUpdate();
    }
  }

  public int delete(GeneralManager model, Connection connection)
      throws SQLException {
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
      for (GeneralManager model : MOCKS) {
        copyKeyAndAll(model, statement, 1);
        statement.executeUpdate();
      }
    }
  }

  private int copyKeyAndAll(GeneralManager model,
      PreparedStatement statement, int index) throws SQLException {
    return copyAll(model, statement, copyKey(model, statement, index) + 1);
  }

  private int copyAllAndKey(GeneralManager model,
      PreparedStatement statement, int index) throws SQLException {
    return copyKey(model, statement, copyAll(model, statement, index) + 1);
  }

  private int copyKey(GeneralManager model,
      PreparedStatement statement, int index) throws SQLException {
    copyString(model.getId(), statement, index);
    return index;
  }

  private int copyAll(GeneralManager model,
      PreparedStatement statement, int index) throws SQLException {
    copyString(model.getPasscode(), statement, index);
    copyString(model.getName(), statement, ++index);
    copyInt(model.getExperience(), statement, ++index);
    copyString(model.getTeamCity(), statement, ++index);
    copyString(model.getTeamName(), statement, ++index);
    return index;
  }

  private int copyTuple(ResultSet results, GeneralManager model, int index)
      throws SQLException {
    model.setId(getString(results, index));
    model.setPasscode(getString(results, ++index));
    model.setName(getString(results, ++index));
    model.setExperience(getInt(results, ++index));
    model.setTeamCity(getString(results, ++index));
    model.setTeamName(getString(results, ++index));
    return index;
  }
}
