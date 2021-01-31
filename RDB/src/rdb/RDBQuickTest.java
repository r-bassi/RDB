package rdb;

import java.sql.SQLException;
import java.util.Scanner;
import rdb.driver.AgentDriver;
import rdb.driver.CoachDriver;
import rdb.driver.ContractDriver;
import rdb.driver.GeneralManagerDriver;
import rdb.driver.LeagueDriver;
import rdb.driver.PlayerDriver;
import rdb.driver.PlaysDriver;
import rdb.driver.TeamDriver;
import rdb.driver.TradeDriver;
import rdb.model.Agent;
import rdb.model.Coach;
import rdb.model.Contract;
import rdb.model.GeneralManager;
import rdb.model.League;
import rdb.model.Player;
import rdb.model.Plays;
import rdb.model.Team;
import rdb.model.Trade;
import rdb.presenter.Database;
import rdb.presenter.Session;
import static rdb.shared.Constants.STDERR;
import static rdb.shared.Constants.STDINP;
import static rdb.shared.Constants.STDOUT;
import rdb.shared.MockKit;

public class RDBQuickTest {
  private static final String AUTO_LOGIN = "";

  public static void main(String[] args) {
    try {
      Database database = new Database();
      Scanner scanner = new Scanner(STDINP);
      String username;
      String password;
      if (AUTO_LOGIN.isEmpty()) {
        STDOUT.print("Username: ");
        username = scanner.nextLine();
        STDOUT.print("Password: ");
        password = scanner.nextLine();
      } else {
        username = AUTO_LOGIN.substring(0, AUTO_LOGIN.length() - 10);
        password = AUTO_LOGIN.substring(AUTO_LOGIN.length() - 9);
      }
      STDOUT.print("Opening...");
      database.open(username, password);
      STDOUT.print("OK\nPurge...");
      database.purge(Session.TABLES);
      STDOUT.print("OK\nPopulate...");
      database.populate(Session.TABLES, Session.DRIVERS);
      STDOUT.print("OK\nMatch...");
      {
        STDOUT.print("Agent...");
        Agent[] agents = AgentDriver.NUL.list(database.getConnection());
        for (int i = 0; i < MockKit.AGENTS.length; i++)
          if (!agents[i].equals(MockKit.AGENTS[i]))
            throw new AssertionError(i);
      }
      {
        STDOUT.print("Coach...");
        Coach[] coaches = CoachDriver.NUL.list(database.getConnection());
        for (int i = 0; i < MockKit.COACHES.length; i++)
          if (!coaches[i].equals(MockKit.COACHES[i]))
            throw new AssertionError(i);
      }
      {
        STDOUT.print("Contract...");
        Contract[] contracts = ContractDriver.NUL.list(database.getConnection());
        for (int i = 0; i < MockKit.CONTRACTS.length; i++)
          if (!contracts[i].equals(MockKit.CONTRACTS[i]))
            throw new AssertionError(i);
      }
      {
        STDOUT.print("GeneralManager...");
        GeneralManager[] generalManagers
            = GeneralManagerDriver.NUL.list(database.getConnection());
        for (int i = 0; i < MockKit.GENERALMANAGERS.length; i++)
          if (!generalManagers[i].equals(MockKit.GENERALMANAGERS[i]))
            throw new AssertionError(i);
      }
      {
        STDOUT.print("League...");
        League[] leagues = LeagueDriver.NUL.list(database.getConnection());
        for (int i = 0; i < MockKit.LEAGUES.length; i++)
          if (!leagues[i].equals(MockKit.LEAGUES[i]))
            throw new AssertionError(i);
      }
      {
        STDOUT.print("Player...");
        Player[] players = PlayerDriver.NUL.list(database.getConnection());
        for (int i = 0; i < MockKit.PLAYERS.length; i++)
          if (!players[i].equals(MockKit.PLAYERS[i]))
            throw new AssertionError(i);
      }
      {
        STDOUT.print("Plays...");
        Plays[] plays = PlaysDriver.NUL.list(database.getConnection());
        for (int i = 0; i < MockKit.PLAYS.length; i++)
          if (!plays[i].equals(MockKit.PLAYS[i]))
            throw new AssertionError(i);
      }
      {
        STDOUT.print("Team...");
        Team[] teams = TeamDriver.NUL.list(database.getConnection());
        for (int i = 0; i < MockKit.TEAMS.length; i++)
          if (!teams[i].equals(MockKit.TEAMS[i]))
            throw new AssertionError(i);
      }
      {
        STDOUT.print("Trade...");
        Trade[] trades = TradeDriver.NUL.list(database.getConnection());
        for (int i = 0; i < MockKit.TRADES.length; i++)
          if (!trades[i].equals(MockKit.TRADES[i]))
            throw new AssertionError(i);
      }
      STDOUT.print("OK\nFetching Agents...");
      Agent[] agents = AgentDriver.NUL.list(database.getConnection());
      STDOUT.print("got " + agents.length + ".\nBotching Agents...");
      agents[0].setName(
          "I have a very long name, two sentences in fact--make it longer!");
      agents[2].setName("Integer.MAX_VALUE");
      agents[2].setAgentFee(Integer.MAX_VALUE);
      agents[4].setName("Free Beer");
      agents[4].setAgentFee(0.0);
      for (Agent agent : agents)
        STDOUT.print(AgentDriver.NUL.update(agent, database.getConnection()));
      STDOUT.print("...OK\nDeleting Agent with id = 2...");
      STDOUT.print(AgentDriver.NUL.delete(agents[1], database.getConnection())
          + " deleted.\nFetching Agents...");
      agents = AgentDriver.NUL.list(database.getConnection());
      STDOUT.print("got " + agents.length + ".\nFetching Agent with id = 9...");
      Agent agent = AgentDriver.NUL.get(new Agent(9), database.getConnection());
      if (agent == null) STDOUT.println("nil (OK)");
      else throw new AssertionError(agent.getId());
      agent = new Agent(9, "RDBTestingAgent", 9.9, 2);
      STDOUT.println("Inserting Agent::\n" + agent);
      STDOUT.print(AgentDriver.NUL.insert(agent, database.getConnection())
          + " inserted.\nFetching Agent with id = 5...");
      agent = AgentDriver.NUL.get(new Agent(5), database.getConnection());
      if (agent.getId() != 5) throw new AssertionError(agent.getId());
      STDOUT.println("\n" + agent);
      database.close();
      STDOUT.println("...and closed again.");
      System.exit(0);
    } catch (AssertionError | SQLException throwable) {
      handleThrowable(throwable);
    }
  }

  private static void handleThrowable(Throwable throwable) {
    throwable.printStackTrace(STDERR);
    System.exit(1);
  }
}
