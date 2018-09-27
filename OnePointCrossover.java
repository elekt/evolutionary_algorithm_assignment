import java.util.ArrayList;
import java.util.*;

public class OnePointCrossover implements Crossover {
    @Override
    public List<Individual> crossover(List<Individual> parents){
        if(parents.size() != 2) {
            throw new IllegalArgumentException("SimpleCrossover should have 2 parents");
        }

        double[] child1 = new double[10];
        double[] child2 = new double[10];

        // Not random, at 5
        // TO DO: RANDOM??

        for (int i = 0; i < 10; i++) {
            if(i<=4) {
                child1[i] = (parents.get(0).getGenome()[i]);
                child2[i] = (parents.get(1).getGenome()[i]);

            } else {
                child1[i] = (parents.get(1).getGenome()[i]);
                child2[i] = (parents.get(0).getGenome()[i]);
            }
        }

            List<Individual> ret = new ArrayList<>();
            ret.add(new Individual(child1));
            ret.add(new Individual(child2));
            return ret;
        }
    }
