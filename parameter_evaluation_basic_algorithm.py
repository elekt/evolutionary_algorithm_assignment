import os
import json
import numpy as np
import matplotlib.pyplot as plt
# from scikit import stats

params_array = []
scores_bent_cigar = []
scores_schaffers = []
for item in ["crowding_algo_params1540039096.jl"]:
    if os.path.isfile(item):
        with open(item, 'r') as f:
            line_number = 0
            for line in f:
                params = json.loads(line)
                params_array.append(params)
                score = float(params["score"])
                if params["evaluation"] == "BentCigarFunction":
                    scores_bent_cigar.append((score, line_number))
                if params["evaluation"] == "SchaffersEvaluation":
                    scores_schaffers.append((score, line_number))
                line_number += 1

mean_bent_cigar = np.mean([i[0] for i in scores_bent_cigar])
mean_schaffers = np.mean([i[0] for i in scores_schaffers])

sorted_bent_cigar = sorted(scores_bent_cigar, key=lambda tup: tup[0])[-5:]
print(sorted_bent_cigar)


for score, i in sorted_bent_cigar:
    print("A param set:")
    print("mutationMethod: {}".format(params_array[i]['mutationMethod']))
    print("NumberOfParticipants: {}".format(params_array[i]['NumberOfParticipants']))
    print("MatingPoolSize: {}".format(params_array[i]['MatingPoolSize']))
    print("MutationProbability: {}".format(params_array[i]['MutationProbability']))
    print("PopulationSize: {}".format(params_array[i]['PopulationSize']))
    print("MutationSpeed: {}".format(params_array[i]['MutationSpeed']))
    print("SurvivorSelectionSize: {}".format(params_array[i]['SurvivorSelectionSize']))
    print("score: {}".format(params_array[i]['score']))
    print("crossoverMethod: {}".format(params_array[i]['crossoverMethod']))
    print("runtime: {}".format(params_array[i]['runtime']))
    print("evaluation: {}".format(params_array[i]['evaluation']))
    print()

sorted_schaffers = sorted(scores_schaffers, key=lambda tup: tup[0])[-5:]
print(sorted_schaffers)

for score, i in sorted_schaffers:
    print("A param set:")
    print("mutationMethod: {}".format(params_array[i]['mutationMethod']))
    print("NumberOfParticipants: {}".format(params_array[i]['NumberOfParticipants']))
    print("MatingPoolSize: {}".format(params_array[i]['MatingPoolSize']))
    print("MutationProbability: {}".format(params_array[i]['MutationProbability']))
    print("PopulationSize: {}".format(params_array[i]['PopulationSize']))
    print("MutationSpeed: {}".format(params_array[i]['MutationSpeed']))
    print("SurvivorSelectionSize: {}".format(params_array[i]['SurvivorSelectionSize']))
    print("score: {}".format(params_array[i]['score']))
    print("crossoverMethod: {}".format(params_array[i]['crossoverMethod']))
    print("runtime: {}".format(params_array[i]['runtime']))
    print("evaluation: {}".format(params_array[i]['evaluation']))
    print()


# plt.bar(range(len(means_scores)), means_scores.values(), align='center')
# plt.xticks(range(len(means_scores)), means_scores.keys())
# plt.show()
#
#
# for k, v in means_scores.items():
#     if means_scores[k] > 8.5:
#         print("{}\n{}\n{}\n".format(params_array[k], scores[k], means_scores[k]))