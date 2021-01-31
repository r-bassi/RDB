package rdb.presenter;

import java.sql.SQLException;
import rdb.delegate.InitializationDelegate;
import rdb.delegate.MagicDelegate;
import rdb.delegate.ModelDelegate;
import rdb.delegate.TransactionDelegate;
import rdb.driver.AgentDriver;
import rdb.driver.CoachDriver;
import rdb.driver.ContractDriver;
import rdb.driver.GeneralManagerDriver;
import rdb.driver.LeagueDriver;
import rdb.driver.MagicDriver;
import rdb.driver.PlayerDriver;
import rdb.driver.PlaysDriver;
import rdb.driver.TableDriver;
import rdb.driver.TeamDriver;
import rdb.driver.TradeDriver;
import rdb.model.Agent;
import rdb.model.Coach;
import rdb.model.Contract;
import rdb.model.GeneralManager;
import rdb.model.League;
import rdb.model.Player;
import rdb.model.Plays;
import rdb.model.Table;
import rdb.model.Team;
import rdb.model.Trade;
import rdb.shared.Errors;
import rdb.ui.AuthFrame;
import rdb.ui.MainFrame;

public class Session implements InitializationDelegate, ModelDelegate,
    TransactionDelegate, MagicDelegate {
  public static final Table[] TABLES = new Table[] {
    League.NUL,
    Team.NUL,
    GeneralManager.NUL,
    Contract.NUL,
    Agent.NUL,
    Coach.NUL,
    Player.NUL,
    Plays.NUL,
    Trade.NUL
  };
  public static final TableDriver[] DRIVERS = new TableDriver[] {
    LeagueDriver.NUL,
    TeamDriver.NUL,
    GeneralManagerDriver.NUL,
    ContractDriver.NUL,
    AgentDriver.NUL,
    CoachDriver.NUL,
    PlayerDriver.NUL,
    PlaysDriver.NUL,
    TradeDriver.NUL
  };
  private final Database databaser;
  private Agent[] agents = new Agent[0];
  private Coach[] coaches = new Coach[0];
  private Contract[] contracts = new Contract[0];
  private GeneralManager[] generalManagers = new GeneralManager[0];
  private League[] leagues = new League[0];
  private Player[] players = new Player[0];
  private Plays[] plays = new Plays[0];
  private Team[] teams = new Team[0];
  private Trade[] trades = new Trade[0];
  private String[] magics = new String[0];
  private String recentMessage;

  public Session() throws SQLException {
    this.databaser = new Database();
  }

  @Override
  public String getRecentMessage() {
    return this.recentMessage;
  }

  @Override
  public void startAuthUi() {
    new AuthFrame(this).setVisible(true);
  }

  @Override
  public void startMainUi() {
    new MainFrame(this, this, this).setVisible(true);
  }

  @Override
  public int openDatabase(String username, String password) {
    try {
      this.databaser.open(username, password);
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int closeDatabase() {
    try {
      this.databaser.close();
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public String[] getAgentList() {
    String[] out = new String[this.agents.length];
    for (int i = 0; i < out.length; i++) {
      Agent model = this.agents[i];
      out[i] = model.getId() + ": " + model.getName();
    }
    return out;
  }

  @Override
  public Agent getAgentModel(int index) {
    return this.agents[index];
  }

  @Override
  public String[] getCoachList() {
    String[] out = new String[this.coaches.length];
    for (int i = 0; i < out.length; i++) {
      Coach model = this.coaches[i];
      out[i] = model.getId() + ": " + model.getName();
    }
    return out;
  }

  @Override
  public Coach getCoachModel(int index) {
    return this.coaches[index];
  }

  @Override
  public String[] getContractList() {
    String[] out = new String[this.contracts.length];
    for (int i = 0; i < out.length; i++) {
      Contract model = this.contracts[i];
      out[i] = model.getId() + ": $"
          + model.getSalary() + " mil. for "
          + model.getDuration() + " years";
    }
    return out;
  }

  @Override
  public Contract getContractModel(int index) {
    return this.contracts[index];
  }

  @Override
  public String[] getGeneralManagerList() {
    String[] out = new String[this.generalManagers.length];
    for (int i = 0; i < out.length; i++) {
      GeneralManager model = this.generalManagers[i];
      out[i] = model.getId() + ": " + model.getName();
    }
    return out;
  }

  @Override
  public GeneralManager getGeneralManagerModel(int index) {
    return this.generalManagers[index];
  }

  @Override
  public String[] getLeagueList() {
    String[] out = new String[this.leagues.length];
    for (int i = 0; i < out.length; i++) out[i] = this.leagues[i].getDivision();
    return out;
  }

  @Override
  public League getLeagueModel(int index) {
    return this.leagues[index];
  }

  @Override
  public String[] getPlayerList() {
    String[] out = new String[this.players.length];
    for (int i = 0; i < out.length; i++) {
      Player model = this.players[i];
      out[i] = model.getId() + ": " + model.getName();
    }
    return out;
  }

  @Override
  public Player getPlayerModel(int index) {
    return this.players[index];
  }

  @Override
  public String[] getPlaysList() {
    String[] out = new String[this.plays.length];
    for (int i = 0; i < out.length; i++) {
      Plays model = this.plays[i];
      out[i] = "Player `" + model.getUserId() + "` for "
          + model.getTeamCity() + " " + model.getTeamName();
    }
    return out;
  }

  @Override
  public Plays getPlaysModel(int index) {
    return this.plays[index];
  }

  @Override
  public String[] getTeamList() {
    String[] out = new String[this.teams.length];
    for (int i = 0; i < out.length; i++) {
      Team model = this.teams[i];
      out[i]
          = model.getCity() + " " + model.getName() + ", " + model.getDivision();
    }
    return out;
  }

  @Override
  public Team getTeamModel(int index) {
    return this.teams[index];
  }

  @Override
  public String[] getTradeList() {
    String[] out = new String[this.trades.length];
    for (int i = 0; i < out.length; i++) {
      Trade model = this.trades[i];
      out[i] = model.getId() + ": "
          + model.getPlayerInId() + " <=> " + model.getPlayerOutId();
    }
    return out;
  }

  @Override
  public Trade getTradeModel(int index) {
    return this.trades[index];
  }

  @Override
  public String[] getMagics() {
    return this.magics;
  }

  @Override
  public int getAgent(Agent model) {
    try {
      model = AgentDriver.NUL.get(model, this.databaser.getConnection());
      if (model == null) return Errors.NOTHING;
      this.agents = new Agent[] {model};
      this.recentMessage = "Got " + this.agents.length + " agent(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int listAgents() {
    try {
      this.agents = AgentDriver.NUL.list(this.databaser.getConnection());
      if (this.agents.length == 0) return Errors.NOTHING;
      this.recentMessage = "Got " + this.agents.length + " agent(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int updateAgent(Agent model) {
    try {
      int out = AgentDriver.NUL.update(model, this.databaser.getConnection());
      this.recentMessage = "Updated " + out + " agent(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int insertAgent(Agent model) {
    try {
      int out = AgentDriver.NUL.insert(model, this.databaser.getConnection());
      this.recentMessage = "Inserted " + out + " agent(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int deleteAgent(Agent model) {
    try {
      int out = AgentDriver.NUL.delete(model, this.databaser.getConnection());
      this.recentMessage = "Deleted " + out + " agent(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int getCoach(Coach model) {
    try {
      model = CoachDriver.NUL.get(model, this.databaser.getConnection());
      if (model == null) return Errors.NOTHING;
      this.coaches = new Coach[] {model};
      this.recentMessage = "Got " + this.coaches.length + " coach(es).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int listCoaches() {
    try {
      this.coaches = CoachDriver.NUL.list(this.databaser.getConnection());
      if (this.coaches.length == 0) return Errors.NOTHING;
      this.recentMessage = "Got " + this.coaches.length + " coach(es).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int updateCoach(Coach model) {
    try {
      int out = CoachDriver.NUL.update(model, this.databaser.getConnection());
      this.recentMessage = "Updated " + out + " coach(es).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int insertCoach(Coach model) {
    try {
      int out = CoachDriver.NUL.insert(model, this.databaser.getConnection());
      this.recentMessage = "Inserted " + out + " coach(es).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int deleteCoach(Coach model) {
    try {
      int out = CoachDriver.NUL.delete(model, this.databaser.getConnection());
      this.recentMessage = "Deleted " + out + " coach(es).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int getContract(Contract model) {
    try {
      model = ContractDriver.NUL.get(model, this.databaser.getConnection());
      if (model == null) return Errors.NOTHING;
      this.contracts = new Contract[] {model};
      this.recentMessage = "Got " + this.contracts.length + " contract(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int listContracts() {
    try {
      this.contracts = ContractDriver.NUL.list(this.databaser.getConnection());
      if (this.contracts.length == 0) return Errors.NOTHING;
      this.recentMessage = "Got " + this.contracts.length + " contract(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int updateContract(Contract model) {
    try {
      int out = ContractDriver.NUL.update(model, this.databaser.getConnection());
      this.recentMessage = "Updated " + out + " contract(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int insertContract(Contract model) {
    try {
      int out = ContractDriver.NUL.insert(model, this.databaser.getConnection());
      this.recentMessage = "Inserted " + out + " contract(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int deleteContract(Contract model) {
    try {
      int out = ContractDriver.NUL.delete(model, this.databaser.getConnection());
      this.recentMessage = "Deleted " + out + " contract(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int getGeneralManager(GeneralManager model) {
    try {
      model
          = GeneralManagerDriver.NUL.get(model, this.databaser.getConnection());
      if (model == null) return Errors.NOTHING;
      this.generalManagers = new GeneralManager[] {model};
      this.recentMessage
          = "Got " + this.generalManagers.length + " general manager(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int listGeneralManagers() {
    try {
      this.generalManagers
          = GeneralManagerDriver.NUL.list(this.databaser.getConnection());
      if (this.generalManagers.length == 0) return Errors.NOTHING;
      this.recentMessage
          = "Got " + this.generalManagers.length + " general manager(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int updateGeneralManager(GeneralManager model) {
    try {
      int out = GeneralManagerDriver.NUL.update(
          model, this.databaser.getConnection());
      this.recentMessage = "Updated " + out + " general manager(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int insertGeneralManager(GeneralManager model) {
    try {
      int out = GeneralManagerDriver.NUL.insert(
          model, this.databaser.getConnection());
      this.recentMessage = "Inserted " + out + " general manager(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int deleteGeneralManager(GeneralManager model) {
    try {
      int out = GeneralManagerDriver.NUL.delete(
          model, this.databaser.getConnection());
      this.recentMessage = "Deleted " + out + " general manager(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int getLeague(League model) {
    try {
      model = LeagueDriver.NUL.get(model, this.databaser.getConnection());
      if (model == null) return Errors.NOTHING;
      this.leagues = new League[] {model};
      this.recentMessage = "Got " + this.leagues.length + " league(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int listLeagues() {
    try {
      this.leagues = LeagueDriver.NUL.list(this.databaser.getConnection());
      if (this.leagues.length == 0) return Errors.NOTHING;
      this.recentMessage = "Got " + this.leagues.length + " league(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int updateLeague(League model) {
    try {
      int out = LeagueDriver.NUL.update(model, this.databaser.getConnection());
      this.recentMessage = "Updated " + out + " league(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int insertLeague(League model) {
    try {
      int out = LeagueDriver.NUL.insert(model, this.databaser.getConnection());
      this.recentMessage = "Inserted " + out + " league(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int deleteLeague(League model) {
    try {
      int out = LeagueDriver.NUL.delete(model, this.databaser.getConnection());
      this.recentMessage = "Deleted " + out + " league(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int getPlayer(Player model) {
    try {
      model = PlayerDriver.NUL.get(model, this.databaser.getConnection());
      if (model == null) return Errors.NOTHING;
      this.players = new Player[] {model};
      this.recentMessage = "Got " + this.players.length + " player(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int listPlayers() {
    try {
      this.players = PlayerDriver.NUL.list(this.databaser.getConnection());
      if (this.players.length == 0) return Errors.NOTHING;
      this.recentMessage = "Got " + this.players.length + " player(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int updatePlayer(Player model) {
    try {
      int out = PlayerDriver.NUL.update(model, this.databaser.getConnection());
      this.recentMessage = "Updated " + out + " player(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int insertPlayer(Player model) {
    try {
      int out = PlayerDriver.NUL.insert(model, this.databaser.getConnection());
      this.recentMessage = "Inserted " + out + " player(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int deletePlayer(Player model) {
    try {
      int out = PlayerDriver.NUL.delete(model, this.databaser.getConnection());
      this.recentMessage = "Deleted " + out + " player(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int getPlays(Plays model) {
    try {
      model = PlaysDriver.NUL.get(model, this.databaser.getConnection());
      if (model == null) return Errors.NOTHING;
      this.plays = new Plays[] {model};
      this.recentMessage = "Got " + this.plays.length + " plays('s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int listPlays() {
    try {
      this.plays = PlaysDriver.NUL.list(this.databaser.getConnection());
      if (this.plays.length == 0) return Errors.NOTHING;
      this.recentMessage = "Got " + this.plays.length + " plays('s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int updatePlays(Plays model) {
    try {
      int out = PlaysDriver.NUL.update(model, this.databaser.getConnection());
      this.recentMessage = "Updated " + out + " plays('s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int insertPlays(Plays model) {
    try {
      int out = PlaysDriver.NUL.insert(model, this.databaser.getConnection());
      this.recentMessage = "Inserted " + out + " plays('s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int deletePlays(Plays model) {
    try {
      int out = PlaysDriver.NUL.delete(model, this.databaser.getConnection());
      this.recentMessage = "Deleted " + out + " plays('s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int getTeam(Team model) {
    try {
      model = TeamDriver.NUL.get(model, this.databaser.getConnection());
      if (model == null) return Errors.NOTHING;
      this.teams = new Team[] {model};
      this.recentMessage = "Got " + this.teams.length + " team(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int listTeams() {
    try {
      this.teams = TeamDriver.NUL.list(this.databaser.getConnection());
      if (this.teams.length == 0) return Errors.NOTHING;
      this.recentMessage = "Got " + this.teams.length + " team(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int updateTeam(Team model) {
    try {
      int out = TeamDriver.NUL.update(model, this.databaser.getConnection());
      this.recentMessage = "Updated " + out + " team(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int insertTeam(Team model) {
    try {
      int out = TeamDriver.NUL.insert(model, this.databaser.getConnection());
      this.recentMessage = "Inserted " + out + " team(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int deleteTeam(Team model) {
    try {
      int out = TeamDriver.NUL.delete(model, this.databaser.getConnection());
      this.recentMessage = "Deleted " + out + " team(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int getTrade(Trade model) {
    try {
      model = TradeDriver.NUL.get(model, this.databaser.getConnection());
      if (model == null) return Errors.NOTHING;
      this.trades = new Trade[] {model};
      this.recentMessage = "Got " + this.trades.length + " trade(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int listTrades() {
    try {
      this.trades = TradeDriver.NUL.list(this.databaser.getConnection());
      if (this.trades.length == 0) return Errors.NOTHING;
      this.recentMessage = "Got " + this.trades.length + " trade(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int updateTrade(Trade model) {
    try {
      int out = TradeDriver.NUL.update(model, this.databaser.getConnection());
      this.recentMessage = "Updated " + out + " trade(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int insertTrade(Trade model) {
    try {
      int out = TradeDriver.NUL.insert(model, this.databaser.getConnection());
      this.recentMessage = "Inserted " + out + " trade(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int deleteTrade(Trade model) {
    try {
      int out = TradeDriver.NUL.delete(model, this.databaser.getConnection());
      this.recentMessage = "Deleted " + out + " trade(s).";
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int resetDatabase() {
    try {
      databaser.purge(TABLES);
      databaser.populate(TABLES, DRIVERS);
      return Errors.SUCCESS;
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int doSelect(String term) {
    try {
      this.magics = MagicDriver.doSelect(term, this.databaser.getConnection());
      return postMagic();
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int doProject() {
    try {
      this.magics = MagicDriver.doProject(this.databaser.getConnection());
      return postMagic();
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int doJoin(String term) {
    try {
      this.magics = MagicDriver.doJoin(term, this.databaser.getConnection());
      return postMagic();
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int doGroup() {
    try {
      this.magics = MagicDriver.doGroup(this.databaser.getConnection());
      return postMagic();
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int doHaving() {
    try {
      this.magics = MagicDriver.doHaving(this.databaser.getConnection());
      return postMagic();
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int doNested() {
    try {
      this.magics = MagicDriver.doNested(this.databaser.getConnection());
      return postMagic();
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  @Override
  public int doDivide() {
    try {
      this.magics = MagicDriver.doDivide(this.databaser.getConnection());
      return postMagic();
    } catch (SQLException exception) {
      return Errors.parseSqlException(exception);
    }
  }

  private int postMagic() {
    if (this.magics.length == 0) return Errors.NOTHING;
    this.recentMessage = "Magic returned " + this.magics.length + " entry/ies.";
    return Errors.SUCCESS;
  }
}
