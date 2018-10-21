import subprocess
import numpy as np
import json
import time
import timeit
import math
import matplotlib.pyplot as plt
import pickle
import numpy as np

with open('bent_cigar_benchmark_inter_score.pkl', 'rb') as input:
    result_bent_cigar_benchmark_array = pickle.load(input)
    bent_cigar_benchmark_scores = [param["intermediate_scores"] for param in result_bent_cigar_benchmark_array]
    bent_cigar_benchmark_scores = np.array([ np.array(scores).astype(np.float) for scores in bent_cigar_benchmark_scores ])
    bent_cigar_benchmark_scores = bent_cigar_benchmark_scores.mean(axis=0)
    print(np.mean(np.array([param["score"] for param in result_bent_cigar_benchmark_array]).astype(np.float)))
    bent_cigar_benchmark_evals = [param["eval_number"] for param in result_bent_cigar_benchmark_array]
    bent_cigar_benchmark_evals  = np.array(bent_cigar_benchmark_evals[0]).astype(np.float)


with open('bent_cigar_crowding_inter_score.pkl', 'rb') as input:
    result_bent_cigar_crowding_array = pickle.load(input)
    bent_cigar_crowding_scores = [param["intermediate_scores"] for param in result_bent_cigar_crowding_array]
    bent_cigar_crowding_scores  = np.array([ np.array(scores).astype(np.float) for scores in bent_cigar_crowding_scores ])
    bent_cigar_crowding_scores = bent_cigar_crowding_scores.mean(axis=0)
    print(np.mean(np.array([param["score"] for param in result_bent_cigar_crowding_array]).astype(np.float)))
    bent_cigar_crowding_evals = [param["eval_number"] for param in result_bent_cigar_crowding_array]
    bent_cigar_crowding_evals  = np.array(bent_cigar_crowding_evals[0]).astype(np.float)

with open('bent_cigar_islands_inter_score.pkl', 'rb') as input:
    result_bent_cigar_islands_array = pickle.load(input)
    bent_cigar_islands_scores = [param["intermediate_scores"] for param in result_bent_cigar_islands_array]
    bent_cigar_islands_scores  = np.array([ np.array(scores).astype(np.float) for scores in bent_cigar_islands_scores ])
    bent_cigar_islands_scores = bent_cigar_islands_scores.mean(axis=0)
    print(np.mean(np.array([param["score"] for param in result_bent_cigar_islands_array]).astype(np.float)))
    bent_cigar_islands_evals = [param["eval_number"] for param in result_bent_cigar_islands_array]
    bent_cigar_islands_evals  = np.array(bent_cigar_islands_evals[0]).astype(np.float)

with open('schaffers_benchmark_inter_score.pkl', 'rb') as input:
    result_schaffers_benchmark_array = pickle.load(input)
    schaffers_benchmark_scores = [param["intermediate_scores"] for param in result_schaffers_benchmark_array]
    schaffers_benchmark_scores = np.array([ np.array(scores).astype(np.float) for scores in schaffers_benchmark_scores ])
    schaffers_benchmark_scores = schaffers_benchmark_scores.mean(axis=0)
    print(np.mean(np.array([param["score"] for param in result_schaffers_benchmark_array]).astype(np.float)))
    schaffers_benchmark_evals = [param["eval_number"] for param in result_schaffers_benchmark_array]
    schaffers_benchmark_evals  = np.array(schaffers_benchmark_evals[0]).astype(np.float)


with open('schaffers_crowding_inter_score.pkl', 'rb') as input:
    result_schaffers_crowding_array = pickle.load(input)
    schaffers_crowding_scores = [param["intermediate_scores"] for param in result_schaffers_crowding_array]
    schaffers_crowding_scores  = np.array([ np.array(scores).astype(np.float) for scores in schaffers_crowding_scores ])
    schaffers_crowding_scores = schaffers_crowding_scores.mean(axis=0)
    print(np.mean(np.array([param["score"] for param in result_schaffers_crowding_array]).astype(np.float)))
    schaffers_crowding_evals = [param["eval_number"] for param in result_schaffers_crowding_array]
    schaffers_crowding_evals  = np.array(schaffers_crowding_evals[0]).astype(np.float)

