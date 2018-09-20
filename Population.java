import org.vu.contest.ContestEvaluation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Population {
    private List<Individual> individuals;
    private int population_size;
    private Random rnd;
    private Crossover crossover;
    private Mutation mutation;

    public Population(int _size, Random _rnd) {
        population_size = _size;
        individuals = new ArrayList<>();
        rnd = _rnd;

        crossover = new DummyCrossover();
        mutation = new DummyMutation();

        for(int i=0; i<population_size; ++i){
            individuals.add(new Individual(rnd));
        }
    }

    public double evaluatePopulation(ContestEvaluation evaluation) {
        double maxFitness = 0.0;

        for (Individual individual : individuals) {
            double fitness = (double) evaluation.evaluate(individual.getGenome());
            individual.setFitness(fitness);
            if(fitness > maxFitness) {
                maxFitness = fitness;
            }
        }
        return maxFitness;
    }

    public void nextGeneration() throws IllegalArgumentException {

        // TODO: create a better way of parent selection instead of just ranking and select the best individuals
        Collections.sort(individuals);

        // TODO: have a look at how to pair parents for crossover (e.g. always pair the best ones or pair randomly)
        List<Individual> children = crossover.crossover(individuals.subList(0, 2));


        // TODO: create a way children will be (randomly) selected for mutation
        Individual mutatedChild = mutation.mutate(children.get(0));

        // TODO: update population by adding the new individuals and remove (worst?) old ones
    }

    public Individual getFittest() {
        Collections.sort(individuals);
        return individuals.get(0);
    }

    public int getPopulationSize() {
        return individuals.size();
    }
}
