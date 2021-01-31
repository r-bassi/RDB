package rdb.model.low;

import rdb.model.Table;

public class ForeignKey {
  private Table reference;
  private String events;
  private Column[] keys;

  public ForeignKey(Table referenceTable, String events, Column... keys) {
    this.reference = referenceTable;
    this.events = events;
    this.keys = keys;
  }

  public Table getReference() {
    return this.reference;
  }

  public void setReference(Table reference) {
    this.reference = reference;
  }

  public String getEvents() {
    return this.events;
  }

  public void setEvents(String events) {
    this.events = events;
  }

  public Column[] getKeys() {
    return this.keys;
  }

  public void setKeys(Column... keys) {
    this.keys = keys;
  }
}
