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

public interface ModelDelegate {
  String[] getAgentList();

  Agent getAgentModel(int index);

  String[] getCoachList();

  Coach getCoachModel(int index);

  String[] getContractList();

  Contract getContractModel(int index);

  String[] getGeneralManagerList();

  GeneralManager getGeneralManagerModel(int index);

  String[] getLeagueList();

  League getLeagueModel(int index);

  String[] getPlayerList();

  Player getPlayerModel(int index);

  String[] getPlaysList();

  Plays getPlaysModel(int index);

  String[] getTeamList();

  Team getTeamModel(int index);

  String[] getTradeList();

  Trade getTradeModel(int index);

  String[] getMagics();
}
