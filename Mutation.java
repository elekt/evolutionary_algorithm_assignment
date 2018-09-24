import java.util.List;

public interface Mutation {
    void mutate(Individual individual);

    void mutateIndividuals(List<Individual> individuals);

}
