import java.util.Collections;
import java.util.List;

public class SimpleParentSelection implements Selection {

    private int matingPoolSize; // parameter

    public SimpleParentSelection(int matingPoolSize) {
        if (matingPoolSize % 2 != 0) {
            throw new IllegalArgumentException("The mating pool size should be an even number");
        }
        if (matingPoolSize < 2) {
            System.out.println(this.getClass().getName());
            throw new IllegalArgumentException("mating pool should contain at least two parents");
        }
        this.matingPoolSize = matingPoolSize;
    }

    @Override
    public List<Individual> selectIndividuals(List<Individual> individuals, int populationSize) {
        if (matingPoolSize > individuals.size()) {
            throw new IllegalArgumentException("The mating pool size cannot be larger than the population");
        }

        Collections.sort(individuals);

        List<Individual> matingPool = individuals.subList(0, matingPoolSize);

        return matingPool;
    }
}
