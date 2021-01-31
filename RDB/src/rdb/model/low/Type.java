package rdb.model.low;

public abstract class Type {
  private final int id;
  private final String name;

  protected Type(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
