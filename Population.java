import org.vu.contest.ContestEvaluation;

import java.util.*;

public class Population {
    private int crossoverMethod;
    private int mutationMethod;
    private List<Individual> individuals;
    private int expectedPopulationSize;
    private ContestEvaluation evaluation;
    private Random rnd;
    private Crossover[] crossover;
    private Mutation[] mutations;
    private Selection[] parentSelection;
    private int parentSelectionMethod;
    private int exchangeMethod;
    private Selection selection;
    public static int evals;
    private int evalsLimit;
    private int islands;
    private Exchange[] exchange;
    private Map<String, Double> paramMap;
    private Crowding crowding;

    public Population(int _size, Random _rnd, ContestEvaluation _evaluation, int _islands, Map<String, Double> _paramMap) {
        expectedPopulationSize = _size;
        evaluation = _evaluation;
	    islands = _islands;
        paramMap = _paramMap;
        individuals = new ArrayList<>();
        rnd = _rnd;
        Properties props = evaluation.getProperties();
        evalsLimit = Integer.parseInt(props.getProperty("Evaluations"));

        crossover = new Crossover[] {  new OnePointCrossover(),
                                        new TwoPointCrossover(),
                                        new UniformCrossover(),
                                        new BlendCrossover(),
                                        new WholeArithmeticCrossover(0.51)};

        mutations = new Mutation[] {    new InversionMutation(paramMap.getOrDefault("InversionMutationProbability, 0.5", 0.5)),
                                        new UniformMutation(paramMap.getOrDefault("UniformMutationProbability", 0.5), paramMap.getOrDefault("UniformMutationSpeed", 0.5), evalsLimit),
                                        new SwapMutation(paramMap.getOrDefault("SwapMutationProbability", 0.5), paramMap.getOrDefault("SwapMutationNumberOfSwaps", 0.5).intValue()),
                                        new ScrambleMutation(paramMap.getOrDefault("ScrambleMutationProbability", 0.5)) };

        parentSelection = new Selection[] { new RankingSelectionSUS(paramMap.getOrDefault("RankingSelectionSUSMatingPoolSize", 6.0).intValue(), paramMap.getOrDefault("RankingSelectionSUSs", 1.5)),
                                            new TournamentSelection(paramMap.getOrDefault("TournamentSelectionMatingPoolSize", 6.0).intValue(), paramMap.getOrDefault("TournamentSelectionNumberOfParticipiants", 0.5).intValue()),
                                            new UniformParentSelection(paramMap.getOrDefault("UniformParentSelectionMatingPoolSize", 0.5).intValue())};

	    exchange = new Exchange[] {  new ExchangeBest(),
                                     new ExchangeRandom(),
                                     new ExchangePickFromFittestHalf()};

	    crowding = new Crowding();

        parentSelectionMethod = paramMap.get("parentSelectionMethod").intValue();
        crossoverMethod = paramMap.get("crossoverMethod").intValue();
	    mutationMethod = paramMap.get("mutationMethod").intValue();
        exchangeMethod = paramMap.get("exchangeMethod").intValue();

	    selection = new RoundRobinSelection(paramMap.getOrDefault("roundRobinTournamentSurvivorSelectionSize", 0.5).intValue());
	
	
        for(int i = 0; i< expectedPopulationSize; ++i){
	        int subPopulation = i % islands;
	        individuals.add(new Individual(rnd, subPopulation));
        }
        evals = 0;
    }

    public double evaluatePopulation() {
        return evaluateIndividuals(individuals);
    }

    // assign fitness to individuals
    public double evaluateIndividuals(List<Individual> individualsToEvaluate) {
        double maxFitness = 0.0;
        for (Individual individual : individualsToEvaluate) {
            if(evals <= evalsLimit) {
                if(individual.getFitness() < 0.0) {
                    double fitness = (double) evaluation.evaluate(individual.getGenome());
                    ++evals;
                    individual.setFitness(fitness);
                    if (fitness > maxFitness) {
                        maxFitness = fitness;
                    }
                } else {
                    double fitness = individual.getFitness();
                    if (fitness > maxFitness) {
                        maxFitness = fitness;
                    }
                }
            } else {
                System.out.println("Run out eval cycles");
                break;
            }
        }
        return maxFitness;
    }

