package rdb.model;

import java.util.Objects;
import rdb.model.low.Char;
import rdb.model.low.Column;
import rdb.model.low.ForeignKey;
import rdb.model.low.Real;
import static rdb.shared.Constants.NUL_DOUBLE;
import static rdb.shared.Constants.NUL_STRING;

public class League extends Table {
  public static final Table NUL = new League();
  private static final String NAME = "League";
  private static final Column[] COLUMNS = new Column[] {
    new Column("division", "Division", Char.DEFAULT),
    new Column("tvDeal", "TV Deal (m/$)", Real.DEFAULT, false, true),
    new Column("sponsorship", "Sponsorship (m/$)", Real.DEFAULT, false, true),
    new Column("salaryCap", "Salary Cap (m/$)", Real.DEFAULT, false, true),
    new Column("commissioner", "Commissioner", Char.DEFAULT)
  };
  private static final Column[] PRIMARY_KEYS = new Column[] {
    COLUMNS[0]
  };
  private static final ForeignKey[] FOREIGN_KEYS = new ForeignKey[] {};

  private String division;
  private double tvDeal;
  private double sponsorship;
  private double salaryCap;
  private String commissioner;

  public League() {
    this(NUL_STRING);
  }

  public League(String division) {
    this(division, NUL_DOUBLE, NUL_DOUBLE, NUL_DOUBLE, NUL_STRING);
  }

  public League(String division, double tvDeal, double sponsorship,
      double salaryCap, String commissioner) {
    this.division = division;
    this.tvDeal = tvDeal;
    this.sponsorship = sponsorship;
    this.salaryCap = salaryCap;
    this.commissioner = commissioner;
  }

  public String getDivision() {
    return this.division;
  }

  public void setDivision(String division) {
    this.division = division;
  }

  public double getTvDeal() {
    return this.tvDeal;
  }

  public void setTvDeal(double tvDeal) {
    this.tvDeal = tvDeal;
  }

  public double getSponsorship() {
    return this.sponsorship;
  }

  public void setSponsorship(double sponsorship) {
    this.sponsorship = sponsorship;
  }

  public double getSalaryCap() {
    return this.salaryCap;
  }

  public void setSalaryCap(double salaryCap) {
    this.salaryCap = salaryCap;
  }

  public String getCommissioner() {
    return this.commissioner;
  }

  public void setCommissioner(String commissioner) {
    this.commissioner = commissioner;
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
        && equals((League) that));
  }

  public boolean equals(League that) {
    return this.division.equals(that.division)
        && this.tvDeal == that.tvDeal
        && this.sponsorship == that.sponsorship
        && this.salaryCap == that.salaryCap
        && this.commissioner.equals(that.commissioner);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.division, this.tvDeal, this.sponsorship,
        this.salaryCap, this.commissioner);
  }

  @Override
  public String toString() {
    int index = 0;
    return stringifyPair(COLUMNS[index].getLongName(), getDivision())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getTvDeal())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getSponsorship())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(), getSalaryCap())
        + "\n" + stringifyPair(COLUMNS[++index].getLongName(),
            getCommissioner());
  }
}
