import java.util.*;

public class SwapMutation extends BaseMutation {

    private double mutationProbability;
    private Random rnd;
    private int numberOfSwaps;


    public SwapMutation(double mutationProbability, int numberOfSwaps) {
        if(mutationProbability > 1.0 || mutationProbability < 0.0){
            throw new IllegalArgumentException("Probability can only be between 0.0 and 1.0");
        }

        this.numberOfSwaps = numberOfSwaps;
        this.mutationProbability = mutationProbability;
        rnd = new Random();
    }

    @Override
    public void mutate(Individual individual) {
        boolean isMutating = rnd.nextDouble() > mutationProbability;

        if(isMutating) {
            System.out.println("SwapMutation");
            for(int i = 0; i < numberOfSwaps; ++i) {
                int swapIndex1 = rnd.nextInt(individual.getGenome().length);
                int swapIndex2 = rnd.nextInt(individual.getGenome().length);
                double c = individual.getGene(swapIndex1);
                individual.setGene(swapIndex1, individual.getGene(swapIndex2));
                individual.setGene(swapIndex2, c);
            }
        }
    }
}
