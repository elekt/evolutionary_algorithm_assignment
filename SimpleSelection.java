import java.util.Collections;
import java.util.List;

public class SimpleSelection implements Selection {

    @Override
    public List<Individual> selectIndividuals(List<Individual> individuals, int populationSize) {

        if(populationSize > individuals.size()) {
            System.out.println("WARNING: Expected population size is bigger than the current population in selection!");
            return individuals;
        }
	
	
        Collections.sort(individuals);

        individuals = individuals.subList(0, populationSize);

        return individuals;
    }
}
