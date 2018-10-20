import subprocess
import numpy as np
import json
import time
import timeit
import math


counter = 0

def make_process(parameter_dict, evaluation):
    global counter
    params = ""
    for k,v in parameter_dict.items():
        params += "{}:{},".format(k, v)
    params = params[:-1]

    result = subprocess.check_output(['./build_run_for_params.sh', params, evaluation])
    score = 0
    runtime = 0
    intermediate_scores = []
    final_diversity = 0
    mean_diversity = 0
    lines = result.decode().splitlines()
    score_counter = 0
    score_counter_modulo = 100
    for line in lines:
        if line.startswith("Best Score:"):
            score = line.split(":")[1]
        elif line.startswith("Runtime:"):
            runtime = line.split(":")[1].replace("ms", "")
        elif line.startswith("Score:"):
            score_counter += 1
            if score_counter % score_counter_modulo == 0:
                intermediate_scores.append(line.split(":")[1].strip())
        elif line.startswith("Final Diversity:"):
            final_diversity = line.split(":")[1].strip()
        elif line.startswith("Mean Diversity:"):
            mean_diversity = line.split(":")[1].strip()
        else:
            print(line)


    parameter_dict["score"] = score
    parameter_dict["runtime"] = runtime
    parameter_dict["evaluation"] = evaluation
    parameter_dict["intermediate_scores"] = intermediate_scores
    parameter_dict["final_diversity"] = final_diversity
    parameter_dict["mean_diversity"] = mean_diversity
    json.dump(parameter_dict, outfile)
    counter += 1
    outfile.write('\n')


start = timeit.default_timer()
prev_percent = 0
print("{} %".format(int(prev_percent)))
with open("new_params{}.jl".format(int(time.time())), "w") as outfile:
    for evaluation in ["BentCigarFunction", "SchaffersEvaluation"]:
        for PopulationSize in [10, 50, 100]:
                for crossoverMethod in range(0, 4):
                    for mutationMethod in range(0, 2):
                        for MutationProbability in [0.05, 0.5, 0.8]:

                            for MatingPoolSize in [2,   math.ceil(PopulationSize*0.3 / 2.), math.ceil(PopulationSize*0.6 / 2.)]:
                                for NumberOfParticipants in [0.1*PopulationSize, 0.25*PopulationSize]:
                                    for SurvivorSelectionSize in [4, 8, 12]:
                                        if counter >= 4317:
                                            if mutationMethod == 0:
                                                for MutationSpeed in [5.0, 7.5, 10.0]:
                                                    parameter_dict = {
                                                                      "PopulationSize": PopulationSize,
                                                                      "NumberOfParticipants": NumberOfParticipants,
                                                                      "crossoverMethod": crossoverMethod,
                                                                      "mutationMethod": mutationMethod,
                                                                      "MutationProbability": MutationProbability,
                                                                      "MatingPoolSize": MatingPoolSize,
                                                                      "SurvivorSelectionSize": SurvivorSelectionSize,
                                                                      "MutationSpeed": MutationSpeed}
                                                    make_process(parameter_dict, evaluation)
                                            else:
                                                parameter_dict = {
                                                                  "PopulationSize": PopulationSize,
                                                                  "NumberOfParticipants": NumberOfParticipants,
                                                                  "crossoverMethod": crossoverMethod,
                                                                  "mutationMethod": mutationMethod,
                                                                  "SurvivorSelectionSize": SurvivorSelectionSize,
                                                                  "MutationProbability": MutationProbability,
                                                                  "MatingPoolSize": MatingPoolSize,
                                                                  "MutationSpeed": 1.0}
                                                make_process(parameter_dict, evaluation)


                                    percent = counter / 1296.0 * 100
                                    if int(percent) > prev_percent:
                                        prev_percent = int(percent)
                                        print("{} %".format(int(prev_percent)))

stop = timeit.default_timer()
print(counter)
print('Runime: {} sec'.format(stop - start))
