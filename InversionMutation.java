import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class InversionMutation extends BaseMutation {

    private double mutationProbability;
    private Random rnd;


    public InversionMutation(double mutationProbability) {
        if(mutationProbability > 1.0 || mutationProbability < 0.0){
            throw new IllegalArgumentException("Probability can only be between 0.0 and 1.0");
        }

        this.mutationProbability = mutationProbability;
        rnd = new Random();
    }

    @Override
    public void mutate(Individual individual) {
        boolean isMutating = rnd.nextDouble() > mutationProbability;

        if(isMutating) {
            int temp1 = rnd.nextInt(individual.getGenome().length - 4);
            int temp2 = 4 + rnd.nextInt(individual.getGenome().length - 4);

            int inverseRange1 = (temp1>temp2) ? temp2 : temp1;
            int inverseRange2 = (temp1>temp2) ? temp1 : temp2;

            double[] unboxedGenome = Arrays.copyOfRange(individual.getGenome(), inverseRange1, inverseRange2);
            Double[] boxedGenome = new Double[unboxedGenome.length];

            for(int j = 0; j<unboxedGenome.length; ++j) {
                boxedGenome[j] = unboxedGenome[j];
            }

            List<Double> inverseList = Arrays.asList(boxedGenome);
            Collections.reverse(inverseList);


            for(int j = inverseRange1; j < inverseRange2; ++j) {
                individual.setGene(j, inverseList.get(j - inverseRange1));
            }
        }

    }
}
