import subprocess
import numpy as np

# "InversionMutationProbability: 0.0 - 1.0
# "SimpleMutationProbability: 0.0 - 1.0
# "SimpleMutationSpeed: 0.0 - 2.5
# "SwapMutationProbability: 0.0 - 1.0
# "SwapMutationNumberOfSwaps: 0 - 5
# "ScrambleMutationProbability: 0.0 - 1.0
# "RankingSelectionSUSMatingPoolSize: 0 - 6 % 2
# "RankingSelectionSUSs: 1.0001 - 2.0
# "TournamentSelectionMatingPoolSize: 2 - 8 % 2
# "TournamentSelectionNumberOfParticipiants: 1 - 8
# "UniformParentSelectionMatingPoolSize: 2 - 8 % 2
# crossoverMethod 0 - 3
# mutationMethod 0 - 3
# parentSelectionMethod 0 - 2
# exchangeMethod 0 - 2

def generateParentselectionAndParams(evaluation,InversionMutationProbability,SimpleMutationProbability,
                                     SimpleMutationSpeed,SwapMutationProbability,SwapMutationNumberOfSwaps,
                                     ScrambleMutationProbability,crossoverMethod,mutationMethod,exchangeMethod):
    for parentSelectionMethod in range(0, 3):
        RankingSelectionSUSMatingPoolSize = 0
        RankingSelectionSUSs = 0
        TournamentSelectionMatingPoolSize = 0
        TournamentSelectionNumberOfParticipiants = 0
        UniformParentSelectionMatingPoolSize = 0

        if parentSelectionMethod == 0:
            # "RankingSelectionSUSMatingPoolSize: 0 - 6 % 2
            # "RankingSelectionSUSs: 1.0 - 2.0
            for ps in range(0, 7, 2):
                RankingSelectionSUSMatingPoolSize = ps
                for s in np.arange(1.0, 2.0, 0.05):
                    RankingSelectionSUSs = s
                    params = "InversionMutationProbability:{},SimpleMutationProbability:{},SimpleMutationSpeed:{},SwapMutationProbability:{},SwapMutationNumberOfSwaps:{},ScrambleMutationProbability:{},RankingSelectionSUSMatingPoolSize:{},RankingSelectionSUSs:{},TournamentSelectionMatingPoolSize:{},TournamentSelectionNumberOfParticipiants:{},UniformParentSelectionMatingPoolSize:{},parentSelectionMethod:{},crossoverMethod:{},mutationMethod:{},exchangeMethod:{}".format(InversionMutationProbability,SimpleMutationProbability,SimpleMutationSpeed,SwapMutationProbability,SwapMutationNumberOfSwaps,ScrambleMutationProbability,RankingSelectionSUSMatingPoolSize,RankingSelectionSUSs,TournamentSelectionMatingPoolSize,TournamentSelectionNumberOfParticipiants,UniformParentSelectionMatingPoolSize,parentSelectionMethod,crossoverMethod,mutationMethod,exchangeMethod)
                    print(params)
        elif parentSelectionMethod == 1:
            # "TournamentSelectionMatingPoolSize: 2 - 8 % 2
            # "TournamentSelectionNumberOfParticipiants: 1 - 8
            for ps in range(2, 9, 2):
                TournamentSelectionMatingPoolSize = ps
                for s in range(1, 9):
                    TournamentSelectionNumberOfParticipiants = s
                    params = "InversionMutationProbability:{},SimpleMutationProbability:{},SimpleMutationSpeed:{},SwapMutationProbability:{},SwapMutationNumberOfSwaps:{},ScrambleMutationProbability:{},RankingSelectionSUSMatingPoolSize:{},RankingSelectionSUSs:{},TournamentSelectionMatingPoolSize:{},TournamentSelectionNumberOfParticipiants:{},UniformParentSelectionMatingPoolSize:{},parentSelectionMethod:{},crossoverMethod:{},mutationMethod:{},exchangeMethod:{}".format(InversionMutationProbability,SimpleMutationProbability,SimpleMutationSpeed,SwapMutationProbability,SwapMutationNumberOfSwaps,ScrambleMutationProbability,RankingSelectionSUSMatingPoolSize,RankingSelectionSUSs,TournamentSelectionMatingPoolSize,TournamentSelectionNumberOfParticipiants,UniformParentSelectionMatingPoolSize,parentSelectionMethod,crossoverMethod,mutationMethod,exchangeMethod)
                    print(params)
        elif parentSelectionMethod == 2:
            # "UniformParentSelectionMatingPoolSize: 2 - 8 % 2
            for ps in range(2, 9, 2):
                UniformParentSelectionMatingPoolSize = ps
                params = "InversionMutationProbability:{},SimpleMutationProbability:{},SimpleMutationSpeed:{},SwapMutationProbability:{},SwapMutationNumberOfSwaps:{},ScrambleMutationProbability:{},RankingSelectionSUSMatingPoolSize:{},RankingSelectionSUSs:{},TournamentSelectionMatingPoolSize:{},TournamentSelectionNumberOfParticipiants:{},UniformParentSelectionMatingPoolSize:{},parentSelectionMethod:{},crossoverMethod:{},mutationMethod:{},exchangeMethod:{}".format(InversionMutationProbability,SimpleMutationProbability,SimpleMutationSpeed,SwapMutationProbability,SwapMutationNumberOfSwaps,ScrambleMutationProbability,RankingSelectionSUSMatingPoolSize,RankingSelectionSUSs,TournamentSelectionMatingPoolSize,TournamentSelectionNumberOfParticipiants,UniformParentSelectionMatingPoolSize,parentSelectionMethod,crossoverMethod,mutationMethod,exchangeMethod)
                print(params)
        else:
            print("Invalid mutation method")

        # result = subprocess.check_output(['/home/redmachine/Desktop/Uva-DataScience/EvolutionaryComputing/assignmentfiles_2017/build_run_for_params.sh', params, evaluation])
        # print(result)

