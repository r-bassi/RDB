package rdb.delegate;

import rdb.model.Agent;
import rdb.model.Coach;
import rdb.model.Contract;
import rdb.model.GeneralManager;
import rdb.model.League;
import rdb.model.Player;
import rdb.model.Plays;
import rdb.model.Team;
import rdb.model.Trade;

public interface TransactionDelegate {
  String getRecentMessage();

  int getAgent(Agent model);

  int listAgents();

  int updateAgent(Agent model);

  int insertAgent(Agent model);

  int deleteAgent(Agent model);

  int getCoach(Coach model);

  int listCoaches();

  int updateCoach(Coach model);

  int insertCoach(Coach model);

  int deleteCoach(Coach model);

  int getContract(Contract model);

  int listContracts();

  int updateContract(Contract model);

  int insertContract(Contract model);

  int deleteContract(Contract model);

  int getGeneralManager(GeneralManager model);

  int listGeneralManagers();

  int updateGeneralManager(GeneralManager model);

  int insertGeneralManager(GeneralManager model);

  int deleteGeneralManager(GeneralManager model);

  int getLeague(League model);

  int listLeagues();

  int updateLeague(League model);

  int insertLeague(League model);

  int deleteLeague(League model);

  int getPlayer(Player model);

  int listPlayers();

  int updatePlayer(Player model);

  int insertPlayer(Player model);

  int deletePlayer(Player model);

  int getPlays(Plays model);

  int listPlays();

  int updatePlays(Plays model);

  int insertPlays(Plays model);

  int deletePlays(Plays model);

  int getTeam(Team model);

  int listTeams();

  int updateTeam(Team model);

  int insertTeam(Team model);

  int deleteTeam(Team model);

  int getTrade(Trade model);

  int listTrades();

  int updateTrade(Trade model);

  int insertTrade(Trade model);

  int deleteTrade(Trade model);

  int resetDatabase();

  int closeDatabase();
}
