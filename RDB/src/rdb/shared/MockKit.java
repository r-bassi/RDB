package rdb.shared;

import rdb.model.Agent;
import rdb.model.Coach;
import rdb.model.Contract;
import rdb.model.GeneralManager;
import rdb.model.League;
import rdb.model.Player;
import rdb.model.Plays;
import rdb.model.Team;
import rdb.model.Trade;

public class MockKit {
  public static final Agent[] AGENTS = new Agent[] {
    new Agent(1, "Rick", 3.0, 1),
    new Agent(2, "Chris", 5.0, 2),
    new Agent(3, "Ben", 4.0, 3),
    new Agent(4, "Charles", 3.0, 4),
    new Agent(5, "Mo", 7.0, 5),
    new Agent(6, "James", 9.0, 6)
  };
  public static final Coach[] COACHES = new Coach[] {
    new Coach(1, "Frank Vogel", 95, 1),
    new Coach(2, "Nick Nurse", 96, 2),
    new Coach(3, "Travis Green", 90, 3),
    new Coach(4, "Kevin Cash", 93, 4),
    new Coach(5, "Mike McCarthy", 87, 5),
    new Coach(6, "Larry David", 98, 6)
  };
  public static final Contract[] CONTRACTS = new Contract[] {
    new Contract(1, 2.3, 3, "ainge1"),
    new Contract(2, 20.0, 5, "morey1"),
    new Contract(3, 4.5, 4, "dubas1"),
    new Contract(4, 0.9, 2, "neander1"),
    new Contract(5, 7.6, 7, "jones1"),
    new Contract(6, 8.8, 8, "biggm")
  };
  public static final GeneralManager[] GENERALMANAGERS = new GeneralManager[] {
    new GeneralManager("ainge1", "dainge", "Danny Ainge",
    17, "Boston", "Celtics"),
    new GeneralManager("morey1", "dmorey", "Daryl Morey",
    12, "Houston", "Rockets"),
    new GeneralManager("dubas1", "kdubas", "Kyle Dubas",
    2, "Toronto", "Maple Leafs"),
    new GeneralManager("neander1", "eneander", "Erik Neander",
    4, "Tampa Bay", "Rays"),
    new GeneralManager("jones1", "jjones", "Jerry Jones",
    31, "Dallas", "Cowboys"),
    new GeneralManager("biggm", "biged", "Big Ed",
    20, "Vancouver", "Canucks")
  };
  public static final League[] LEAGUES = new League[] {
    new League("NBA", 120.0, 100.0, 176.0, "Adam Silver"),
    new League("NFL", 150.0, 120.0, 216.0, "Rob Manfred"),
    new League("NHL", 70.0, 50.0, 96.0, "Gary Bettman"),
    new League("MLB", 100.0, 80.0, 144.0, "Roger Goodell"),
    new League("MLS", 30.0, 20.0, 40.0, "Don Garber")
  };
  public static final Player[] PLAYERS = new Player[] {
    new Player("crosby1", "scrosby", "Sidney Crosby",
    33, 1.80, 94.0, 2.5, 2005, 1, 1),
    new Player("trout1", "mtrout", "Mike Trout",
    29, 1.88, 106.0, 1.6, 2011, 2, 2),
    new Player("betts1", "mbetts", "Mookie Betts",
    27, 1.75, 81.0, 3.4, 2014, 3, 3),
    new Player("james1", "ljames", "LeBron James",
    35, 2.06, 113.0, 4.7, 2003, 4, 5),
    new Player("durant1", "kdurant", "Kevin Durant",
    31, 2.08, 108.0, 3.1, 2008, 5, 5),
    new Player("gr8est1", "imthebest", "Grant Great",
    21, 2.08, 115.0, 4.9, 2020, 6, 6),
    new Player("alsogr8", "prettygood", "Jacob Good",
    20, 2.01, 188.0, 4.5, 2020, 6, 6)
  };
  public static final Plays[] PLAYS = new Plays[] {
    new Plays("alsogr8", "Vancouver", "Canucks"),
    new Plays("betts1", "Toronto", "Maple Leafs"),
    new Plays("crosby1", "Boston", "Celtics"),
    new Plays("durant1", "Boston", "Celtics"),
    new Plays("durant1", "Dallas", "Cowboys"),
    new Plays("durant1", "Houston", "Rockets"),
    new Plays("durant1", "Tampa Bay", "Rays"),
    new Plays("durant1", "Toronto", "Maple Leafs"),
    new Plays("durant1", "Vancouver", "Canucks"),
    new Plays("gr8est1", "Boston", "Celtics"),
    new Plays("gr8est1", "Dallas", "Cowboys"),
    new Plays("gr8est1", "Houston", "Rockets"),
    new Plays("gr8est1", "Tampa Bay", "Rays"),
    new Plays("gr8est1", "Toronto", "Maple Leafs"),
    new Plays("gr8est1", "Vancouver", "Canucks"),
    new Plays("james1", "Tampa Bay", "Rays"),
    new Plays("trout1", "Houston", "Rockets")
  };
  public static final Team[] TEAMS = new Team[] {
    new Team("Boston", "Celtics", 19000, 123.0, 10.0, 109.0, "NBA", "ainge1"),
    new Team("Houston", "Rockets", 20000, 125.0, 12.0, 261.0, "NBA", "morey1"),
    new Team("Vancouver", "Canucks", 29000, 180.0, 16.0, 260.0, "NHL", "biggm"),
    new Team("Toronto", "Maple Leafs", 80000, 120.0, 8.0, 292.0, "NHL", "dubas1"),
    new Team("Tampa Bay", "Rays", 54251, 255.0, 14.0, 48.0, "MLB", "neander1"),
    new Team("Dallas", "Cowboys", 18910, 123.0, 123.0, 7.0, "NFL", "jones1")
  };
  public static final Trade[] TRADES = new Trade[] {
    new Trade(1, PLAYERS[0].getId(), PLAYERS[1].getId(), 0.2, 2,
    GENERALMANAGERS[0].getId(), LEAGUES[0].getDivision()),
    new Trade(2, PLAYERS[2].getId(), PLAYERS[1].getId(), -0.2, -1.4,
    GENERALMANAGERS[4].getId(), LEAGUES[0].getDivision()),
    new Trade(3, PLAYERS[1].getId(), PLAYERS[4].getId(), 4.1, 12.3,
    GENERALMANAGERS[1].getId(), LEAGUES[1].getDivision()),
    new Trade(4, PLAYERS[3].getId(), PLAYERS[0].getId(), -1, -2.2,
    GENERALMANAGERS[2].getId(), LEAGUES[3].getDivision()),
    new Trade(5, PLAYERS[0].getId(), PLAYERS[1].getId(), 1.4, 0.2,
    GENERALMANAGERS[3].getId(), LEAGUES[1].getDivision())
  };
};
