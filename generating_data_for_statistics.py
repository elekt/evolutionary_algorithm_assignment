import subprocess
import numpy as np
import json
import time
import timeit
import math
import matplotlib.pyplot as plt
import pickle

bent_cigar_params_dict = {
    "mutationMethod": 1,
    "NumberOfParticipants": 5.0,
    "MatingPoolSize": 30,
    "MutationProbability": 0.8,
    "PopulationSize": 100,
    "MutationSpeed": 10.0,
    "SurvivorSelectionSize": 12,
    "crossoverMethod": 3
}

schaffers_params_dict = {
    "mutationMethod": 0,
    "NumberOfParticipants": 5.0,
    "MatingPoolSize": 2,
    "MutationProbability": 0.8,
    "PopulationSize": 100,
    "MutationSpeed": 10.0,
    "SurvivorSelectionSize": 4,
    "crossoverMethod": 3
}

katsuura_params_dict = {
    "mutationMethod": 0,
    "NumberOfParticipants": 5.0,
    "MatingPoolSize": 4,
    "MutationProbability": 0.5,
    "PopulationSize": 100,
    "MutationSpeed": 10.0,
    "SurvivorSelectionSize": 4,
    "crossoverMethod": 3
}

result_bent_cigar_array = []
result_schaffers_array = []
result_katsuura_array = []

def make_process(parameter_dict, result_array, evaluation):
    params = ""
    for k,v in parameter_dict.items():
        params += "{}:{},".format(k, v)
    params = params[:-1]
    result_dict = {}

    result = subprocess.check_output(['./build_run_for_params.sh', params, evaluation])
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
    return result_dict


for i in range(0, 100):
    print(i)
    print(make_process(katsuura_params_dict, result_katsuura_array, "KatsuuraEvaluation")["score"])
    print(make_process(bent_cigar_params_dict, result_bent_cigar_array, "BentCigarFunction")["score"])
    print(make_process(schaffers_params_dict, result_schaffers_array, "SchaffersEvaluation")["score"])
    print()


with open('bent_cigar_benchmark_final.pkl', 'wb') as output:
    pickle.dump(result_bent_cigar_array, output, pickle.HIGHEST_PROTOCOL)

with open('schaffers_benchmark_final.pkl', 'wb') as output:
    pickle.dump(result_schaffers_array, output, pickle.HIGHEST_PROTOCOL)

with open('katsuura_benchmark_final.pkl', 'wb') as output:
    pickle.dump(result_katsuura_array, output, pickle.HIGHEST_PROTOCOL)


## read
with open('bent_cigar_benchmark_final.pkl', 'rb') as input:
    print(pickle.load(input))

with open('schaffers_benchmark_final.pkl', 'rb') as input:
    print(pickle.load(input))

with open('katsuura_benchmark_final.pkl', 'rb') as input:
    print(pickle.load(input))