    // execute normal next generation with crowding if only one island
    public void nextGeneration() throws IllegalArgumentException {

        evaluatePopulation();

        // parent selection
        List<Individual> matingPool = parentSelection[parentSelectionMethod].selectIndividuals(individuals, expectedPopulationSize);

        // crossover
        Collections.sort(matingPool);
        for (int i = 0; i < matingPool.size(); i = i+2) {
            List<Individual> parents = matingPool.subList(i, i + 2);
            List<Individual> children = crossover[crossoverMethod].crossover(parents);

            // crowding
            individuals.removeAll(parents);
            mutations[mutationMethod].mutateIndividuals(children);
            evaluateIndividuals(children);
            List<Individual> crowdedIndividuals = crowding.crowd(parents, children);
            individuals.addAll(crowdedIndividuals);
        }

        // before selection update fitness values
        evaluatePopulation();

        // survivor selection
        individuals = selection.selectIndividuals(individuals, expectedPopulationSize);	
    }

    // execute next generation in all existing islands
	public void nextGenerationIslands(int generation) throws IllegalArgumentException {

        // for amount of islands, make subPopulations of the total population, and do
        // parent selection, crossover, mutation and survivor selection
        // on this subPopulation. After these methods, the subPopulations are added to a new population,
        // which will be used for the next Generation.

        evaluatePopulation();

        int subPopSize = (expectedPopulationSize/islands);
        List<Individual> newPopulation = new ArrayList<>();
        List<Individual> leftIslandPopulation = new ArrayList<>();
        List<Individual> rightIslandPopulation = new ArrayList<>();


        int migrationInterval = paramMap.get("migrationInterval").intValue();

        // no_distinction: all islands share same operator/algortihm
        // ordered_distinction: each island its own operator/algortihm
        List<String> islandAlgorithms = Arrays.asList("no_distinction", "ordered_distinction");

        // choose which method to use: NOW SET TO NO_DISTINCTION
        String islandAlgorithm = islandAlgorithms.get(0);

        int[] parentSelectionMethodList = new int[islands];
        int[] crossoverMethodList = new int[islands];
        int[] mutationMethodList = new int[islands];

       if (islandAlgorithm == "ordered_distinction"){
            int count = 0;
            int parentSelectionCount = 0;
            int crossoverCount = 0;
            int mutationCount = 0;

            while (count < islands) { 
                if (count % (crossover.length * mutations.length) == 0) {
                    parentSelectionCount = (parentSelectionCount + 1 ) % parentSelection.length;
                }
                if (count % (mutations.length) == 0) {
                    crossoverCount = (crossoverCount + 1 ) % crossover.length;
                }
                mutationCount = (mutationCount + 1) % mutations.length;

                parentSelectionMethodList[count] = parentSelectionCount;
                crossoverMethodList[count] = crossoverCount;
                mutationMethodList[count] = mutationCount;
                ++count;
            }
        }
        Properties props = evaluation.getProperties();
        boolean isMultimodal = Boolean.parseBoolean(props.getProperty("Multimodal"));
        boolean hasStructure = Boolean.parseBoolean(props.getProperty("Regular"));

        for(int currentIsland = 0; currentIsland< islands; ++currentIsland){

            int migrationSize = paramMap.getOrDefault("migrationSize", 0.5).intValue();
            if (migrationSize >= Math.round(subPopSize/2.0)) {
            	throw new IllegalArgumentException("Amount of individuals to exchange should be lower than half the population");
            }

            // compare the subPopulation variable for the population, in order to select each subPopulation
            individuals.sort((c1, c2) -> Integer.compare(c2.subPopulation, c1.subPopulation));

            // Select the current island out of the total population.
            List<Individual> currentIslandPopulation = new ArrayList<>(individuals.subList((currentIsland * subPopSize), (currentIsland * subPopSize + subPopSize - 1)));

            // migration
            int migrationStart = 2000;
                if ((generation % migrationInterval == 0) & ((isMultimodal & !hasStructure & generation > migrationStart))) {

                    // get left and right subPopulations if considered a torus
                    int leftIsland = ( ((currentIsland + (islands - 2))  % (islands - 1)) );
                    int rightIsland =  ( (currentIsland + 1) % islands % (islands - 1) );
                    leftIslandPopulation.addAll(individuals.subList((leftIsland*subPopSize),(leftIsland*subPopSize+subPopSize-1)));
                    rightIslandPopulation.addAll(individuals.subList((rightIsland*subPopSize),(rightIsland*subPopSize+subPopSize-1)));

                    currentIslandPopulation = exchange[exchangeMethod].exchange(currentIslandPopulation, leftIslandPopulation, rightIslandPopulation, migrationSize);

                    leftIslandPopulation.clear();
                    rightIslandPopulation.clear();
                }

            if (!islandAlgorithm.equals("no_distinction")){
                parentSelectionMethod = parentSelectionMethodList[currentIsland];
                crossoverMethod = crossoverMethodList[currentIsland];
                mutationMethod = mutationMethodList[currentIsland];
            } else {
                parentSelectionMethod = paramMap.getOrDefault("parentSelectionMethod", 0.5).intValue();
                crossoverMethod = paramMap.getOrDefault("crossoverMethod", 0.5).intValue();
                mutationMethod = paramMap.getOrDefault("mutationMethod", 0.5).intValue();
            }

            // From this point onwards, the subpopulation is used for the methods
            Collections.sort(currentIslandPopulation);

            // parent selection
            List<Individual> matingPool = parentSelection[parentSelectionMethod].selectIndividuals(currentIslandPopulation, expectedPopulationSize);

            // crossover
            List<Individual> children = new ArrayList<>();
            Collections.sort(matingPool);
            for (int i = 0; i < matingPool.size(); i = i+2) {
                children.addAll(crossover[crossoverMethod].crossover(matingPool.subList(i, i + 2)));
            }

            // add subPopulation variable for children, should be the same as the parents
            for (Individual child : children) {
                child.setSubPopulation(currentIsland);
            }

            // mutation
            mutations[mutationMethod].mutateIndividuals(children);

    	    //evaluate subPopulation:
            for (Individual individual : children) {
                if(evals <= evalsLimit) {
                    if(individual.getFitness() < 0.0) {
                        double fitness = (double) evaluation.evaluate(individual.getGenome());
                        ++evals;
                        individual.setFitness(fitness);
                    }
                }
            }

            currentIslandPopulation.addAll(children);

            // selection within subPopulation
            currentIslandPopulation = selection.selectIndividuals(currentIslandPopulation, subPopSize );

            //add the subPopulation to a new Population
            newPopulation.addAll(currentIslandPopulation);
        }

        // remove the old population, and replace it with the new population.
        individuals.clear();
        individuals.addAll(newPopulation);
        evaluatePopulation();
    }

    // get fittest individual of population
    public Individual getFittest() {
        Collections.sort(individuals);
        return individuals.get(0);
    }

    // get population size
    public int getPopulationSize() {
        return individuals.size();
    }

    private double[] getFitnessList() {
        double[] ret = new double[individuals.size()];

        for(int i = 0; i<individuals.size(); ++i){
            ret[i] = individuals.get(i).getFitness();
        }

        return ret;
    }

    // get current diversity of the population
    public double getDiversity() {
        double maxFitness = 0.0;
        double score = 0.0;
        Individual fittest = getFittest();
        double[] fittestGenome = fittest.getGenome();
        double[] compareGenome;
	    for (Individual individual : individuals) {
            compareGenome = individual.getGenome();
            for(int i=0; i<10; ++i){
                score += Math.abs(compareGenome[i] - fittestGenome[i]);
            }
        }
        return (score/getPopulationSize());
	}
    
     public int getEvaluationCount() {		
         return evals;		
     }
}

