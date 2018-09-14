import org.vu.contest.ContestEvaluation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Population {
    private List<Entity> entities;
    private int population_size;
    private Random random;

    public Population(int _size, Random _rnd) {
        population_size = _size;
        entities = new ArrayList<>();
        random = _rnd;

        for(int i=0; i<population_size; ++i){
            entities.add(new Entity(random));
        }
    }

    public Double evaluatePopulation(ContestEvaluation evaluation) {
        Double maxFitness = 0.0;

        for (Entity entity : entities) {
            Double fitness = (double) evaluation.evaluate(entity.getGenome());
            entity.setFitness(fitness);
            if(fitness > maxFitness) {
                maxFitness = fitness;
            }
        }
        return maxFitness;
    }

    private List<Entity> crossover() {
        // select fittest parents
        Collections.sort(entities);

        List<Entity> parents = entities.subList(0, 4);

        // TODO do the crossover X
        List<Entity> children = new ArrayList<>();
        double[] firstChild = new double[10];
        double[] secondChild = new double[10];
        for (int i = 0; i < 10; i++) {
            firstChild[i] = (parents.get(0).getGenome()[i] + parents.get(1).getGenome()[i]) / 2.0;
            secondChild[i] = (parents.get(2).getGenome()[i] + parents.get(3).getGenome()[i]) / 2.0;
        }
        children.add(new Entity(firstChild));
        children.add(new Entity(secondChild));

        return children;
    }

    private List<Entity> mutation(List<Entity> entitiesToMutate) {
        // TODO with some probability do mutation

        return entitiesToMutate;
    }

    public void nextGeneration() {
        List<Entity> newEntities = crossover();
        mutation(newEntities);

        entities = entities.subList(0, entities.size() - newEntities.size());
        entities.addAll(newEntities);

        System.out.println("Size of population: " + getPopulationSize());
    }

    public int getPopulationSize() {
        return entities.size();
    }
}
