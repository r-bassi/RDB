package rdb.model;

import java.util.Objects;
import rdb.model.low.Char;
import rdb.model.low.Column;
import rdb.model.low.ForeignKey;
import rdb.model.low.Integer;
import rdb.model.low.Real;
import static rdb.shared.Constants.DEL_CASCADE;
import static rdb.shared.Constants.NUL_DOUBLE;
import static rdb.shared.Constants.NUL_INT;
import static rdb.shared.Constants.NUL_STRING;

public class Player extends Table {
  public static final Table NUL = new Player();
  private static final String NAME = "Player";
  private static final Column[] COLUMNS = new Column[] {
    new Column("id", "ID", Char.DEFAULT),
    new Column("passcode", "Passcode", Char.DEFAULT),
    new Column("name", "Name", Char.DEFAULT, false, true),
    new Column("age", "Age (y)", Integer.DEFAULT),
    new Column("height", "Height (m)", Real.DEFAULT),
    new Column("weight", "Weight (m)", Real.DEFAULT),
    new Column("winshare", "Winshare", Real.DEFAULT, false, true),
    new Column("draftYear", "Draft Year", Integer.DEFAULT, false, true),
    new Column("coachId", "Coach ID", Integer.DEFAULT, false, true),
    new Column("agentId", "Agent ID", Integer.DEFAULT, false, true)
  };
  private static final Column[] PRIMARY_KEYS = new Column[] {
    COLUMNS[0]
  };
  private static final ForeignKey[] FOREIGN_KEYS = new ForeignKey[] {
    new ForeignKey(Agent.NUL, DEL_CASCADE, COLUMNS[8]),
    new ForeignKey(Coach.NUL, DEL_CASCADE, COLUMNS[9])
  };

  private String id;
  private String passcode;
  private String name;
  private int age;
  private double height;
  private double weight;
  private double winshare;
  private int draftYear;
  private int coachId;
  private int agentId;

  public Player() {
    this(NUL_STRING);
  }

  public Player(String id) {
    this(id, NUL_STRING, NUL_STRING, NUL_INT, NUL_DOUBLE, NUL_DOUBLE, NUL_DOUBLE,
        NUL_INT, NUL_INT, NUL_INT);
  }

  public Player(String id, String passcode, String name, int age, double height,
      double weight, double winshare, int draftYear, int coachId, int agentId) {
    this.id = id;
    this.passcode = passcode;
    this.name = name;
    this.age = age;
    this.height = height;
    this.weight = weight;
    this.winshare = winshare;
    this.draftYear = draftYear;
    this.coachId = coachId;
    this.agentId = agentId;
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

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public double getHeight() {
    return height;
  }

  public void setHeight(double height) {
    this.height = height;
  }

  public double getWeight() {
    return weight;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

  public double getWinshare() {
    return winshare;
  }

  public void setWinshare(double winshare) {
    this.winshare = winshare;
  }

  public int getDraftYear() {
    return draftYear;
  }

  public void setDraftYear(int draftYear) {
    this.draftYear = draftYear;
  }

  public int getCoachId() {
    return coachId;
  }

  public void setCoachId(int coachId) {
    this.coachId = coachId;
  }

  public int getAgentId() {
    return agentId;
  }

  public void setAgentId(int agentId) {
    this.agentId = agentId;
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
        && equals((Player) that));
  }

  public boolean equals(Player that) {
    return this.id.equals(that.id)
        && this.passcode.equals(that.passcode)
        && this.name.equals(that.name)
        && this.age == that.age
        && this.height == that.height
        && this.weight == that.weight
        && this.winshare == that.winshare
        && this.draftYear == that.draftYear
        && this.coachId == that.coachId
        && this.agentId == that.agentId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.passcode, this.name, this.age,
        this.height, this.weight, this.winshare, this.draftYear, this.coachId,
        this.agentId);
  }

  @Override
  public String toString() {
    int index = 0;
    return stringifyPair(COLUMNS[index].getLongName(), getId())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getPasscode())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getName())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getHeight())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getWeight())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getWinshare())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getDraftYear())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getCoachId())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getAgentId());
  }
}
