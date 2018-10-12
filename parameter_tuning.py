import subprocess
import numpy as np
import json
import time
import timeit
import os

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


parameter_dict = {
    "parentSelectionMethod": 1,
    "crossoverMethod": 4,
    "mutationMethod": 1,
    "exchangeMethod": 0
}


start = timeit.default_timer()



counter = 0
prev_percent = 0
step = 0.2
print("{} %".format(int(prev_percent)))
with open("paramters{}.jl".format(int(time.time())), "w") as outfile:
    for evaluation in ["SchaffersEvaluation", "KatsuuraEvaluation"]:
        for subPopulationSize in range(20, 21, 10):
            for numberOfIslands in range(5, 6, 6):
                for migrationSize in range(2, 3, 2):
                    for migrationInterval in range(100, 101, 50):
                        for TournamentSelectionMatingPoolSize in range(2, 7, 2):
                            for TournamentSelectionNumberOfParticipiants in range(1, 7, 2):
                                for UniformMutationProbability in np.arange(0.5, 0.81, step):
                                    for UniformMutationSpeed in np.arange(1.7, 2.01, step):
                                        for roundRobinroundRobinTournamentSurvivorSelectionSize in range(5, subPopulationSize, 10):
                                            parameter_dict["subPopulationSize"] = subPopulationSize
                                            parameter_dict["numberOfIslands"] = numberOfIslands
                                            parameter_dict["migrationSize"] = migrationSize
                                            parameter_dict["migrationInterval"] = migrationInterval
                                            parameter_dict["UniformMutationProbability"] = UniformMutationProbability
                                            parameter_dict["UniformMutationSpeed"] = UniformMutationSpeed
                                            parameter_dict["TournamentSelectionMatingPoolSize"] = TournamentSelectionMatingPoolSize
                                            parameter_dict["TournamentSelectionNumberOfParticipiants"] = TournamentSelectionNumberOfParticipiants
                                            parameter_dict["roundRobinroundRobinTournamentSurvivorSelectionSize"] = roundRobinroundRobinTournamentSurvivorSelectionSize

                                            params = f"subPopulationSize:{parameter_dict['subPopulationSize']}," \
                                                     f"numberOfIslands:{parameter_dict['numberOfIslands']}," \
                                                     f"migrationSize:{parameter_dict['migrationSize']}," \
                                                     f"migrationInterval:{parameter_dict['migrationInterval']}," \
                                                     f"UniformMutationProbability:{parameter_dict['UniformMutationProbability']}," \
                                                     f"UniformMutationSpeed:{parameter_dict['UniformMutationSpeed']}," \
                                                     f"TournamentSelectionMatingPoolSize:{parameter_dict['TournamentSelectionMatingPoolSize']}," \
                                                     f"TournamentSelectionNumberOfParticipiants:{parameter_dict['TournamentSelectionNumberOfParticipiants']}," \
                                                     f"parentSelectionMethod:{parameter_dict['parentSelectionMethod']}," \
                                                     f"crossoverMethod:{parameter_dict['crossoverMethod']}," \
                                                     f"mutationMethod:{parameter_dict['mutationMethod']}," \
                                                     f"exchangeMethod:{parameter_dict['exchangeMethod']}," \
                                                     f"roundRobinroundRobinTournamentSurvivorSelectionSize:{parameter_dict['roundRobinroundRobinTournamentSurvivorSelectionSize']}"
                                            result = subprocess.check_output(['./build_run_for_params.sh', params, evaluation])
                                            for line in result.decode().splitlines():
                                                if line.startswith("Best Score:"):
                                                    score = line.split(":")[1]
                                                    parameter_dict["score"] = score
                                                    json.dump(parameter_dict, outfile)
                                                    outfile.write('\n')
                                            counter += 1
                                            percent = counter/144.0*100
                                            if int(percent) > prev_percent:
                                                prev_percent = int(percent)
                                                print("{} %".format(int(prev_percent)))


stop = timeit.default_timer()

print('Runime: {} sec'.format(stop - start))
