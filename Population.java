import org.vu.contest.ContestEvaluation;

import java.util.*;
import java.lang.Math; 

public class Population {
    private List<Individual> individuals;
    private int expectedPopulationSize;
    private ContestEvaluation evaluation;
    private Random rnd;
    private Crossover crossover;
    private Mutation mutation;
    private Selection selection;

    public Population(int _size, Random _rnd, ContestEvaluation _evaluation) {
        expectedPopulationSize = _size;
        evaluation = _evaluation;
        individuals = new ArrayList<>();
        rnd = _rnd;

        crossover = new SimpleCrossover();
        mutation = new SimpleMutation(0.0, 0.0);
        selection = new SimpleSelection();

        for(int i = 0; i< expectedPopulationSize; ++i){
            individuals.add(new Individual(rnd));
        }
    }

    public double evaluatePopulation() {
        double maxFitness = 0.0;
        for (Individual individual : individuals) {
            double fitness = (double) evaluation.evaluate(individual.getGenome());
            individual.setFitness(fitness);
            if (fitness > maxFitness) {
                maxFitness = fitness;
            } else {
                //System.out.println("Individual is null");
            }
        }
        return maxFitness;
    }

    public void nextGeneration() throws IllegalArgumentException {

        // TODO: create a better way of parent selection instead of just ranking and select the best individuals
        // TODO: have a look at how to pair parents for crossover (e.g. always pair the best ones or pair randomly)
        // TODO: fix the nullpointer exception caused by the extra evaluations

        // parent selection


        Collections.sort(individuals);
	List<Individual> parents = new ArrayList<>();
	//System.out.println(parents);
	double populationProbability = 0.0;
	//System.out.println("Individual fitness");
	for (Individual individual : individuals) {
		//System.out.println(individual.getFitness());
		populationProbability += individual.getFitness();
	}
	
	double MatingPool = 2
	double p1 = Math.random() * populationProbability/MatingPool;
	double p2 = populationProbability;
	double cumulativeProbability = 0.0;
	

	for (Individual individual : individuals) {
		cumulativeProbability += individual.getFitness();
		 if (p1 <= cumulativeProbability) {
			parents.add(individual);
			p2 = p1 + (1/expectedPopulationSize * populationProbability);
		} 
		if (p2 <= cumulativeProbability) {
			parents.add(individual);
			break;
			
    		} 
	}
	

            
	// Repeated crossover, adding half the population from 2 parents
	List<Individual> children = crossover.crossover(parents);
	int crossover_amount = Math.round(expectedPopulationSize/4);	
	for (int i = 0; i < crossover_amount; i++) { 
		children.addAll(crossover.crossover(parents));
	} 

        //List<Individual> parents = individuals.subList(0, 2);
        //List<Individual> children = crossover.crossover(parents);
        //parents = individuals.subList(0, 2);
        //children.addAll(crossover.crossover(parents));
        individuals.addAll(children);
        mutation.mutateIndividuals(individuals, 0.0);

        // before selection update fitness values
        evaluatePopulation();
	System.out.println(evaluatePopulation());

        // selection
        individuals = selection.selectIndividuals(individuals, expectedPopulationSize);
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
}
