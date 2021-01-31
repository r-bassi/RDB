package rdb.model;

import java.util.Objects;
import rdb.model.low.Char;
import rdb.model.low.Column;
import rdb.model.low.ForeignKey;
import rdb.model.low.Integer;
import static rdb.shared.Constants.DEL_CASCADE;
import static rdb.shared.Constants.NUL_INT;
import static rdb.shared.Constants.NUL_STRING;

public class GeneralManager extends Table {
  public static final Table NUL = new GeneralManager();
  private static final String NAME = "GeneralManager";
  private static final Column[] COLUMNS = new Column[] {
    new Column("id", "ID", Char.DEFAULT),
    new Column("passcode", "Passcode", Char.DEFAULT),
    new Column("name", "Name", Char.DEFAULT, false, true),
    new Column("experience", "Experience", Integer.DEFAULT, false, true),
    new Column("teamCity", "Team City", Char.DEFAULT, true, true),
    new Column("teamName", "Team Name", Char.DEFAULT, true, true)
  };
  private static final Column[] PRIMARY_KEYS = new Column[] {
    COLUMNS[0]
  };
  private static final ForeignKey[] FOREIGN_KEYS = new ForeignKey[] {
    new ForeignKey(Team.NUL, DEL_CASCADE, COLUMNS[4], COLUMNS[5])
  };

  private String id;
  private String passcode;
  private String name;
  private int experience;
  private String teamCity;
  private String teamName;

  public GeneralManager() {
    this(NUL_STRING);
  }

  public GeneralManager(String id) {
    this(id, NUL_STRING, NUL_STRING, NUL_INT, NUL_STRING, NUL_STRING);
  }

  public GeneralManager(String id, String passcode, String name, int experience,
      String teamCity, String teamName) {
    this.id = id;
    this.passcode = passcode;
    this.name = name;
    this.experience = experience;
    this.teamCity = teamCity;
    this.teamName = teamName;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPasscode() {
    return this.passcode;
  }

  public void setPasscode(String passcode) {
    this.passcode = passcode;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getExperience() {
    return this.experience;
  }

  public void setExperience(int experience) {
    this.experience = experience;
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
        && equals((GeneralManager) that));
  }

  public boolean equals(GeneralManager that) {
    return this.id.equals(that.id)
        && this.passcode.equals(that.passcode)
        && this.name.equals(that.name)
        && this.experience == that.experience
        && this.teamCity.equals(that.teamCity)
        && this.teamName.equals(that.teamName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.passcode, this.name, this.experience,
        this.teamCity, this.teamName);
  }

  @Override
  public String toString() {
    int index = 0;
    return stringifyPair(COLUMNS[index].getLongName(), getId())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getPasscode())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getName())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getExperience())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getTeamCity())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getTeamName());
  }
}
