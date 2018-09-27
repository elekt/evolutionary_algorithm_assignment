import java.util.ArrayList;
import java.util.*;

public class BlendCrossover implements Crossover {
    @Override
    public List<Individual> crossover(List<Individual> parents){
        if(parents.size() != 2) {
            throw new IllegalArgumentException("SimpleCrossover should have 2 parents");
        }

        double[] child1 = new double[10];
        double[] child2 = new double[10];

        Random r = new Random();
        double alpha =  Math.random()- 0.5;

        for (int i = 0; i < 10; i++) {
            child1[i] = (alpha * (parents.get(0).getGenome()[i])) + ((1-alpha)*(parents.get(1).getGenome()[i]));
            child2[i] = (alpha * (parents.get(1).getGenome()[i])) + ((1-alpha)*(parents.get(0).getGenome()[i]));
        }

        List<Individual> ret = new ArrayList<>();
        ret.add(new Individual(child1));
        ret.add(new Individual(child2));
        return ret;
    }
}

