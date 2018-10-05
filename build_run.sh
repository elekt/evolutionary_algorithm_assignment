#!/usr/bin/env bash
export LD_LIBRARY_PATH=./
javac -cp contest.jar *.java
jar cmf MainClass.txt submission.jar *.class
jar uf testrun.jar *.class
java -Dvar1="InversionMutationProbability:0.6,SimpleMutationProbability:0.2,SimpleMutationSpeed:1.5,SwapMutationProbability:0.6,SwapMutationNumberOfSwaps:3,ScrambleMutationProbability:0.6,RankingSelectionSUSMatingPoolSize:2,RankingSelectionSUSs:1.3,TournamentSelectionMatingPoolSize:2,TournamentSelectionNumberOfParticipiants:4,UniformParentSelectionMatingPoolSize:2,parentSelectionMethod:1,crossoverMethod:1,mutationMethod:2,exchangeMethod:1" -jar testrun.jar -submission=player2 -evaluation=SchaffersEvaluation -seed=1
python clean.py
