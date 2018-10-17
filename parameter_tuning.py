import subprocess
import numpy as np
import json
import time
import timeit
import os

parameter_dict = {
    "parentSelectionMethod": 1,
    "crossoverMethod": 4,
    "mutationMethod": 1,
    "exchangeMethod": 0
}

start = timeit.default_timer()
for i in range(0, 20):
    counter = 0
    prev_percent = 0
    print("{} %".format(int(prev_percent)))
    with open("new_params{}.jl".format(int(time.time())), "w") as outfile:
        for evaluation in ["SchaffersEvaluation"]:
            for subPopulationSize in range(20, 101, 20):
                for numberOfIslands in [3, 5, 10, 20]:
                    for migrationSize in range(2, 6):
                        for migrationInterval in [50, 100, 150]:
                            TournamentSelectionMatingPoolSize = 2
                            TournamentSelectionNumberOfParticipiants = 3
                            UniformMutationProbability = 0.5
                            UniformMutationSpeed = 1.9
                            roundRobinroundRobinTournamentSurvivorSelectionSize = 10

                            parameter_dict["subPopulationSize"] = subPopulationSize
                            parameter_dict["numberOfIslands"] = numberOfIslands
                            parameter_dict["migrationSize"] = migrationSize
                            parameter_dict["migrationInterval"] = migrationInterval
                            parameter_dict["UniformMutationProbability"] = UniformMutationProbability
                            parameter_dict["UniformMutationSpeed"] = UniformMutationSpeed
                            parameter_dict["TournamentSelectionMatingPoolSize"] = TournamentSelectionMatingPoolSize
                            parameter_dict["TournamentSelectionNumberOfParticipiants"] = TournamentSelectionNumberOfParticipiants
                            parameter_dict["roundRobinroundRobinTournamentSurvivorSelectionSize"] = roundRobinroundRobinTournamentSurvivorSelectionSize
                            parameter_dict["evaluation"] = evaluation

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
                            score = 0
                            runtime = 0
                            for line in result.decode().splitlines():
                                if line.startswith("Best Score:"):
                                    score = line.split(":")[1]
                                    print("BAH+")
                                if line.startswith("Runtime:"):
                                    runtime = line.split(":")[1].replace("ms", "")

                            print(result)
                            parameter_dict["score"] = score
                            parameter_dict["runtime"] = runtime
                            json.dump(parameter_dict, outfile)
                            outfile.write('\n')
                            counter += 1
                            percent = counter / 480.0 * 100
                            if int(percent) > prev_percent:
                                prev_percent = int(percent)
                                print("{} %".format(int(prev_percent)))

stop = timeit.default_timer()

print('Runime: {} sec'.format(stop - start))
