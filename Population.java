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
    

    public Population(int _size, Random _rnd, ContestEvaluation _evaluation, int _islands, Map<String, Double> _paramMap) {
        expectedPopulationSize = _size;
        evaluation = _evaluation;
	    islands = _islands;
        paramMap = _paramMap;
        individuals = new ArrayList<>();
        rnd = _rnd;
        Properties props = evaluation.getProperties();
        evalsLimit = Integer.parseInt(props.getProperty("Evaluations"));


        // Choose Crossover method: OnePointCrossover, TwoPointCrossover, UniformCrossover or BlendCrossover
        crossover = new Crossover[] {  new OnePointCrossover(),
                                        new TwoPointCrossover(),
                                        new UniformCrossover(),
                                        new BlendCrossover()};

        mutations = new Mutation[] {    new InversionMutation(paramMap.getOrDefault("InversionMutationProbability, 0.5", 0.5)),
                                        new UniformMutation(paramMap.getOrDefault("SimpleMutationProbability", 0.5), paramMap.getOrDefault("SimpleMutationSpeed", 0.5), evalsLimit),
                                        new SwapMutation(paramMap.getOrDefault("SwapMutationProbability", 0.5), paramMap.getOrDefault("SwapMutationNumberOfSwaps", 0.5).intValue()),
                                        new ScrambleMutation(paramMap.getOrDefault("ScrambleMutationProbability", 0.5)) };

        parentSelection = new Selection[] { new RankingSelectionSUS(paramMap.getOrDefault("RankingSelectionSUSMatingPoolSize", 6.0).intValue(), paramMap.getOrDefault("RankingSelectionSUSs", 1.5)),
                                            new TournamentSelection(paramMap.getOrDefault("TournamentSelectionMatingPoolSize", 6.0).intValue(), paramMap.getOrDefault("TournamentSelectionNumberOfParticipiants", 0.5).intValue()),
                                            new UniformParentSelection(paramMap.getOrDefault("UniformParentSelectionMatingPoolSize", 0.5).intValue())};

	    exchange = new Exchange[] {  new ExchangeBest(),
                                     new ExchangeRandom(),
                                     new ExchangePickFromFittestHalf()};

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
        double maxFitness = 0.0;
        for (Individual individual : individuals) {
            if(evals <= evalsLimit) {
                if(individual.getFitness() < 0.0) {
                    double fitness = (double) evaluation.evaluate(individual.getGenome());
                    ++evals;
                    individual.setFitness(fitness);
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

    public void nextGeneration() throws IllegalArgumentException {

        // TODO: create a better way of parent selection instead of just ranking and select the best individuals
        // TODO: have a look at how to pair parents for crossover (e.g. always pair the best ones or pair randomly)

        evaluatePopulation();

        // parent selection
        Collections.sort(individuals);

        // parent selection
        List<Individual> matingPool = parentSelection[parentSelectionMethod].selectIndividuals(individuals, expectedPopulationSize);

        // crossover
        List<Individual> children = new ArrayList<>();
        Collections.sort(matingPool);
        for (int i = 0; i < matingPool.size(); i = i+2) {
            children.addAll(crossover[crossoverMethod].crossover(matingPool.subList(i, i + 2)));
        }

        // select random mutation
        mutations[mutationMethod].mutateIndividuals(children);
	    individuals.addAll(children);
        // before selection update fitness values
        evaluatePopulation();

        // survivor selection
        individuals = selection.selectIndividuals(individuals, expectedPopulationSize);	
    }

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


        // Many different islands, each its own operator/algortihm and see what happens? done, not very elegant though
        List<String> islandAlgorithms = Arrays.asList("no_distinction", "ordered_distinction");

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

        for(int currentIsland = 0; currentIsland< islands; ++currentIsland){

            // islands notes:
            // How long should I evolve the islands?
            // When should I start with mutations?
            // Which indivuals to exchange, random/best, and how many? done

            int migrationSize = paramMap.getOrDefault("migrationSize", 0.5).intValue();
            if (migrationSize >= Math.round(subPopSize/2.0)) {
            	throw new IllegalArgumentException("Amount of individuals to exchange should be lower than half the population");
            }

            // Shall we copy or move individuals between islands?
            
	

            // islands, if input islands == 1 in player.java, it will work the same as without islands

            // compare the subPopulation variable for the population, in order to select each subPopulation
            individuals.sort((c1, c2) -> Integer.compare(c2.subPopulation, c1.subPopulation));


            // Select the current island out of the total population.
            List<Individual> currentIslandPopulation = new ArrayList<>(individuals.subList((currentIsland * subPopSize), (currentIsland * subPopSize + subPopSize - 1)));

            // migration
            if (generation % migrationInterval == 0) {
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

	    //System.out.println(currentIslandPopulation.size());
            // parent selection
            List<Individual> matingPool = parentSelection[parentSelectionMethod].selectIndividuals(currentIslandPopulation, expectedPopulationSize);

            // crossover
            List<Individual> children = new ArrayList<>();
            Collections.sort(matingPool);
            for (int i = 0; i < matingPool.size(); i = i+2) {
                children.addAll(crossover[crossoverMethod].crossover(matingPool.subList(i, i + 2)));
            }

            //children.addAll(crossover.crossover(parents));

            // add subPopulation variable for children, should be the same as the parents
            for (Individual child : children) {
                child.setSubPopulation(currentIsland);
            }

            // select random mutation
            //mutations[rnd.nextInt(mutations.length)].mutateIndividuals(children);
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
            // before selection update fitness values

            // selection within subPopulation
            currentIslandPopulation = selection.selectIndividuals(currentIslandPopulation, subPopSize );

            //System.out.println("After selection");
            //for(Double d : getFitnessList()) {
            //System.out.print(String.format("%.4f ", d));
            //}
            //System.out.println();

            //add the subPopulation to a new Population
            newPopulation.addAll(currentIslandPopulation);
        }
        // remove the old population, and replace it with the new population.
        individuals.clear();
        individuals.addAll(newPopulation);
        evaluatePopulation();
	
    }


    public Individual getFittest() {
        Collections.sort(individuals);
        return individuals.get(0);
    }

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
    
     public int getEvaluationCount() {		
         return evals;		
     }
}
