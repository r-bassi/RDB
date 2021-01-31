package rdb.model;

import java.util.Objects;
import rdb.model.low.Char;
import rdb.model.low.Column;
import rdb.model.low.ForeignKey;
import rdb.model.low.Integer;
import static rdb.shared.Constants.DEL_CASCADE;
import static rdb.shared.Constants.NUL_INT;
import static rdb.shared.Constants.NUL_STRING;

public class Coach extends Table {
  public static final Table NUL = new Coach();
  private static final String NAME = "Coach";
  private static final Column[] COLUMNS = new Column[] {
    new Column("id", "ID", Integer.DEFAULT),
    new Column("name", "Name", Char.DEFAULT, false, true),
    new Column("rating", "Rating", Integer.DEFAULT, false, true),
    new Column("agentId", "Agent ID", Integer.DEFAULT, false, true)
  };
  private static final Column[] PRIMARY_KEYS = new Column[] {
    COLUMNS[0]
  };
  private static final ForeignKey[] FOREIGN_KEYS = new ForeignKey[] {
    new ForeignKey(Agent.NUL, DEL_CASCADE, COLUMNS[3])
  };

  private int id;
  private String name;
  private int rating;
  private int agentId;

  public Coach() {
    this(NUL_INT);
  }

  public Coach(int id) {
    this(id, NUL_STRING, NUL_INT, NUL_INT);
  }

  public Coach(int id, String name, int rating, int agentId) {
    this.id = id;
    this.name = name;
    this.rating = rating;
    this.agentId = agentId;
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

  public int getRating() {
    return this.rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public int getAgentId() {
    return this.agentId;
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
        && equals((Coach) that));
  }

  public boolean equals(Coach that) {
    return this.id == that.id
        && this.name.equals(that.name)
        && this.rating == that.rating
        && this.agentId == that.agentId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.name, this.rating, this.agentId);
  }

  @Override
  public String toString() {
    int index = 0;
    return stringifyPair(COLUMNS[index].getLongName(), getId())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getName())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getRating())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getAgentId());
  }
}
