#!/usr/bin/env bash
export LD_LIBRARY_PATH=./
javac -cp contest.jar *.java
jar cmf MainClass.txt submission.jar *.class
jar uf testrun.jar *.class
java -Dvar1="subPopulationSize:20,numberOfIslands:5,migrationSize:5,migrationInterval:50,UniformMutationProbability:0.5,UniformMutationSpeed:0.5,TournamentSelectionMatingPoolSize:10,TournamentSelectionNumberOfParticipiants:10,parentSelectionMethod:1,crossoverMethod:2,mutationMethod:1,exchangeMethod:0,roundRobinroundRobinTournamentSurvivorSelectionSize:5" -jar testrun.jar -submission=player2 -evaluation=SchaffersEvaluation -seed=1
python clean.py



