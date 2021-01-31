package rdb.delegate;

public interface InitializationDelegate {
  int openDatabase(String username, String password);

  void startAuthUi();

  void startMainUi();
}
