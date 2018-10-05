import java.util.List;

public interface Exchange {
    List<Individual> exchange(List<Individual> currentIslandPopulation, List<Individual> leftIslandPopulation, List<Individual> rightIslandPopulation, int amountIndivExchange);
}
