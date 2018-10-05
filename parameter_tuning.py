import subprocess

params = "InversionMutationProbability:0.6,SimpleMutationProbability:0.2,SimpleMutationSpeed:1.5,SwapMutationProbability:0.6,SwapMutationNumberOfSwaps:3,ScrambleMutationProbability:0.6,RankingSelectionSUSMatingPoolSize:2,RankingSelectionSUSs:1.3,TournamentSelectionMatingPoolSize:2,TournamentSelectionNumberOfParticipiants:4,UniformParentSelectionMatingPoolSize:2,ParentSelectionMethod:1,CrossoverMethod:2"


result = subprocess.check_output(['/home/redmachine/Desktop/Uva-DataScience/EvolutionaryComputing/assignmentfiles_2017/build_run.sh', params])

print(result)