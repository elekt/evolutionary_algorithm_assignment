import subprocess
import numpy as np
import json
import time
import timeit
import math
import matplotlib.pyplot as plt

bent_cigar_params_dict = {
    "mutationMethod": 1,
    "NumberOfParticipants": 5.0,
    "MatingPoolSize": 30,
    "MutationProbability": 0.8,
    "PopulationSize": 50,
    "MutationSpeed": 10.0,
    "SurvivorSelectionSize": 12,
    "crossoverMethod": 3
}

schaffers_params_dict = {
    "mutationMethod": 0,
    "NumberOfParticipants": 5.0,
    "MatingPoolSize": 2,
    "MutationProbability": 0.8,
    "PopulationSize": 50,
    "MutationSpeed": 10.0,
    "SurvivorSelectionSize": 4,
    "crossoverMethod": 3
}

result_array = []

def make_process(parameter_dict):
    global result_array
    params = ""
    for k,v in parameter_dict.items():
        params += "{}:{},".format(k, v)
    params = params[:-1]
    result_dict = {}

    result = subprocess.check_output(['./build_run_for_params.sh', params, "BentCigarFunction"])
    score = 0
    runtime = 0
    intermediate_scores = []
    final_diversity = 0
    mean_diversity = 0
    lines = result.decode().splitlines()
    for line in lines:
        if line.startswith("Best Score:"):
            score = line.split(":")[1]
        elif line.startswith("Runtime:"):
            runtime = line.split(":")[1].replace("ms", "")
        elif line.startswith("Score:"):
            intermediate_scores.append(line.split(":")[1].strip())
        elif line.startswith("Final Diversity:"):
            final_diversity = line.split(":")[1].strip()
        elif line.startswith("Mean Diversity:"):
            mean_diversity = line.split(":")[1].strip()

    result_dict["score"] = score
    result_dict["runtime"] = runtime
    result_dict["evaluation"] = "BentCigarFunction"
    result_dict["intermediate_scores"] = intermediate_scores
    result_dict["final_diversity"] = final_diversity
    result_dict["mean_diversity"] = mean_diversity
    result_array.append(result_dict)
    return  result_dict

start = timeit.default_timer()

for i in range(0, 1):
    result = make_process(bent_cigar_params_dict)
    if i == 0:
        print(len(result["intermediate_scores"]))
        plt.plot(result["intermediate_scores"])
        plt.ylabel('Score')
        plt.xlabel('Number of evaluations')
        # plt.xticks(range(0, len(result["intermediate_scores"]), 50))
        # plt.yticks(np.arange(0, 10, 1.0))
        plt.show()


stop = timeit.default_timer()
print('Runime: {} sec'.format(stop - start))
