import java.util.List;

public interface Selection {
    List<Individual> selectIndividuals(List<Individual> individuals, int populationSize);
}
