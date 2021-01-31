package rdb.model.low;

public class Column {
  private final String name;
  private final String longName;
  private final Type type;
  private final boolean unique;
  private final boolean notNull;

  public Column(String name, String longName, Type type) {
    this(name, longName, type, false, false);
  }

  public Column(String name, String longName, Type type, boolean unique,
      boolean notNull) {
    this.name = name;
    this.longName = longName;
    this.type = type;
    this.unique = unique;
    this.notNull = notNull;
  }

  public String getName() {
    return this.name;
  }

  public String getLongName() {
    return this.longName;
  }

  public Type getType() {
    return this.type;
  }

  public boolean isUnique() {
    return this.unique;
  }

  public boolean isNotNull() {
    return this.notNull;
  }

  @Override
  public String toString() {
    return this.getName() + " : " + this.getType();
  }
}
