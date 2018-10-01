import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UniformParentSelection implements Selection {


    private int matingPoolSize; // parameter
    private Random rnd;

    public UniformParentSelection(int matingPoolSize) {

        this.matingPoolSize = matingPoolSize;
        rnd = new Random();
    }

    @Override
    public List<Individual> selectIndividuals(List<Individual> individuals, int populationSize) {

        List<Individual> matingPool = new ArrayList<>();

        int currentMember = 1;

        while (currentMember <= matingPoolSize) {
            matingPool.add(individuals.get(rnd.nextInt(individuals.size())));
            currentMember++;
        }

//        System.out.println("______________MATING POOL");
//        for (int i = 0; i < matingPoolSize; i++) {
//            System.out.println(matingPool.get(i).getFitness());
//
//        }
//        System.out.println("\n");

        return matingPool;

    }
}
