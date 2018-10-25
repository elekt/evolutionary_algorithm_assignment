import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UniformParentSelection implements Selection {


    private int matingPoolSize; // parameter
    private Random rnd;

    public UniformParentSelection(int matingPoolSize) {
        if (matingPoolSize % 2 != 0) {
            throw new IllegalArgumentException("The mating pool size should be an even number");
        }

        this.matingPoolSize = matingPoolSize;
        rnd = new Random();
    }

    @Override
    public List<Individual> selectIndividuals(List<Individual> individuals, int populationSize) {
        if (matingPoolSize > individuals.size()) {
            throw new IllegalArgumentException("The mating pool size cannot be larger than the population");
        }
        if (matingPoolSize < 2) {
            System.out.println(this.getClass().getName());
            throw new IllegalArgumentException("mating pool should contain at least two parents");
        }

        List<Individual> matingPool = new ArrayList<>();

        int currentMember = 1;

        while (currentMember <= matingPoolSize) {
            matingPool.add(individuals.get(rnd.nextInt(individuals.size())));
            currentMember++;
        }

        return matingPool;

    }
}
