import subprocess
import numpy as np






# - sub Populations Size (10 - 30)
# - Nr. of islands  (10 - 20)
# - Migration size (how many individuals will exchange)  (2 - 5) (lowen than half of the population)
# - Migration interval (after how many generations will the migration begin) (25 - 150)
# - Crossover: Uniform. Uniform crossover will create genomes that will be very different from their parents if their parents are not similar. If its parents are similar, the offspring will be similar to its parents.
# - Crossover rate: A high crossover rate causes the genomes in the next generation to be more random,
#  as there will be more genomes that are a mix of previous generation genomes.  (?? - ?? )
# "TournamentSelectionMatingPoolSize: 2 - 8 % 2
# "TournamentSelectionNumberOfParticipiants: 1 - 8
# "UniformMutationProbability: 0.0 - 1.0
# "UniformMutationSpeed: 0.0 - 2.5
# roundRobinroundRobinTournamentSurvivorSelectionSize 3 - 8

parentSelectionMethod = 1
crossoverMethod = 2
mutationMethod = 1
exchangeMethod = 0


counter = 0
step = 0.2
for evaluation in ["SchaffersEvaluation", "BentCigarFunction", "KatsuuraEvaluation"]:
    for subPopulationSize in range(10, 40, 5):
        for numberOfIslands in range(3, 20, 5):
            for migrationSize in range(2, 6, 2):
                for migrationInterval in range(25, 150, 30):
                    for TournamentSelectionMatingPoolSize in range(2, 7, 2):
                        for TournamentSelectionNumberOfParticipiants in range(1, 7):
                            for UniformMutationProbability in np.arange(0.25, 0.81, step):
                                for UniformMutationSpeed in np.arange(0.5, 2.01, step):
                                    for roundRobinroundRobinTournamentSurvivorSelectionSize in range(5, subPopulationSize, 5):
                                        # params = f"subPopulationSize:{subPopulationSize},numberOfIslands:{numberOfIslands},migrationSize:{migrationSize},migrationInterval:{migrationInterval}," \
                                        #          f"UniformMutationProbability:{UniformMutationProbability},UniformMutationSpeed:{UniformMutationSpeed},TournamentSelectionMatingPoolSize:{TournamentSelectionMatingPoolSize},TournamentSelectionNumberOfParticipiants:{TournamentSelectionNumberOfParticipiants}," \
                                        #          f"parentSelectionMethod:{parentSelectionMethod},crossoverMethod:{crossoverMethod},mutationMethod:{mutationMethod},exchangeMethod:{exchangeMethod},roundRobinroundRobinTournamentSurvivorSelectionSize:{roundRobinroundRobinTournamentSurvivorSelectionSize}"
                                        # result = subprocess.check_output(['/home/redmachine/Desktop/Uva-DataScience/EvolutionaryComputing/assignmentfiles_2017/build_run_for_params.sh', params, evaluation])
                                        # print(result)
                                        counter += 1


print(counter)
