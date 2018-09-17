import java.util.ArrayList;
import java.util.List;

public class DummyCrossover implements Crossover {

    @Override
    public List<Individual> crossover(Individual individual1, Individual individual2) {
        double[] child = new double[10];
        for (int i = 0; i < 10; i++) {
            child[i] = (individual1.getGenome()[i] + individual2.getGenome()[i]) / 2.0;
        }

        List<Individual> ret = new ArrayList<>();
        ret.add(new Individual(child));
        return ret;
    }
}
