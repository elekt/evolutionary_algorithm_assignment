#!/usr/bin/env bash
export LD_LIBRARY_PATH=./
javac -cp contest.jar *.java
jar cmf MainClass.txt submission.jar *.class
jar uf testrun.jar *.class
java -Dvar1="parentSelectionMethod:1,crossoverMethod:4,mutationMethod:1,exchangeMethod:0,subPopulationSize:20,numberOfIslands:5,migrationSize:2,migrationInterval:100,UniformMutationProbability:0.7,UniformMutationSpeed:1.9,TournamentSelectionMatingPoolSize:6,TournamentSelectionNumberOfParticipiants:5,roundRobinroundRobinTournamentSurvivorSelectionSize:15" -jar testrun.jar -submission=player2 -evaluation=BentCigarFunction -seed=1
python clean.py




