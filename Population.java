import org.vu.contest.ContestEvaluation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Population {
    private List<Individual> individuals;
    private int population_size;
    private Random rnd;

    public Population(int _size, Random _rnd) {
        population_size = _size;
        individuals = new ArrayList<>();
        rnd = _rnd;

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

    private List<Individual> crossover() {
        // select fittest parents
        Collections.sort(individuals);

        List<Individual> parents = individuals.subList(0, 4);

        // TODO do the crossover X
        List<Individual> children = new ArrayList<>();
        double[] firstChild = new double[10];
        double[] secondChild = new double[10];
        for (int i = 0; i < 10; i++) {
            firstChild[i] = (parents.get(0).getGenome()[i] + parents.get(1).getGenome()[i]) / 2.0;
            secondChild[i] = (parents.get(2).getGenome()[i] + parents.get(3).getGenome()[i]) / 2.0;
        }
        children.add(new Individual(firstChild));
        children.add(new Individual(secondChild));

        return children;
    }

    private List<Individual> mutation(List<Individual> entitiesToMutate) {
        // TODO with some probability do mutation

        return entitiesToMutate;
    }

    public void nextGeneration() {
        List<Individual> newEntities = crossover();
        mutation(newEntities);

        individuals = individuals.subList(0, individuals.size() - newEntities.size());
        individuals.addAll(newEntities);

        System.out.println("Size of population: " + getPopulationSize());
    }

    public Individual getFittest() {
        Collections.sort(individuals);
        return individuals.get(0);
    }

    public int getPopulationSize() {
        return individuals.size();
    }
}
