public class DummyCrossover implements Crossover {

    @Override
    public Individual crossover(Individual individual1, Individual individual2) {
        double[] child = new double[10];
        for (int i = 0; i < 10; i++) {
            child[i] = (individual1.getGenome()[i] + individual2.getGenome()[i]) / 2.0;
        }

        return new Individual(child);
    }
}
