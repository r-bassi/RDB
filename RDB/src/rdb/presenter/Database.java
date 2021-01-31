package rdb.presenter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import rdb.driver.TableDriver;
import rdb.model.Table;
import rdb.util.Statements;

public class Database {
  private static final String DBMS_URL = "jdbc:oracle:thin:@localhost:1522:stu";

  private Connection connection = null;

  public Database() throws SQLException {
    DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
  }

  public void open(String username, String password) throws SQLException {
    if (this.connection != null) close();
    this.connection = DriverManager.getConnection(DBMS_URL, username, password);
  }

  public void populate(Table[] tables, TableDriver[] drivers)
      throws SQLException {
    for (Table table : tables) executeDdl(Statements.makeCreateTable(table));
    for (TableDriver driver : drivers) driver.populate(this.connection);
  }

  public void purge(Table[] tables) throws SQLException {
    try (
        Statement list = this.connection.createStatement();
        Statement drop = this.connection.createStatement();
        ResultSet results
        = list.executeQuery("SELECT table_name FROM user_tables")) {
      while (results.next())
        for (Table table : tables)
          if (results.getString(1).equalsIgnoreCase(table.getTableName())) {
            drop.execute(Statements.makeDropTable(table));
            break;
          }
    }
  }

  public void commit() throws SQLException {
    this.connection.commit();
  }

  public void rollback() throws SQLException {
    this.connection.rollback();
  }

  public void close() throws SQLException {
    this.connection.close();
    this.connection = null;
  }

  public ResultSet executeDml(String dml) throws SQLException {
    try (Statement statement = this.connection.createStatement()) {
      return statement.executeQuery(dml);
    }
  }

  public Connection getConnection() {
    return this.connection;
  }

  private int executeDdl(String ddl) throws SQLException {
    try (Statement statement = this.connection.createStatement()) {
      return statement.executeUpdate(ddl);
    }
  }
}