with open('schaffers_islands_inter_score.pkl', 'rb') as input:
    result_schaffers_islands_array = pickle.load(input)
    schaffers_islands_scores = [param["intermediate_scores"] for param in result_schaffers_islands_array]
    schaffers_islands_scores  = np.array([ np.array(scores).astype(np.float) for scores in schaffers_islands_scores ])
    schaffers_islands_scores = schaffers_islands_scores.mean(axis=0)
    print(np.mean(np.array([param["score"] for param in result_schaffers_islands_array]).astype(np.float)))
    schaffers_islands_evals = [param["eval_number"] for param in result_schaffers_islands_array]
    schaffers_islands_evals  = np.array(schaffers_islands_evals[0]).astype(np.float)


with open('katsuura_benchmark_inter_score.pkl', 'rb') as input:
    result_katsuura_benchmark_array = pickle.load(input)
    katsuura_benchmark_scores = [param["intermediate_scores"] for param in result_katsuura_benchmark_array]
    katsuura_benchmark_scores = np.array([ np.array(scores).astype(np.float) for scores in katsuura_benchmark_scores ])
    katsuura_benchmark_scores = katsuura_benchmark_scores.mean(axis=0)
    print(np.mean(np.array([param["score"] for param in result_katsuura_benchmark_array]).astype(np.float)))
    katsuura_benchmark_evals = [param["eval_number"] for param in result_katsuura_benchmark_array]
    katsuura_benchmark_evals  = np.array(katsuura_benchmark_evals[0]).astype(np.float)


with open('katsuura_crowding_inter_score.pkl', 'rb') as input:
    result_katsuura_crowding_array = pickle.load(input)
    katsuura_crowding_scores = [param["intermediate_scores"] for param in result_katsuura_crowding_array]
    katsuura_crowding_scores  = np.array([ np.array(scores).astype(np.float) for scores in katsuura_crowding_scores ])
    katsuura_crowding_scores = katsuura_crowding_scores.mean(axis=0)
    print(np.mean(np.array([param["score"] for param in result_katsuura_crowding_array]).astype(np.float)))
    katsuura_crowding_evals = [param["eval_number"] for param in result_katsuura_crowding_array]
    katsuura_crowding_evals  = np.array(katsuura_crowding_evals[0]).astype(np.float)

with open('katsuura_islands_inter_score.pkl', 'rb') as input:
    result_katsuura_islands_array = pickle.load(input)
    katsuura_islands_scores = [param["intermediate_scores"] for param in result_katsuura_islands_array]
    katsuura_islands_scores  = np.array([ np.array(scores).astype(np.float) for scores in katsuura_islands_scores ])
    katsuura_islands_scores = katsuura_islands_scores.mean(axis=0)
    print(np.mean(np.array([param["score"] for param in result_katsuura_islands_array]).astype(np.float)))
    katsuura_islands_evals = [param["eval_number"] for param in result_katsuura_islands_array]
    katsuura_islands_evals  = np.array(katsuura_islands_evals[0]).astype(np.float)


fig = plt.figure()
fig.suptitle('Intermediate scores', fontsize=14, fontweight='bold')

ax = fig.add_subplot(131)
ax.plot(bent_cigar_benchmark_evals, bent_cigar_benchmark_scores, color='orange')
ax.plot(bent_cigar_crowding_evals, bent_cigar_crowding_scores, color='green')
ax.plot(bent_cigar_islands_evals, bent_cigar_islands_scores, color='blue')
ax.set_title('Bent Cigar')
ax.set_ylabel('Score')

ax = fig.add_subplot(132)
ax.plot(schaffers_benchmark_evals, schaffers_benchmark_scores, color='orange')
ax.plot(schaffers_crowding_evals, schaffers_crowding_scores, color='green')
ax.plot(schaffers_islands_evals, schaffers_islands_scores, color='blue')
ax.set_title('Schaffers')

ax = fig.add_subplot(133)
ax.plot(katsuura_benchmark_evals, katsuura_benchmark_scores, color='orange')
ax.plot(katsuura_crowding_evals, katsuura_crowding_scores, color='green')
ax.plot(katsuura_islands_evals, katsuura_islands_scores, color='blue')
ax.set_title('Katsuura')


plt.show()
