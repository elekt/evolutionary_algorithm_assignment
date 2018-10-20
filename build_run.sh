#!/usr/bin/env bash
export LD_LIBRARY_PATH=./
python clean.py
javac -cp contest.jar *.java
jar cmf MainClass.txt submission.jar *.class
jar uf testrun.jar *.class
java -Dvar1="MatingPoolSize:2,MutationProbability:0.01,MutationSpeed:3.01,SurvivorSelectionSize:4,mutationMethod:0,parentSelectionMethod:0,crossoverMethod:0,PopulationSize:50,NumberOfParticipants:3" -jar testrun.jar -submission=player2 -evaluation=KatsuuraEvaluation -seed=1
python clean.py