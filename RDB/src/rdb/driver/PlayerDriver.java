package rdb.driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import rdb.model.Player;
import rdb.model.Table;
import rdb.shared.MockKit;
import rdb.util.Statements;

public class PlayerDriver extends TableDriver {
  public static final PlayerDriver NUL = new PlayerDriver();
  private static final Table TABLE = Player.NUL;
  private static final Player[] MOCKS = MockKit.PLAYERS;

  public Player get(Player model, Connection connection) throws SQLException {
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

  public Player[] list(Connection connection) throws SQLException {
    List<Player> out = new LinkedList<>();
    try (Statement statement = connection.createStatement()) {
      ResultSet results = statement.executeQuery(Statements.makeSelect(TABLE));
      while (results.next()) {
        Player model = new Player();
        copyTuple(results, model, 1);
        out.add(model);
      }
    }
    return out.toArray(new Player[out.size()]);
  }

  public int update(Player model, Connection connection) throws SQLException {
    try (PreparedStatement statement
        = connection.prepareStatement(Statements.makeUpdate(TABLE))) {
      copyAllAndKey(model, statement, 1);
      return statement.executeUpdate();
    }
  }

  public int insert(Player model, Connection connection) throws SQLException {
    try (PreparedStatement statement
        = connection.prepareStatement(Statements.makeInsertInto(TABLE))) {
      copyKeyAndAll(model, statement, 1);
      return statement.executeUpdate();
    }
  }

  public int delete(Player model, Connection connection) throws SQLException {
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
      for (Player model : MOCKS) {
        copyKeyAndAll(model, statement, 1);
        statement.executeUpdate();
      }
    }
  }

  private int copyKeyAndAll(Player model, PreparedStatement statement, int index)
      throws SQLException {
    return copyAll(model, statement, copyKey(model, statement, index) + 1);
  }

  private int copyAllAndKey(Player model, PreparedStatement statement, int index)
      throws SQLException {
    return copyKey(model, statement, copyAll(model, statement, index) + 1);
  }

  private int copyKey(Player model, PreparedStatement statement, int index)
      throws SQLException {
    copyString(model.getId(), statement, index);
    return index;
  }

  private int copyAll(Player model, PreparedStatement statement, int index)
      throws SQLException {
    copyString(model.getPasscode(), statement, index);
    copyString(model.getName(), statement, ++index);
    copyInt(model.getAge(), statement, ++index);
    copyDouble(model.getHeight(), statement, ++index);
    copyDouble(model.getWeight(), statement, ++index);
    copyDouble(model.getWinshare(), statement, ++index);
    copyInt(model.getDraftYear(), statement, ++index);
    copyInt(model.getCoachId(), statement, ++index);
    copyInt(model.getAgentId(), statement, ++index);
    return index;
  }

  private int copyTuple(ResultSet results, Player model, int index)
      throws SQLException {
    model.setId(getString(results, index));
    model.setPasscode(getString(results, ++index));
    model.setName(getString(results, ++index));
    model.setAge(getInt(results, ++index));
    model.setHeight(getDouble(results, ++index));
    model.setWeight(getDouble(results, ++index));
    model.setWinshare(getDouble(results, ++index));
    model.setDraftYear(getInt(results, ++index));
    model.setCoachId(getInt(results, ++index));
    model.setAgentId(getInt(results, ++index));
    return index;
  }
}
