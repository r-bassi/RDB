package rdb.driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import rdb.model.Agent;
import rdb.model.Table;
import rdb.shared.MockKit;
import rdb.util.Statements;

public class AgentDriver extends TableDriver {
  public static final AgentDriver NUL = new AgentDriver();
  private static final Table TABLE = Agent.NUL;
  private static final Agent[] MOCKS = MockKit.AGENTS;

  public Agent get(Agent model, Connection connection) throws SQLException {
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

  public Agent[] list(Connection connection) throws SQLException {
    List<Agent> out = new LinkedList<>();
    try (Statement statement = connection.createStatement()) {
      ResultSet results = statement.executeQuery(Statements.makeSelect(TABLE));
      while (results.next()) {
        Agent model = new Agent();
        copyTuple(results, model, 1);
        out.add(model);
      }
    }
    return out.toArray(new Agent[out.size()]);
  }

  public int update(Agent model, Connection connection) throws SQLException {
    try (PreparedStatement statement
        = connection.prepareStatement(Statements.makeUpdate(TABLE))) {
      copyAllAndKey(model, statement, 1);
      return statement.executeUpdate();
    }
  }

  public int insert(Agent model, Connection connection) throws SQLException {
    try (PreparedStatement statement
        = connection.prepareStatement(Statements.makeInsertInto(TABLE))) {
      copyKeyAndAll(model, statement, 1);
      return statement.executeUpdate();
    }
  }

  public int delete(Agent model, Connection connection) throws SQLException {
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
      for (Agent model : MOCKS) {
        copyKeyAndAll(model, statement, 1);
        statement.executeUpdate();
      }
    }
  }

  private int copyKeyAndAll(Agent model, PreparedStatement statement, int index)
      throws SQLException {
    return copyAll(model, statement, copyKey(model, statement, index) + 1);
  }

  private int copyAllAndKey(Agent model, PreparedStatement statement, int index)
      throws SQLException {
    return copyKey(model, statement, copyAll(model, statement, index) + 1);
  }

  private int copyKey(Agent model, PreparedStatement statement, int index)
      throws SQLException {
    copyInt(model.getId(), statement, index);
    return index;
  }

  private int copyAll(Agent model, PreparedStatement statement, int index)
      throws SQLException {
    copyString(model.getName(), statement, index);
    copyDouble(model.getAgentFee(), statement, ++index);
    copyInt(model.getContractId(), statement, ++index);
    return index;
  }

  private int copyTuple(ResultSet results, Agent model, int index)
      throws SQLException {
    model.setId(getInt(results, index));
    model.setName(getString(results, ++index));
    model.setAgentFee(getDouble(results, ++index));
    model.setContractId(getInt(results, ++index));
    return index;
  }
}
