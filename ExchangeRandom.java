import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.lang.Math; 
import java.util.Comparator;

public class ExchangeRandom implements Exchange {


    @Override
    public List<Individual> exchange(List<Individual> currentIslandPopulation, List<Individual> leftIslandPopulation, List<Individual> rightIslandPopulation, int amountIndivExchange) {

    	Collections.shuffle(leftIslandPopulation);
        Collections.shuffle(rightIslandPopulation);
        currentIslandPopulation.addAll(rightIslandPopulation.subList(0, amountIndivExchange));
        currentIslandPopulation.addAll(leftIslandPopulation.subList(0, amountIndivExchange));
    	return currentIslandPopulation;
    }
}
