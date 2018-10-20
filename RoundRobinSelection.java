import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.lang.Math;
import java.util.Comparator;

public class RoundRobinSelection implements Selection {
    private int q;

    public RoundRobinSelection(int q) {
        this.q = q;
    }

    @Override
    public List<Individual> selectIndividuals(List<Individual> individuals, int populationSize) {

        // int populationSize = 15;

        for (Individual individual : individuals) {
            int wins = 0;
            for(int i = 0; i< q; ++i){

                Individual contestant = individuals.get(new Random().nextInt(individuals.size()));

                if (individual.getFitness() > contestant.getFitness()) {
                    wins += 1;
                }

            }
            individual.wins = wins;
        }

        individuals.sort((c1, c2) -> Integer.compare(c2.wins, c1.wins));
        //System.out.println(individuals);
        //Collections.sort(individuals);
        //System.out.println(individuals);

        individuals = individuals.subList(0, populationSize);

        return individuals;
    }

}
