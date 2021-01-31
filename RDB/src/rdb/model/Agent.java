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

public class Agent extends Table {
  public static final Table NUL = new Agent();
  private static final String NAME = "Agent";
  private static final Column[] COLUMNS = new Column[] {
    new Column("id", "ID", Integer.DEFAULT),
    new Column("name", "Name", Char.DEFAULT, false, true),
    new Column("agentFee", "Fee (%)", Real.DEFAULT, false, true),
    new Column("contractId", "Contract ID", Integer.DEFAULT, true, true)
  };
  private static final Column[] PRIMARY_KEYS = new Column[] {
    COLUMNS[0]
  };
  private static final ForeignKey[] FOREIGN_KEYS = new ForeignKey[] {
    new ForeignKey(Contract.NUL, DEL_CASCADE, COLUMNS[3])
  };

  private int id;
  private String name;
  private double agentFee;
  private int contractId;

  public Agent() {
    this(NUL_INT);
  }

  public Agent(int id) {
    this(id, NUL_STRING, NUL_DOUBLE, NUL_INT);
  }

  public Agent(int id, String name, double agentFee, int contractId) {
    this.id = id;
    this.name = name;
    this.agentFee = agentFee;
    this.contractId = contractId;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getAgentFee() {
    return this.agentFee;
  }

  public void setAgentFee(double agentFee) {
    this.agentFee = agentFee;
  }

  public int getContractId() {
    return this.contractId;
  }

  public void setContractId(int contractId) {
    this.contractId = contractId;
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
        && equals((Agent) that));
  }

  public boolean equals(Agent that) {
    return this.id == that.id
        && this.name.equals(that.name)
        && this.agentFee == that.agentFee
        && this.contractId == that.contractId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.name, this.agentFee, this.contractId);
  }

  @Override
  public String toString() {
    int index = 0;
    return stringifyPair(COLUMNS[index].getLongName(), getId())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getName())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getAgentFee())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getContractId());
  }
}
