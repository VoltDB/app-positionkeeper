TOY POSITION KEEPER INSTRUCTIONS
================================

1. Install VoltDB on the server(s) where the database will run.  Instructions are provided here (http://voltdb.com/docs/UsingVoltDB/installDist.php).

1a. If you install with a .tar.gz file, do one fo the following so that the toy position keeper scripts know where VoltDB is installed:

  - adding the voltdb-3.0/bin directory to your PATH like this:

    export PATH="$PATH:/opt/voltdb-3.0/bin"

  - or, set VOLTDB_HOME as an environment variable:

    export VOLTDB_HOME="/opt/voltdb-3.0"

1b. If you installed using the Debian package, the bin folder is already added to the PATH.

2. Start the database

   Open a Terminal window and run:

    cd position_keeper/db
    ./run.sh

   (keep this window open)

3. Run Scenario 1:

   Open a separate terminal window or tab and run:

    cd position_keeper/client
    ./run.sh scenario1

4. Run Scenario 2:

   In the first window where the dataabase is running, use Ctrl-C to stop the database.  Then restart the database:

    ./run.sh

   In a separate terminal window or tab and run:

    cd position_keeper/client
    ./run.sh scenario2


5. Run additional scenarios:

   Stop and restart the database using Ctrl-C and ./run.sh

   Edit the position_keeper/client/run.sh script:
       modify any of the parameters in the scenario1 or scenario2 sections, such as:
          - duration
          - number of traders
          - securities per trader




