import java.util.List;

public interface Crossover {
    List<Individual> crossover(Individual parent1, Individual parent2);
}
