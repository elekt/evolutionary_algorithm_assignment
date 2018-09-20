import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SimpleMutation implements Mutation {

    private double mutationProbability;
    private double mutationSpeed;
    private Random rnd;


    public SimpleMutation(double mutationProbability, double mutationSpeed) {

        if(mutationProbability > 1.0 || mutationProbability < 0.0){
            throw new IllegalArgumentException("Probability can only be between 0.0 and 1.0");
        }

        this.mutationProbability = mutationProbability;
        this.mutationSpeed = mutationSpeed;
        rnd = new Random();
    }

    @Override
    public void mutate(Individual individual) {
        for(int i=0; i<individual.getGenome().length; ++i) {
            boolean isMutating = rnd.nextDouble() > mutationProbability;

            if(isMutating) {
                double minValue = individual.getMinValue();
                double maxValue = individual.getMaxValue();
                double valueRange = maxValue - minValue;
                double mutationValue = valueRange * mutationSpeed * (minValue + valueRange*rnd.nextDouble());
                double currentValue = individual.getGene(i);
                double mutatedValue = currentValue + mutationValue;

                individual.setGene(i, mutatedValue);
            }
        }
    }

    @Override
    public void mutateIndividuals(List<Individual> individuals, double individualMutationProbability) {
        for(Individual individual : individuals) {
            mutate(individual);
        }
    }
}
