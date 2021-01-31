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

public class Trade extends Table {
  public static final Table NUL = new Trade();
  private static final String NAME = "Trade";
  private static final Column[] COLUMNS = new Column[] {
    new Column("id", "ID", Integer.DEFAULT),
    new Column("playerInId", "Inbound Player ID", Char.DEFAULT, false, true),
    new Column("playerOutId", "Outbound Player ID", Char.DEFAULT, false, true),
    new Column("winshareDiff", "Winshare Difference", Real.DEFAULT, false, true),
    new Column("salaryDiff", "Salary Difference", Real.DEFAULT, false, true),
    new Column("gmId", "General Manager ID", Char.DEFAULT, false, true),
    new Column("division", "League Division", Char.DEFAULT, false, true)
  };
  private static final Column[] PRIMARY_KEYS = new Column[] {
    COLUMNS[0]
  };
  private static final ForeignKey[] FOREIGN_KEYS = new ForeignKey[] {
    new ForeignKey(Player.NUL, DEL_CASCADE, COLUMNS[1]),
    new ForeignKey(Player.NUL, DEL_CASCADE, COLUMNS[2]),
    new ForeignKey(GeneralManager.NUL, DEL_CASCADE, COLUMNS[5])
  };

  private int id;
  private String playerInId;
  private String playerOutId;
  private double winshareDiff;
  private double salaryDiff;
  private String gmId;
  private String division;

  public Trade() {
    this(NUL_INT);
  }

  public Trade(int id) {
    this(id, NUL_STRING, NUL_STRING, NUL_DOUBLE, NUL_DOUBLE, NUL_STRING,
        NUL_STRING);
  }

  public Trade(int id, String playerInId, String playerOutId,
      double winshareDiff, double salaryDiff, String gmId, String division) {
    this.id = id;
    this.playerInId = playerInId;
    this.playerOutId = playerOutId;
    this.winshareDiff = winshareDiff;
    this.salaryDiff = salaryDiff;
    this.gmId = gmId;
    this.division = division;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getPlayerInId() {
    return this.playerInId;
  }

  public void setPlayerInId(String playerInId) {
    this.playerInId = playerInId;
  }

  public String getPlayerOutId() {
    return this.playerOutId;
  }

  public void setPlayerOutId(String playerOutId) {
    this.playerOutId = playerOutId;
  }

  public double getWinshareDiff() {
    return this.winshareDiff;
  }

  public void setWinshareDiff(double winshareDiff) {
    this.winshareDiff = winshareDiff;
  }

  public double getSalaryDiff() {
    return this.salaryDiff;
  }

  public void setSalaryDiff(double salaryDiff) {
    this.salaryDiff = salaryDiff;
  }

  public String getGmId() {
    return this.gmId;
  }

  public void setGmId(String gmId) {
    this.gmId = gmId;
  }

  public String getDivision() {
    return this.division;
  }

  public void setDivision(String division) {
    this.division = division;
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
        && equals((Trade) that));
  }

  public boolean equals(Trade that) {
    return this.id == that.id
        && this.playerInId.equals(that.playerInId)
        && this.playerOutId.equals(that.playerOutId)
        && this.winshareDiff == that.winshareDiff
        && this.salaryDiff == that.salaryDiff
        && this.gmId.equals(that.gmId)
        && this.division.equals(that.division);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.playerInId, this.playerOutId,
        this.winshareDiff, this.salaryDiff, this.gmId, this.division);
  }

  @Override
  public String toString() {
    int index = 0;
    return stringifyPair(COLUMNS[index].getLongName(), getId())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getPlayerInId())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getPlayerOutId())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(),
            getWinshareDiff())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getSalaryDiff())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getGmId())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getDivision());
  }
}
