import os
import json
import numpy as np
import matplotlib.pyplot as plt

files = os.listdir(os.getcwd())

scores = {}
means_scores = {}
standard_deviation_scores = {}
params_array = []
for item in files:
    if os.path.isfile(item):
        if item.startswith("new_params") and item.endswith(".jl"):
            with open(item, 'r') as f:
                line_number = 0
                for line in f:
                    params = json.loads(line)
                    params_array.append(params)
                    score = float(params["score"])
                    if line_number not in scores:
                        scores[line_number] = []
                    scores[line_number].append(score)
                    line_number += 1

for (k, v) in scores.items():
    means_scores[k] = np.mean(v)

print(line_number)

# plt.bar(range(len(means_scores)), means_scores.values(), align='center')
# plt.xticks(range(len(means_scores)), means_scores.keys())
# plt.show()


for k, v in means_scores.items():
    if means_scores[k] > 1:
        print("{}\n{}\n{}\n{}\n".format(params_array[k], scores[k], scores[k+72], "{}/{}".format(means_scores[k], means_scores[k+72])))