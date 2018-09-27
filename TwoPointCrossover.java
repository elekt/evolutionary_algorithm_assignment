import java.util.ArrayList;
import java.util.*;

public class TwoPointCrossover implements Crossover {
    @Override
    public List<Individual> crossover(List<Individual> parents){
        if(parents.size() != 2) {
            throw new IllegalArgumentException("SimpleCrossover should have 2 parents");
        }

        double[] child1 = new double[10];
        double[] child2 = new double[10];

        Random r1 = new Random();
        int Low1 = 0;
        int High1 = 7;
        int P1 = r1.nextInt(High1-Low1) + Low1;
        System.out.print("P1:");
        System.out.println(P1);

        Random r2 = new Random();
        int Low2 = P1;
        int High2 = 10;
        int P2 = r2.nextInt(High2-Low2) + Low2;
        System.out.print("P2:");
        System.out.println(P2);

        for (int i = 0; i < 10; i++) {
          if(i<=P1) {
              child1[i] = (parents.get(0).getGenome()[i]);
              child2[i] = (parents.get(1).getGenome()[i]);

          }
          else if (i>P1 && i<P2) {
        	  child1[i] = (parents.get(1).getGenome()[i]);
              child2[i] = (parents.get(0).getGenome()[i]);


          } else {
        	  child1[i] = (parents.get(0).getGenome()[i]);
              child2[i] = (parents.get(1).getGenome()[i]);
          }
      }

        List<Individual> ret = new ArrayList<>();
        ret.add(new Individual(child1));
        ret.add(new Individual(child2));
        return ret;
    }
}

