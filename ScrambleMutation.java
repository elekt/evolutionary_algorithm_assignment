import java.util.*;

public class ScrambleMutation extends BaseMutation {

    private double mutationProbability;
    private Random rnd;


    public ScrambleMutation(double mutationProbability) {

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
            List<Integer> order = new ArrayList<>();
            for (int i = 0; i < individual.getGenome().length; i++) {
                order.add(i);
            }
            Collections.shuffle(order);


            for(int i = 0; i < individual.getGenome().length; ++i) {
                int swapIndex = order.get(i);
                double c = individual.getGene(i);

                individual.setGene(i, individual.getGene(swapIndex));
                individual.setGene(swapIndex, c);
            }
        }
    }
}
