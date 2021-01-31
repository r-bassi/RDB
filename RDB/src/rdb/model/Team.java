package rdb.model;

import java.util.Objects;
import rdb.model.low.Char;
import rdb.model.low.Column;
import rdb.model.low.ForeignKey;
import rdb.model.low.Integer;
import rdb.model.low.Real;
import static rdb.shared.Constants.DEL_SET_NULL;
import static rdb.shared.Constants.NUL_DOUBLE;
import static rdb.shared.Constants.NUL_INT;
import static rdb.shared.Constants.NUL_STRING;

public class Team extends Table {
  public static final Table NUL = new Team();
  private static final String NAME = "Team";
  private static final Column[] COLUMNS = new Column[] {
    new Column("city", "City", Char.DEFAULT),
    new Column("name", "Name", Char.DEFAULT),
    new Column("homeCapacity", "Home Capacity", Integer.DEFAULT, false, true),
    new Column("payroll", "Payroll (m/$)", Real.DEFAULT, false, true),
    new Column("merchandise", "Merchandise (m/$)", Real.DEFAULT, false, true),
    new Column("ticketPrice", "Ticket Price (m/$)", Real.DEFAULT, false, true),
    new Column("division", "League Division", Char.DEFAULT),
    new Column("gmId", "General Manager ID", Char.DEFAULT, true, true)
  };
  private static final Column[] PRIMARY_KEYS = new Column[] {
    COLUMNS[0], COLUMNS[1]
  };
  private static final ForeignKey[] FOREIGN_KEYS = new ForeignKey[] {
    new ForeignKey(League.NUL, DEL_SET_NULL, COLUMNS[6])
  };

  private String city;
  private String name;
  private int homeCapacity;
  private double payroll;
  private double merchandise;
  private double ticketPrice;
  private String division;
  private String gmId;

  public Team() {
    this(NUL_STRING, NUL_STRING);
  }

  public Team(String city, String name) {
    this(city, name, NUL_INT, NUL_DOUBLE, NUL_DOUBLE, NUL_DOUBLE, NUL_STRING,
        NUL_STRING);
  }

  public Team(String city, String name, int homeCapacity, double payroll,
      double merchandise, double ticketPrice, String division, String gmId) {
    this.city = city;
    this.name = name;
    this.homeCapacity = homeCapacity;
    this.payroll = payroll;
    this.merchandise = merchandise;
    this.ticketPrice = ticketPrice;
    this.division = division;
    this.gmId = gmId;
  }

  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getHomeCapacity() {
    return this.homeCapacity;
  }

  public void setHomeCapacity(int homeCapacity) {
    this.homeCapacity = homeCapacity;
  }

  public double getPayroll() {
    return this.payroll;
  }

  public void setPayroll(double payroll) {
    this.payroll = payroll;
  }

  public double getMerchandise() {
    return this.merchandise;
  }

  public void setMerchandise(double merchandise) {
    this.merchandise = merchandise;
  }

  public double getTicketPrice() {
    return this.ticketPrice;
  }

  public void setTicketPrice(double ticketPrice) {
    this.ticketPrice = ticketPrice;
  }

  public String getDivision() {
    return this.division;
  }

  public void setDivision(String division) {
    this.division = division;
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
        && equals((Team) that));
  }

  public boolean equals(Team that) {
    return this.city.equals(that.city)
        && this.name.equals(that.name)
        && this.homeCapacity == that.homeCapacity
        && this.payroll == that.payroll
        && this.merchandise == that.merchandise
        && this.ticketPrice == that.ticketPrice
        && this.division.equals(that.division)
        && this.gmId.equals(that.gmId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.city, this.name, this.homeCapacity, this.payroll,
        this.merchandise, this.ticketPrice, this.division, this.gmId);
  }

  @Override
  public String toString() {
    int index = 0;
    return stringifyPair(COLUMNS[index].getLongName(), getCity())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getName())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(),
            getHomeCapacity())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getPayroll())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getMerchandise())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getTicketPrice())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getDivision())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getGmId());
  }
}
