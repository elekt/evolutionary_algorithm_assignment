import org.vu.contest.ContestEvaluation;

import java.util.*;

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
        mutation = new SimpleMutation(0.5, 0.02);
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
                System.out.println("Individual is null");
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

        List<Individual> parents = individuals.subList(0, 2);
        List<Individual> children = crossover.crossover(parents);
        parents = individuals.subList(0, 2);
        children.addAll(crossover.crossover(parents));
        individuals.addAll(children);

        mutation.mutateIndividuals(individuals, 0.2);

        // before selection update fitness values
        evaluatePopulation();

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
