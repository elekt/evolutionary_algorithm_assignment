import java.util.Random;

public class NonUniformMutation extends BaseMutation{

    private double mutationProbability;
    private Random rnd;

    public NonUniformMutation(double mutationProbability) {

        if(mutationProbability > 1.0 || mutationProbability < 0.0){
            throw new IllegalArgumentException("Probability can only be between 0.0 and 1.0");
        }

        this.mutationProbability = mutationProbability;
        rnd = new Random();
    }

    @Override
    public void mutate(Individual individual) {
        for(int i=0; i<individual.getGenome().length; ++i) {
            boolean isMutating = rnd.nextDouble() > mutationProbability;

            if(isMutating) {
                double mutationValue = rnd.nextGaussian() ;
                double currentValue = individual.getGene(i);
                double mutatedValue = currentValue + mutationValue;

                individual.setGene(i, mutatedValue);
            }
        }
    }
}
