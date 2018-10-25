import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RankingSelectionSUS implements Selection {

    private double s; // parameter
    private int matingPoolSize; // parameter
    private Random rnd;

    public RankingSelectionSUS(int matingPoolSize, double s) {

        if (s <= 1 || s > 2) {
            throw new IllegalArgumentException("parameter s has to be 1 < s <= 2");
        }

        this.matingPoolSize = matingPoolSize;
        this.s = s;
        rnd = new Random();
    }

    @Override
    public List<Individual> selectIndividuals(List<Individual> individuals, int populationSize) {

        return StochasticUniversalSampling(individuals, getCumProbDistribution(populationSize, s), matingPoolSize);
    }


    private Double[] getCumProbDistribution(int populationSize, double s) {

        Double[] cumProbDistribution = new Double[populationSize];

        for (int i = 0; i < populationSize; i++) {

            // calculate probability based on linear ranking scheme
            double probRank = (2 - s)/populationSize + (2 * i * (s - 1))/ (populationSize * (populationSize - 1));

            if (i == 0) {
                cumProbDistribution[i] = probRank;
            }
            else {
                cumProbDistribution[i] = probRank + cumProbDistribution[i - 1];
            }
        }
        return cumProbDistribution;
    }


    private List<Individual> StochasticUniversalSampling(List<Individual> individuals, Double[] cumProbDistribution, int matingPoolSize) {

        if (matingPoolSize < 2) {
            throw new IllegalArgumentException("mating pool should contain at least two parents");
        }

        List<Individual> matingPool = new ArrayList<>();
        int i = 1, currentMember = 1;
        double r = rnd.nextDouble() * ((double)1 / matingPoolSize);

        while (currentMember <= matingPoolSize) {
            while (r <= cumProbDistribution[i]) {
                matingPool.add(individuals.get(i));
                r = r + ((double)1 / matingPoolSize);
                currentMember++;
            }
            i++;
        }
        return matingPool;
    }
}