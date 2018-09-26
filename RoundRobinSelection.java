import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.lang.Math; 
import java.util.Comparator;

public class RoundRobinSelection implements Selection {


    @Override
    public List<Individual> selectIndividuals(List<Individual> individuals, int populationSize) {
	int q = 5;
	// int populationSize = 15;
	
	for (Individual individual : individuals) {
	    int wins = 0;
	    for(int i = 0; i< q; ++i){

		Individual contestant = individuals.get(new Random().nextInt(individuals.size()));

		if (individual.getFitness() > contestant.getFitness()) {
		    wins += 1;
		}
		
	    }
	    individual.wins = wins;
	    //System.out.print("Individual: ");
	    //System.out.print(individual);
	    //System.out.print(" wins: ");
	    //System.out.println(wins);
        }

        Collections.sort(individuals, new Comparator<Individual>() {
  public int compare(Individual c1, Individual c2) {
    if (c1.wins > c2.wins) return -1;
    if (c1.wins < c2.wins) return 1;
    return 0;
  }});
	//System.out.println(individuals);
        //Collections.sort(individuals);
	//System.out.println(individuals);
	
        individuals = individuals.subList(0, populationSize);

        return individuals;
    }

}
