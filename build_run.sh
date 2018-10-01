#!/usr/bin/env bash
export LD_LIBRARY_PATH=./
javac -cp contest.jar *.java
jar cmf MainClass.txt submission.jar *.class
jar uf testrun.jar *.class
java -jar testrun.jar -submission=player2 -evaluation=BentCigarFunction -seed=1
#python clean.py
