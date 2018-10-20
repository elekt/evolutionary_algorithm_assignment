import java.util.ArrayList;
import java.util.*;

public class WholeArithmeticCrossover implements Crossover {

    double alpha;

    public WholeArithmeticCrossover(double alpha) {
        this.alpha = alpha;
    }

    @Override
    public List<Individual> crossover(List<Individual> parents) {

        if (parents.size() != 2) {
            throw new IllegalArgumentException("Whole Arithmetic Crossover should have 2 parents");
        }

        double[] child1 = new double[10];
        double[] child2 = new double[10];

        for (int i = 0; i < 10; i++) {

            child1[i] = alpha * parents.get(0).getGenome()[i] + (1 - alpha) * parents.get(1).getGenome()[i];
            child2[i] = alpha * parents.get(1).getGenome()[i] + (1 - alpha) * parents.get(0).getGenome()[i];

        }
            List<Individual> offspring = new ArrayList<>();
            offspring.add(new Individual(child1));
            offspring.add(new Individual(child2));
            return offspring;
    }
}
