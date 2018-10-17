import os
import json
import numpy as np
import matplotlib.pyplot as plt

files = os.listdir(os.getcwd())

scores = {}
means_scores = {}
standard_deviation_scores = {}
params_array = {}
for item in files:
    if os.path.isfile(item):
        if item.startswith("schaffers_params") and item.endswith(".jl"):
            with open(item, 'r') as f:
                line_number = 0
                for line in f:
                    params = json.loads(line)
                    if line_number not in params_array:
                        params_array[line_number] = []
                    params_array[line_number].append(params)
                    score = float(params["score"])
                    if score > 9:
                        print("{} {}".format(item, line_number))
                    if line_number not in scores:
                        scores[line_number] = []
                    scores[line_number].append(score)
                    line_number += 1

# max_mean = 0
# for (k, v) in scores.items():
#     means_scores[k] = np.mean(v)
#     if means_scores[k] > max_mean:
#         max_mean = k
# print(k)

for (k, v) in scores.items():
    for s in v:
        if s > 9:
            print(scores[k])
            continue


# plt.bar(range(len(means_scores)), means_scores.values(), align='center')
# plt.xticks(range(len(means_scores)), means_scores.keys())
# plt.show()
#




# max_standard_deviation_K = 0
# max_standard_deviation_S = 0
# for (k, v) in scores.items():
#     standard_deviation_scores[k] = np.std(v)
#     print(standard_deviation_scores[k])
#
# plt.bar(range(len(standard_deviation_scores)), standard_deviation_scores.values(), align='center')
# plt.xticks(range(len(standard_deviation_scores)), standard_deviation_scores.keys())
#
# plt.show()


