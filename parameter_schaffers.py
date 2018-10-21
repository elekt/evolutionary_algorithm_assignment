import subprocess
import numpy as np
import json
import time
import timeit
import math
import matplotlib.pyplot as plt
import pickle
import numpy as np

with open('bent_cigar_crowding.pkl', 'rb') as input:
    result_bent_cigar_array = pickle.load(input)
    bent_cigar_scores = [param["score"] for param in result_bent_cigar_array]
    bent_cigar_scores  = np.array(bent_cigar_scores).astype(np.float)

with open('schaffers_crowding.pkl', 'rb') as input:
    result_schaffers_array = pickle.load(input)
    schaffers_scores = [param["score"] for param in result_schaffers_array]
    schaffers_scores  = np.array(schaffers_scores).astype(np.float)

with open('katsuura_crowding.pkl', 'rb') as input:
    result_katsuura_array = pickle.load(input)
    katsuura_scores = [param["score"] for param in result_katsuura_array]
    katsuura_scores  = np.array(katsuura_scores).astype(np.float)


print(np.mean(bent_cigar_scores))
print(np.mean(schaffers_scores))
print(np.mean(katsuura_scores))

fig = plt.figure()
fig.suptitle('Crodwing algorithm', fontsize=14, fontweight='bold')

ax = fig.add_subplot(131)
ax.boxplot(bent_cigar_scores)
ax.set_title('Bent Cigar')
ax.set_ylabel('Score')
ax.set_yticks(range(5, 11))

ax = fig.add_subplot(132)
ax.boxplot(schaffers_scores)
ax.set_title('Schaffers')
ax.set_yticks(range(5, 11))

ax = fig.add_subplot(133)
ax.boxplot(katsuura_scores)
ax.set_title('Katsuura')
ax.set_yticks(range(5, 11))

plt.show()
