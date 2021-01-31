package rdb.model;

import rdb.model.low.Column;
import rdb.model.low.ForeignKey;

public abstract class Table {
  public abstract String getTableName();

  public abstract Column[] getTableColumns();

  public abstract Column[] getTablePrimaryKeys();

  public abstract ForeignKey[] getTableForeignKeys();

  public boolean isPrimaryKey(Column column) {
    return contains(column, getTablePrimaryKeys());
  }

  public boolean isForeignKey(Column column) {
    for (ForeignKey key : getTableForeignKeys())
      if (contains(column, key.getKeys())) return true;
    return false;
  }

  protected String stringifyPair(Object key, Object value) {
    return key + ": " + value;
  }

  private boolean contains(Column column, Column[] columns) {
    for (Column columnn : columns) if (columnn == column) return true;
    return false;
  }
}
