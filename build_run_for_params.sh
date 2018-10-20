#!/usr/bin/env bash
export LD_LIBRARY_PATH=./
javac -cp contest.jar *.java
jar cmf MainClass.txt submission.jar *.class
java -Dvar1=$1 -jar testrun.jar -submission=player2 -evaluation=$2 -seed=1
