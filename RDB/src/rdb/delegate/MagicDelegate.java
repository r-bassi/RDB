package rdb.delegate;

public interface MagicDelegate {
  int doSelect(String term);

  int doProject();

  int doJoin(String term);

  int doGroup();

  int doHaving();

  int doNested();

  int doDivide();
}
