import java.util.Arrays;
import java.util.List;


public abstract class BaseMutation implements Mutation {

    @Override
    public void mutateIndividuals(List<Individual> individuals) {
        for(Individual individual : individuals) {
            mutate(individual);
        }
    }
}