for evaluation in ["SchaffersEvaluation", "BentCigarFunction", "KatsuuraEvaluation"]:
    for crossoverMethod in range(0, 4):
        for exchangeMethod in range(0, 3):
            for mutationMethod in range(0, 4):
                InversionMutationProbability = 0
                SimpleMutationProbability = 0
                SimpleMutationSpeed = 0
                SwapMutationProbability = 0
                SwapMutationNumberOfSwaps = 0
                ScrambleMutationProbability = 0

                if mutationMethod == 0:
                    # "InversionMutationProbability: 0.0 - 1.0
                    for p in np.arange(0.0, 1.05, 0.05):
                        InversionMutationProbability = p
                        generateParentselectionAndParams(evaluation, InversionMutationProbability,SimpleMutationProbability,
                                                         SimpleMutationSpeed,SwapMutationProbability,SwapMutationNumberOfSwaps,
                                                         ScrambleMutationProbability,crossoverMethod,mutationMethod,exchangeMethod)
                elif mutationMethod == 1:
                    # "SimpleMutationProbability: 0.0 - 1.0
                    # "SimpleMutationSpeed: 0.0 - 2.5
                    for p in np.arange(0.0, 1.05, 0.05):
                        SimpleMutationProbability = p
                        for s in np.arange(0.0, 1.0, 0.05):
                            SimpleMutationSpeed = s
                            generateParentselectionAndParams(evaluation, InversionMutationProbability,SimpleMutationProbability,
                                                             SimpleMutationSpeed,SwapMutationProbability,SwapMutationNumberOfSwaps,
                                                             ScrambleMutationProbability,crossoverMethod,mutationMethod,exchangeMethod)
                elif mutationMethod == 2:
                    # "SwapMutationProbability: 0.0 - 1.0
                    # "SwapMutationNumberOfSwaps: 1 - 5
                    for p in np.arange(0.0, 1.05, 0.05):
                        SwapMutationProbability = p
                        for s in range(1, 6):
                            SwapMutationNumberOfSwaps = s
                            generateParentselectionAndParams(evaluation, InversionMutationProbability,SimpleMutationProbability,
                                                             SimpleMutationSpeed,SwapMutationProbability,SwapMutationNumberOfSwaps,
                                                             ScrambleMutationProbability,crossoverMethod,mutationMethod,exchangeMethod)
                elif mutationMethod == 3:
                    # "ScrambleMutationProbability: 0.0 - 1.0
                    for p in np.arange(0.0, 1.05, 0.05):
                        ScrambleMutationProbability = p
                        generateParentselectionAndParams(evaluation, InversionMutationProbability,SimpleMutationProbability,
                                                         SimpleMutationSpeed,SwapMutationProbability,SwapMutationNumberOfSwaps,
                                                         ScrambleMutationProbability,crossoverMethod,mutationMethod,exchangeMethod)
                else:
                    print("Invalid mutation method")
