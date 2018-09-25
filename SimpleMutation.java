import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SimpleMutation extends BaseMutation{

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
                System.out.println("SimpleMutation");
                double mutationValue = mutationSpeed * rnd.nextDouble();
                double currentValue = individual.getGene(i);
                boolean isAdding = rnd.nextDouble()>0.5;
                double mutatedValue = isAdding ? currentValue + mutationValue : currentValue - mutationValue;

                System.out.println(String.format("Mutation value: %f", mutationValue));
                individual.setGene(i, mutatedValue);
            }
        }
    }
}
