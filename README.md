# athDB
An academic athlete management database project made using Java/JDBC

Made in collaboration with [@BrendonIrwan](https://github.com/BrendonIrwan) and [@davidzhouars](https://github.com/davidzhouars)

## Running

##### SSH Tunnel

    $ ssh -l cwlId -L localhost:1522:dbhost.students.cs.ubc.ca:1522 remote.students.cs.ubc.ca

##### Application

    $ java -jar bin/rdb.jar

## Driver Testing

Run `rdb.RDBQuickTest` with your IDE. This will reset the database.

To enable auto login, open the main class and set `AUTO_LOGIN` to `ora_cwlId,a12345678`.

## Building

##### Driver Extraction

Put `ojdbc8.jar` in `./lib` and then:

    $ cd bin && jar -xf ../lib/ojdbc8.jar oracle && cd ..

##### Compilation + Packaging

    $ javac --class-path lib/ojdbc8.jar -d bin --release 8 --source-path src src/rdb/RDB.java && jar -c -f bin/rdb.jar -e rdb.RDB -C bin rdb -C bin oracle
