#!/usr/bin/env bash
export LD_LIBRARY_PATH=./
python clean.py
javac -cp contest.jar *.java
jar cmf MainClass.txt submission.jar *.class
jar uf testrun.jar *.class
java -Dvar1="parentSelectionMethod:1,crossoverMethod:4,mutationMethod:1,exchangeMethod:0,subPopulationSize:50,numberOfIslands:3,migrationSize:4,migrationInterval:50,UniformMutationProbability:0.5,UniformMutationSpeed:10,TournamentSelectionMatingPoolSize:2,TournamentSelectionNumberOfParticipiants:10,roundRobinroundRobinTournamentSurvivorSelectionSize:10" -jar testrun.jar -submission=player2 -evaluation=KatsuuraEvaluation -seed=1
python clean.py




