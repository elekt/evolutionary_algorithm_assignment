import subprocess
import numpy as np
import json
import time
import timeit
import math
import matplotlib.pyplot as plt
import pickle
import numpy as np

with open('schaffers_benchmark.pkl', 'rb') as input:
    result_schaffers_benchmark_array = pickle.load(input)
    schaffers_benchmark_scores = [param["intermediate_scores"] for param in result_schaffers_benchmark_array]
    schaffers_benchmark_scores  = np.array(schaffers_benchmark_scores[3]).astype(np.float)

with open('schaffers_crowding.pkl', 'rb') as input:
    result_schaffers_crowding_array = pickle.load(input)
    schaffers_crowding_scores = [param["intermediate_scores"] for param in result_schaffers_crowding_array]
    schaffers_crowding_scores  = np.array(schaffers_crowding_scores[3]).astype(np.float)

with open('schaffers_islands_400.pkl', 'rb') as input:
    result_schaffers_islands_array = pickle.load(input)
    schaffers_islands_scores = [param["intermediate_scores"] for param in result_schaffers_islands_array]
    schaffers_islands_scores  = np.array(schaffers_islands_scores[3]).astype(np.float)


print(np.mean(schaffers_benchmark_scores))
print(np.mean(schaffers_crowding_scores))
print(np.mean(schaffers_islands_scores))

# fig = plt.figure()
# fig.suptitle('Crodwing algorithm', fontsize=14, fontweight='bold')
#
# ax = fig.add_subplot(131)
# ax.boxplot(bent_cigar_scores)
# ax.set_title('Bent Cigar')
# ax.set_ylabel('Score')
# ax.set_yticks(range(5, 11))
#
# ax = fig.add_subplot(132)
# ax.boxplot(schaffers_scores)
# ax.set_title('Schaffers')
# ax.set_yticks(range(5, 11))
#
# ax = fig.add_subplot(133)
# ax.boxplot(katsuura_scores)
# ax.set_title('Katsuura')
# ax.set_yticks(range(5, 11))

plt.plot(schaffers_benchmark_scores, color='orange')
plt.plot(schaffers_crowding_scores, color='blue')
plt.plot(schaffers_islands_scores, color='green')
plt.show()
