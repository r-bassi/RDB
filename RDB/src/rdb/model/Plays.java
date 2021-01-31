package rdb.model;

import java.util.Objects;
import rdb.model.low.Column;
import rdb.model.low.ForeignKey;
import static rdb.shared.Constants.DEL_CASCADE;
import static rdb.shared.Constants.NUL_STRING;

public class Plays extends Table {
  public static final Table NUL = new Plays();
  private static final String NAME = "Plays";
  private static final Column[] COLUMNS = new Column[] {
    new Column("userId", "Player ID",
    Player.NUL.getTablePrimaryKeys()[0].getType()),
    new Column("teamCity", "Team City",
    Team.NUL.getTablePrimaryKeys()[0].getType()),
    new Column("teamName", "Team Name",
    Team.NUL.getTablePrimaryKeys()[1].getType())
  };
  private static final Column[] PRIMARY_KEYS = new Column[] {
    COLUMNS[0], COLUMNS[1], COLUMNS[2]
  };
  private static final ForeignKey[] FOREIGN_KEYS = new ForeignKey[] {
    new ForeignKey(Player.NUL, DEL_CASCADE, COLUMNS[0]),
    new ForeignKey(Team.NUL, DEL_CASCADE, COLUMNS[1], COLUMNS[2])
  };

  private String userId;
  private String teamCity;
  private String teamName;

  public Plays() {
    this(NUL_STRING, NUL_STRING, NUL_STRING);
  }

  public Plays(String userId, String teamCity, String teamName) {
    this.userId = userId;
    this.teamCity = teamCity;
    this.teamName = teamName;
  }

  public String getUserId() {
    return this.userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getTeamCity() {
    return this.teamCity;
  }

  public void setTeamCity(String teamCity) {
    this.teamCity = teamCity;
  }

  public String getTeamName() {
    return this.teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  @Override
  public String getTableName() {
    return NAME;
  }

  @Override
  public Column[] getTableColumns() {
    return COLUMNS;
  }

  @Override
  public Column[] getTablePrimaryKeys() {
    return PRIMARY_KEYS;
  }

  @Override
  public ForeignKey[] getTableForeignKeys() {
    return FOREIGN_KEYS;
  }

  @Override
  public boolean equals(Object that) {
    return this == that || (that != null && getClass() == that.getClass()
        && equals((Plays) that));
  }

  public boolean equals(Plays that) {
    return this.userId.equals(that.userId)
        && this.teamCity.equals(that.teamCity)
        && this.teamName.equals(that.teamName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.userId, this.teamCity, this.teamName);
  }

  @Override
  public String toString() {
    int index = 0;
    return stringifyPair(COLUMNS[index].getLongName(), getUserId())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getTeamCity())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getTeamName());
  }
}
