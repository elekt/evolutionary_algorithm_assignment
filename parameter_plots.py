import subprocess
import numpy as np
import json
import time
import timeit
import math
import matplotlib.pyplot as plt
import pickle
import numpy as np


bent_cigar_params_dict = {
    "mutationMethod": 0,
    "NumberOfParticipants": 25.0,
    "MatingPoolSize": 2,
    "MutationProbability": 0.8,
    "PopulationSize": 100,
    "MutationSpeed": 10.0,
    "SurvivorSelectionSize": 4,
    "crossoverMethod": 3
}

schaffers_params_dict = {
    "mutationMethod": 0,
    "NumberOfParticipants": 5.0,
    "MatingPoolSize": 2,
    "MutationProbability": 0.8,
    "PopulationSize": 100,
    "MutationSpeed": 10.0,
    "SurvivorSelectionSize": 12,
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

# for i in range(0, 100):
#     print(i)
#     print(make_process(katsuura_params_dict, result_katsuura_array, "KatsuuraEvaluation")["score"])
#     print(make_process(bent_cigar_params_dict, result_bent_cigar_array, "BentCigarFunction")["score"])
#     print(make_process(schaffers_params_dict, result_schaffers_array, "SchaffersEvaluation")["score"])
#     print()
#
#
# with open('bent_cigar_crowding.pkl', 'wb') as output:
#     pickle.dump(result_bent_cigar_array, output, pickle.HIGHEST_PROTOCOL)
#
# with open('schaffers_crowding.pkl', 'wb') as output:
#     pickle.dump(result_schaffers_array, output, pickle.HIGHEST_PROTOCOL)
#
# with open('katsuura_crowding.pkl', 'wb') as output:
#     pickle.dump(result_katsuura_array, output, pickle.HIGHEST_PROTOCOL)


with open('bent_cigar_islands_400_new.pkl', 'rb') as input:
    result_bent_cigar_array = pickle.load(input)
    bent_cigar_scores = [param["score"] for param in result_bent_cigar_array]
    bent_cigar_scores  = np.array(bent_cigar_scores).astype(np.float)

with open('schaffers_islands_400.pkl', 'rb') as input:
    result_schaffers_array = pickle.load(input)
    schaffers_scores = [param["score"] for param in result_schaffers_array]
    schaffers_scores  = np.array(schaffers_scores).astype(np.float)

with open('katsuura_islands_400.pkl', 'rb') as input:
    result_katsuura_array = pickle.load(input)
    katsuura_scores = [param["score"] for param in result_katsuura_array]
    katsuura_scores  = np.array(katsuura_scores).astype(np.float)


print(np.mean(bent_cigar_scores))
print(np.mean(schaffers_scores))
print(np.mean(katsuura_scores))

fig = plt.figure()
fig.suptitle('Islands algorithm', fontsize=14, fontweight='bold')

ax = fig.add_subplot(131)
ax.boxplot(bent_cigar_scores)
ax.set_title('Bent Cigar')
ax.set_ylabel('Score')
ax.set_yticks(range(1, 11))

ax = fig.add_subplot(132)
ax.boxplot(schaffers_scores)
ax.set_title('Schaffers')
ax.set_yticks(range(1, 11))

ax = fig.add_subplot(133)
ax.boxplot(katsuura_scores)
ax.set_title('Katsuura')
ax.set_yticks(range(1, 11))

plt.show()
