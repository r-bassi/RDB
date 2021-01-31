package rdb.shared;

import java.sql.SQLException;
import static rdb.shared.Constants.CHAR_LEN;
import static rdb.shared.Constants.STDERR;

public class Errors {
  public static final int SUCCESS = 0;
  public static final int FAILURE = 1;
  public static final int NETWORK = 2;
  public static final int CREDENTIALS = 3;
  public static final int DUPLICATE = 4;
  public static final int PARENT = 5;
  public static final int RESOURCE = 6;
  public static final int TABLE = 7;
  public static final int NOTHING = -1;
  public static final int IMPLEMENT = -2;
  public static final int LISTSELECT = -3;
  public static final int CANCEL = -4;
  public static final String MSG_SUCCESS
      = "Success.";
  public static final String MSG_FAILURE
      = "Unknown DBMS error.";
  public static final String MSG_NETWORK
      = "Networking error. Check SSH tunnel.";
  public static final String MSG_CREDENTIALS
      = "Invalid DBMS credentials.";
  public static final String MSG_DUPLICATE
      = "Another entry with a duplicate attribute exists.";
  public static final String MSG_PARENT
      = "The referenced foreign entry does not exist.";
  public static final String MSG_RESOURCE
      = "The DBMS may be busy.";
  public static final String MSG_TABLE
      = "The table or view does not exist.";
  public static final String MSG_NOTHING
      = "No entries found.";
  public static final String MSG_IMPLEMENT
      = "Not implemented.";
  public static final String MSG_UNKNOWN
      = "Unknown backend error.";
  public static final String MSG_EMPTY
      = "Can't be empty.";
  public static final String MSG_OVERFLOW
      = "Overflow; length > " + CHAR_LEN + ".";
  public static final String MSG_LISTSELECT
      = "The list or selection is empty.";
  public static final String MSG_CANCEL
      = "Cancelled.";

  public static int parseSqlException(SQLException exception) {
    STDERR.println(exception.getErrorCode());
    exception.printStackTrace(STDERR);
    switch (exception.getErrorCode()) {
      case 1: return DUPLICATE;
      case 54: return RESOURCE;
      case 942: return TABLE;
      case 1017: return CREDENTIALS;
      case 2291: return PARENT;
      case 17002: return NETWORK;
      default: return FAILURE;
    }
  }

  public static String parseErrorCode(int errorCode) {
    switch (errorCode) {
      case SUCCESS: return MSG_SUCCESS;
      case FAILURE: return MSG_FAILURE;
      case NETWORK: return MSG_NETWORK;
      case CREDENTIALS: return MSG_CREDENTIALS;
      case DUPLICATE: return MSG_DUPLICATE;
      case PARENT: return MSG_PARENT;
      case RESOURCE: return MSG_RESOURCE;
      case TABLE: return MSG_TABLE;
      case NOTHING: return MSG_NOTHING;
      case IMPLEMENT: return MSG_IMPLEMENT;
      case LISTSELECT: return MSG_LISTSELECT;
      case CANCEL: return MSG_CANCEL;
      default: return MSG_UNKNOWN;
    }
  }
}
