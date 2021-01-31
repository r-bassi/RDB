package rdb;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import rdb.presenter.Session;

public class RDB {
  public static void main(String[] args) {
    try {
      new Session().startAuthUi();
    } catch (SQLException exception) {
      JOptionPane.showMessageDialog(null,
          "The database driver failed to initialize.\n\n"
          + exception.getMessage(), "RDB", JOptionPane.ERROR_MESSAGE);
    }
  }
}
