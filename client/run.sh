#!/usr/bin/env bash

. ./env.sh

APPNAME="toy_position_keeper"

SERVERS="localhost:21212" # could be comma-separated list of host:port

# remove build artifacts
function clean() {
    rm -rf obj log
}

# compile the source code for procedures and the client
function srccompile() {
    mkdir -p obj
    javac -classpath $CLASSPATH -d obj \
        src/*.java
    # stop if compilation fails
    if [ $? != 0 ]; then exit; fi
}

function client() {
    scenario1
}

# run the client that drives the example
function scenario1() {
    srccompile
    # run client
    java -classpath obj:$CLASSPATH:obj -Dlog4j.configuration=file://$LOG4J \
	client.PositionsBenchmark \
        --displayinterval=5 \
        --warmup=5 \
        --duration=60 \
        --servers=$SERVERS \
        --ratelimit=30000 \
        --autotune=true \
        --latencytarget=1 \
        --traders=2000 \
        --secpercnt=5
}

# run the client that drives the example
function scenario2() {
    srccompile
    # run client
    java -classpath obj:$CLASSPATH:obj -Dlog4j.configuration=file://$LOG4J \
	client.PositionsBenchmark \
        --displayinterval=5 \
        --warmup=5 \
        --duration=60 \
        --servers=$SERVERS \
        --ratelimit=30000 \
        --autotune=true \
        --latencytarget=1 \
        --traders=50 \
        --secpercnt=200
}

function help() {
    echo "Usage: ./run.sh {clean|client|download-stocks|help|srccompile}"
}

# Run the target passed as the first arg on the command line
# If no first arg, run server
if [ $# -gt 1 ]; then help; exit; fi
if [ $# = 1 ]; then $1; else client; fi
