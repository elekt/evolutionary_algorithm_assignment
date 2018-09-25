import os


files = os.listdir(os.getcwd())

for item in files:
    if os.path.isfile(item):
        if item.endswith(".class") and item not in ['KatsuuraEvaluation.class', 'SchaffersEvaluation.class', 'BentCigarFunction.class', 'SphereEvaluation.class']:
            os.remove(os.path.join(os.getcwd(), item))
        if item == "submission.jar":
            os.remove(os.path.join(os.getcwd(), item))