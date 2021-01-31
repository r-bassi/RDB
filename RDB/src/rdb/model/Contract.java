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

public class Contract extends Table {
  public static final Table NUL = new Contract();
  private static final String NAME = "Contract";
  private static final Column[] COLUMNS = new Column[] {
    new Column("id", "ID", Integer.DEFAULT),
    new Column("salary", "Salary (m/$)", Real.DEFAULT, false, true),
    new Column("duration", "Duration (y)", Integer.DEFAULT, false, true),
    new Column("gmId", "General Manager ID", Char.DEFAULT, false, true)
  };
  private static final Column[] PRIMARY_KEYS = new Column[] {
    COLUMNS[0]
  };
  private static final ForeignKey[] FOREIGN_KEYS = new ForeignKey[] {
    new ForeignKey(GeneralManager.NUL, DEL_CASCADE, COLUMNS[3])
  };

  private int id;
  private double salary;
  private int duration;
  private String gmId;

  public Contract() {
    this(NUL_INT);
  }

  public Contract(int id) {
    this(id, NUL_DOUBLE, NUL_INT, NUL_STRING);
  }

  public Contract(int id, double salary, int duration, String gmId) {
    this.id = id;
    this.salary = salary;
    this.duration = duration;
    this.gmId = gmId;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public double getSalary() {
    return this.salary;
  }

  public void setSalary(double salary) {
    this.salary = salary;
  }

  public int getDuration() {
    return this.duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public String getGmId() {
    return this.gmId;
  }

  public void setGmId(String gmId) {
    this.gmId = gmId;
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
        && equals((Contract) that));
  }

  public boolean equals(Contract that) {
    return this.id == that.id
        && this.salary == that.salary
        && this.duration == that.duration
        && this.gmId.equals(that.gmId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.salary, this.duration, this.gmId);
  }

  @Override
  public String toString() {
    int index = 0;
    return stringifyPair(COLUMNS[index].getLongName(), getId())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getSalary())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getDuration())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getGmId());
  }
}
