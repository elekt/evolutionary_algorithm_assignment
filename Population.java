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

        mutations = new Mutation[] {    new InversionMutation(paramMap.get("InversionMutationProbability")),
                                        new SimpleMutation(paramMap.get("SimpleMutationProbability"), paramMap.get("SimpleMutationSpeed"), evalsLimit),
                                        new SwapMutation(paramMap.get("SwapMutationProbability"), paramMap.get("SwapMutationNumberOfSwaps").intValue()),
                                        new ScrambleMutation(paramMap.get("ScrambleMutationProbability")) };

        parentSelection = new Selection[] { new RankingSelectionSUS(paramMap.get("RankingSelectionSUSMatingPoolSize").intValue(), paramMap.get("RankingSelectionSUSs")),
                                            new TournamentSelection(paramMap.get("TournamentSelectionMatingPoolSize").intValue(), paramMap.get("TournamentSelectionNumberOfParticipiants").intValue()),
                                            new UniformParentSelection(paramMap.get("UniformParentSelectionMatingPoolSize").intValue())};

	    exchange = new Exchange[] {  new ExchangeBest(),
                                     new ExchangeRandom(),
                                     new ExchangePickFromFittestHalf()};

        parentSelectionMethod = paramMap.get("parentSelectionMethod").intValue();
        crossoverMethod = paramMap.get("crossoverMethod").intValue();
	    mutationMethod = paramMap.get("mutationMethod").intValue();
        exchangeMethod = paramMap.get("exchangeMethod").intValue();

        //selection = new SimpleSelection();
	    selection = new RoundRobinSelection();
	
	
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

	    List<Individual> parents = parentSelection[parentSelectionMethod].selectIndividuals(individuals, expectedPopulationSize);
	
        // parent selection
        Collections.sort(individuals);

        //List<Individual> parents = individuals.subList(0, 2);
        List<Individual> children = crossover[crossoverMethod].crossover(parents);
        
        // select random mutation
        mutations[rnd.nextInt(mutations.length)].mutateIndividuals(children);
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

        int exchangeRate = 200;


	// Many different islands, each its own operator/algortihm and see what happens? done, not very elegant though
	List<String> islandAlgorithms = Arrays.asList("no_distinction", "ordered_distinction", "random_distinction");

        String islandAlgorithm = islandAlgorithms.get(1);

        int[] parentSelectionMethodList = new int[islands];
        int[] crossoverMethodList = new int[islands];
        int[] mutationMethodList = new int[islands];

        if (islandAlgorithm == "random_distinction"){

            for(int currentIsland = 0; currentIsland< islands; ++currentIsland){
                parentSelectionMethodList[currentIsland] = rnd.nextInt(parentSelection.length);
                crossoverMethodList[currentIsland] = rnd.nextInt(crossover.length);
                mutationMethodList[currentIsland] = rnd.nextInt(mutations.length);
            }
            
        } else if (islandAlgorithm == "ordered_distinction"){
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
            
            int amountIndivExchange = 2; 
            if (amountIndivExchange >= Math.round(subPopSize/2)) {
            	throw new IllegalArgumentException("Amount of individuals to exchange should be lower than half the population");
            }

            // Shall we copy or move individuals between islands?
            
	

            // islands, if input islands == 1 in player.java, it will work the same as without islands

            // compare the subPopulation variable for the population, in order to select each subPopulation
            individuals.sort((c1, c2) -> Integer.compare(c2.subPopulation, c1.subPopulation));


            // Select the current island out of the total population.
            List<Individual> currentIslandPopulation = new ArrayList<>(individuals.subList((currentIsland * subPopSize), (currentIsland * subPopSize + subPopSize - 1)));

            // migration
            if (generation % exchangeRate == 0) {
                // get left and right subPopulations if considered a torus

                int leftIsland = ( ((currentIsland + (islands - 2))  % (islands - 1)) );
                int rightIsland =  ( (currentIsland + 1) % islands % (islands - 1) );
                leftIslandPopulation.addAll(individuals.subList((leftIsland*subPopSize),(leftIsland*subPopSize+subPopSize-1)));
                rightIslandPopulation.addAll(individuals.subList((rightIsland*subPopSize),(rightIsland*subPopSize+subPopSize-1)));

		currentIslandPopulation = exchange[exchangeMethod].exchange(currentIslandPopulation, leftIslandPopulation, rightIslandPopulation, amountIndivExchange);
                   
                leftIslandPopulation.clear();
                rightIslandPopulation.clear();
            }

	    if (islandAlgorithm != "no_distinction"){
                parentSelectionMethod = parentSelectionMethodList[currentIsland];
		
                crossoverMethod = crossoverMethodList[currentIsland];
                mutationMethod = mutationMethodList[currentIsland];
            } else {
                parentSelectionMethod = rnd.nextInt(parentSelection.length);
                crossoverMethod = rnd.nextInt(crossover.length);
                mutationMethod = rnd.nextInt(mutations.length);
            }
	    //parentSelectionMethod 0 does not work yet:
	    parentSelectionMethod = 1;

            // From this point onwards, the subpopulation is used for the methods
            Collections.sort(currentIslandPopulation);

	    //System.out.println(currentIslandPopulation.size());
            List<Individual> parents = parentSelection[parentSelectionMethod].selectIndividuals(currentIslandPopulation, expectedPopulationSize);
            List<Individual> children = crossover[crossoverMethod].crossover(parents);

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