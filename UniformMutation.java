import java.util.Random;

public class UniformMutation extends BaseMutation{

    private double mutationProbability;
    private double mutationSpeed;
    private Random rnd;
    private int maxEvalCount;


    public UniformMutation(double mutationProbability, double mutationSpeed, int maxEvalCount) {
        this.maxEvalCount = maxEvalCount;

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
                double evalRate = 1.001 - Population.evals / (double)maxEvalCount;
                double mutationValue = evalRate * mutationSpeed * rnd.nextDouble();
                double currentValue = individual.getGene(i);
                boolean isAdding = rnd.nextDouble()>0.5;
                double mutatedValue = isAdding ? currentValue + mutationValue : currentValue - mutationValue;

                individual.setGene(i, mutatedValue);
            }
        }
    }
}
