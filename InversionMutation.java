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
            for(int i = 0; i < individual.getGenome().length; ++i) {
                int temp1 = rnd.nextInt(individual.getGenome().length);
                int temp2 = rnd.nextInt(individual.getGenome().length);

                int inverseRange1 = (temp1>temp2)? temp1:temp2;
                int inverseRange2 = (temp1>temp2)? temp2:temp1;

                double[] unboxedGenome = Arrays.copyOfRange(individual.getGenome(), inverseRange1, inverseRange2);
                Double[] boxedGenome = new Double[unboxedGenome.length];

                for(int j = 0; i<unboxedGenome.length; ++i) {
                    boxedGenome[j] = new Double(unboxedGenome[j]);
                }

                List<Double> inverseList = Arrays.asList(boxedGenome);
                Collections.reverse(inverseList);

                System.out.println(individual.getGenome().toString());

                for(int j = inverseRange1; j < inverseRange2; ++j) {
                    individual.setGene(j, inverseList.get(j));
                }
                System.out.println(individual.getGenome().toString());

            }
        }

    }
}
