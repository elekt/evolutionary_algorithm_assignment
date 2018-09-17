

public class DummyMutation implements Mutation {

    @Override
    public Individual mutate(Individual individual) {
        individual.setGene(0, 0);
        return individual;
    }
}
