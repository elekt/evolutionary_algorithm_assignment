import org.vu.contest.ContestEvaluation;

import java.util.*;

public class Population {
    private List<Individual> individuals;
    private int expectedPopulationSize;
    private ContestEvaluation evaluation;
    private Random rnd;
    private Crossover crossover;
    private Mutation[] mutations;
    private Selection[] parentSelection;
    private Selection selection;
    static int evals;
    private int evalsLimit;
    private int islands;
    

    public Population(int _size, Random _rnd, ContestEvaluation _evaluation, int _islands) {
        expectedPopulationSize = _size;
        evaluation = _evaluation;
	    islands = _islands;
        individuals = new ArrayList<>();
        rnd = _rnd;
        Properties props = evaluation.getProperties();
        evalsLimit = Integer.parseInt(props.getProperty("Evaluations"));

        // Choose Crossover method: OnePointCrossover, TwoPointCrossover, UniformCrossover or BlendCrossover
        crossover = new TwoPointCrossover(); // OnePoint is default

        mutations = new Mutation[] {    new InversionMutation(0.6),
                                        new SimpleMutation(0.6, 1.5, evalsLimit),
                                        new SwapMutation(0.6, 2),
                                        new ScrambleMutation(0.6) };
	    parentSelection = new Selection[] { new RankingSelectionSUS(2, 1.3),
                                            new TournamentSelection(2, 4),
                                            new UniformParentSelection(2)};
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

	    List<Individual> parents = parentSelection[2].selectIndividuals(individuals, expectedPopulationSize);
	
        // parent selection
        Collections.sort(individuals);

        //List<Individual> parents = individuals.subList(0, 2);
        List<Individual> children = crossover.crossover(parents);
        
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
        List<Individual> leftPopulation = new ArrayList<>();
        List<Individual> rightPopulation = new ArrayList<>();

        int exchangeRate = 200;

        for(int i = 0; i< islands; ++i){

            // islands notes:
            // How long should I evolve the islands?
            // When should I start with mutations?
            // Which indivuals to exchange, random/best, and how many?
            // Shall we copy or move individuals between islands?
            // Many different islands, each its own operator/algortihm and see what happens?

            // islands, if input islands == 1 in player.java, it will work the same as without islands

            // compare the subPopulation variable for the population, in order to select each subPopulation
            individuals.sort((c1, c2) -> Integer.compare(c2.subPopulation, c1.subPopulation));


            // Select the current island out of the total population.
            List<Individual> subPopulation = new ArrayList<>(individuals.subList((i * subPopSize), (i * subPopSize + subPopSize - 1)));

            // migration
            if (generation % exchangeRate == 0) {
                // get left and right subPopulations if considered a torus

                int left = ( ((i + (islands - 2))  % (islands - 1)) );
                int right =  ( (i + 1) % islands % (islands - 1) );
                leftPopulation.addAll(individuals.subList((left*subPopSize),(left*subPopSize+subPopSize-1)));
                rightPopulation.addAll(individuals.subList((left*subPopSize),(left*subPopSize+subPopSize-1)));

                // add the fittest of the left and right population to subPopulation and clear the populations
                Collections.sort(leftPopulation);
                Collections.sort(rightPopulation);
                subPopulation.addAll(rightPopulation.subList(0, 1));
                subPopulation.addAll(leftPopulation.subList(0, 1));
                leftPopulation.clear();
                rightPopulation.clear();
            }

            // From this point onwards, the subpopulation is used for the methods
            Collections.sort(subPopulation);

            List<Individual> parents = parentSelection[2].selectIndividuals(individuals, expectedPopulationSize);
            List<Individual> children = crossover.crossover(parents);

            //children.addAll(crossover.crossover(parents));

            // add subPopulation variable for children, should be the same as the parents
            for (Individual child : children) {
                child.setSubPopulation(parents.get(0).subPopulation);
            }

            // select random mutation
            //mutations[rnd.nextInt(mutations.length)].mutateIndividuals(children);
            mutations[rnd.nextInt(mutations.length)].mutateIndividuals(children);
            subPopulation.addAll(children);
            // before selection update fitness values

            //evaluate subPopulation:
            for (Individual individual : subPopulation) {
                if(evals <= evalsLimit) {
                    if(individual.getFitness() < 0.0) {
                        double fitness = (double) evaluation.evaluate(individual.getGenome());
                        ++evals;
                        individual.setFitness(fitness);
                    }
                }
            }

            // selection within subPopulation
            subPopulation = selection.selectIndividuals(subPopulation, subPopSize );

            //add the subPopulation to a new Population
            newPopulation.addAll(subPopulation);
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