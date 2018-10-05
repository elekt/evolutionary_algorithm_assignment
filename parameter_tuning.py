import subprocess

# "InversionMutationProbability: 0.0 - 1.0
# "SimpleMutationProbability: 0.0 - 1.0
# "SimpleMutationSpeed: 0.0 - 2.5
# "SwapMutationProbability: 0.0 - 1.0
# "SwapMutationNumberOfSwaps: 0 - 5
# "ScrambleMutationProbability: 0.0 - 1.0
# "RankingSelectionSUSMatingPoolSize: 0 - 6 % 2
# "RankingSelectionSUSs: 1.0001 - 2.0
# "TournamentSelectionMatingPoolSize: 2 - 8 % 2
# "TournamentSelectionNumberOfParticipiants: 1 - 8
# "UniformParentSelectionMatingPoolSize: 2 - 8 % 2
# crossoverMethod 0 - 3
# mutationMethod 0 - 3
# parentSelectionMethod 0 - 2
# exchangeMethod 0 - 2


params = "InversionMutationProbability:{},SimpleMutationProbability:{},SimpleMutationSpeed:{},SwapMutationProbability:{},SwapMutationNumberOfSwaps:{},ScrambleMutationProbability:{},RankingSelectionSUSMatingPoolSize:{},RankingSelectionSUSs:{},TournamentSelectionMatingPoolSize:{},TournamentSelectionNumberOfParticipiants:{},UniformParentSelectionMatingPoolSize:{},ParentSelectionMethod:{},CrossoverMethod:{}"

result = subprocess.check_output(['/home/redmachine/Desktop/Uva-DataScience/EvolutionaryComputing/assignmentfiles_2017/build_run_for_params.sh', params])

print(result)