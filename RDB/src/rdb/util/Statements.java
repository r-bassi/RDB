package rdb.util;

import rdb.model.Table;
import rdb.model.low.Column;
import rdb.model.low.ForeignKey;
import static rdb.shared.Constants.SPACE;
import static rdb.shared.Constants.STR_LEN;

public class Statements {
  private static final String ALL = " *";
  private static final String AND = " AND";
  private static final String CASCADE_CONSTRAINTS = " CASCADE CONSTRAINTS";
  private static final String CREATE_TABLE = "CREATE TABLE";
  private static final String DELETE = "DELETE";
  private static final String DELIM = ",";
  private static final String DROP_TABLE = "DROP TABLE";
  private static final String EQUAL = " =";
  private static final String FIELD = " ?";
  private static final String FOREIGN_KEY = " FOREIGN KEY";
  private static final String FROM = " FROM";
  private static final String INSERT_INTO = "INSERT INTO";
  private static final String NOT_NULL = " NOT NULL";
  private static final String PAREN_CLOSE = ")";
  private static final String PAREN_OPEN = " (";
  private static final String PRIMARY_KEY = " PRIMARY KEY";
  private static final String REFERENCES = " REFERENCES";
  private static final String SELECT = "SELECT";
  private static final String SET = " SET";
  private static final String UNIQUE = " UNIQUE";
  private static final String UPDATE = "UPDATE";
  private static final String VALUES = " VALUES";
  private static final String WHERE = " WHERE";

  public static String makeSelect(Table table) {
    return SELECT + ALL + FROM + SPACE + table.getTableName();
  }

  public static String makeSelectWhere(Table table) {
    StringBuilder out = new StringBuilder(STR_LEN).append(makeSelect(table))
        .append(WHERE);
    appendEqualities(table.getTablePrimaryKeys(), out);
    return out.toString();
  }

  public static String makeUpdate(Table table) {
    boolean isLastColumnSkipped = false;
    StringBuilder out = new StringBuilder(STR_LEN).append(UPDATE).append(SPACE)
        .append(table.getTableName()).append(SET).append(SPACE);
    for (int i = 0; i < table.getTableColumns().length; i++) {
      Column column = table.getTableColumns()[i];
      if (table.isPrimaryKey(column)) isLastColumnSkipped = true;
      else {
        if (!isLastColumnSkipped && i >= 1) out.append(DELIM);
        out.append(SPACE).append(column.getName()).append(EQUAL).append(FIELD);
        isLastColumnSkipped = false;
      }
    }
    appendEqualities(table.getTablePrimaryKeys(), out.append(WHERE));
    return out.toString();
  }

  public static String makeInsertInto(Table table) {
    StringBuilder out = new StringBuilder(STR_LEN).append(INSERT_INTO)
        .append(SPACE).append(table.getTableName()).append(VALUES)
        .append(PAREN_OPEN);
    for (int i = 0; i < table.getTableColumns().length; i++) {
      if (i >= 1) out.append(DELIM);
      out.append(FIELD);
    }
    return out.append(PAREN_CLOSE).toString();
  }

  public static String makeDeleteFrom(Table table) {
    StringBuilder out = new StringBuilder(STR_LEN).append(DELETE).append(FROM)
        .append(SPACE).append(table.getTableName()).append(WHERE);
    appendEqualities(table.getTablePrimaryKeys(), out);
    return out.toString();
  }

  public static String makeCreateTable(Table table) {
    Column column;
    ForeignKey foreignKey;
    StringBuilder out = new StringBuilder(STR_LEN).append(CREATE_TABLE)
        .append(SPACE).append(table.getTableName()).append(PAREN_OPEN);
    for (int i = 0; i < table.getTableColumns().length; i++) {
      if (i >= 1) out.append(DELIM).append(SPACE);
      column = table.getTableColumns()[i];
      out.append(column.getName()).append(SPACE)
          .append(column.getType().getName());
      if (column.isUnique()) out.append(UNIQUE);
      if (column.isNotNull()) out.append(NOT_NULL);
    }
    out.append(DELIM).append(PRIMARY_KEY).append(PAREN_OPEN);
    for (int i = 0; i < table.getTablePrimaryKeys().length; i++) {
      if (i >= 1) out.append(DELIM).append(SPACE);
      out.append(table.getTablePrimaryKeys()[i].getName());
    }
    out.append(PAREN_CLOSE);
    if (table.getTableForeignKeys().length > 0) {
      out.append(DELIM);
      for (int i = 0; i < table.getTableForeignKeys().length; i++) {
        if (i >= 1) out.append(DELIM).append(SPACE);
        foreignKey = table.getTableForeignKeys()[i];
        out.append(FOREIGN_KEY).append(PAREN_OPEN);
        for (int j = 0; j < foreignKey.getKeys().length; j++) {
          if (j >= 1) out.append(DELIM).append(SPACE);
          out.append(foreignKey.getKeys()[j].getName());
        }
        out.append(PAREN_CLOSE).append(REFERENCES).append(SPACE)
            .append(foreignKey.getReference().getTableName()).append(SPACE)
            .append(foreignKey.getEvents());
      }
    }
    return out.append(PAREN_CLOSE).toString();
  }

  public static String makeDropTable(Table table) {
    return DROP_TABLE + SPACE + table.getTableName() + CASCADE_CONSTRAINTS;
  }

  private static void appendEqualities(
      Column[] columns, StringBuilder stringBuilder) {
    stringBuilder.append(SPACE);
    for (int i = 0; i < columns.length; i++) {
      if (i >= 1) stringBuilder.append(AND).append(SPACE);
      stringBuilder.append(columns[i].getName()).append(EQUAL).append(FIELD);
    }
  }
}
