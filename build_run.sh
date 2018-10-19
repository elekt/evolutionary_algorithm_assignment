#!/usr/bin/env bash
export LD_LIBRARY_PATH=./
python clean.py
javac -cp contest.jar *.java
jar cmf MainClass.txt submission.jar *.class
jar uf testrun.jar *.class
java -Xss1g -Dvar1="parentSelectionMethod:1,crossoverMethod:4,mutationMethod:1,exchangeMethod:0,subPopulationSize:20,numberOfIslands:20,migrationSize:5,migrationInterval:100,UniformMutationProbability:0.5,UniformMutationSpeed:1.9,TournamentSelectionMatingPoolSize:2,TournamentSelectionNumberOfParticipiants:3,roundRobinroundRobinTournamentSurvivorSelectionSize:10" -jar testrun.jar -submission=player2 -evaluation=BentCigarFunction -seed=1
python clean.py




