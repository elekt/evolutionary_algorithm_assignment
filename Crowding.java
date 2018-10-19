import java.util.ArrayList;
import java.util.List;

public class Crowding {

    public List<Individual> crowd(List<Individual> parents, List<Individual> children) {
        if (parents.size()  != 2) {
            throw new IllegalArgumentException("Crowding parents size has to be 2");
        }

        if (children.size()  != 2) {
            throw new IllegalArgumentException("Crowding children size has to be 2");
        }
        List<Individual> ret = new ArrayList<>();
        double d11, d12, d21, d22 = 0.0;

        d11 = euclidDistance(parents.get(0), children.get(0));
        d12 = euclidDistance(parents.get(0), children.get(1));
        d21 = euclidDistance(parents.get(1), children.get(0));
        d22 = euclidDistance(parents.get(1), children.get(1));

        if(d11+d22 <= d12+d21) {
            if(children.get(0).getFitness() > parents.get(0).getFitness()) {
                System.out.println("FIRST CHILD BEATS PARENT 1");
                ret.add(children.get(0));
            } else {
                System.out.println("PARENT 1 ");
                ret.add(parents.get(0));
            }
            if(children.get(1).getFitness() > parents.get(1).getFitness()) {
                System.out.println("CHILD 2");
                ret.add(children.get(1));
            } else {
                System.out.println("PARENT 2");
                ret.add(parents.get(1));
            }
        } else {
            if(children.get(0).getFitness() > parents.get(1).getFitness()) {
                ret.add(children.get(0));
            } else {
                ret.add(parents.get(1));
            }
            if(children.get(1).getFitness() > parents.get(0).getFitness()) {
                ret.add(children.get(1));
            } else {
                ret.add(parents.get(0));
            }
        }

        return ret;
    }

    private double euclidDistance (Individual i1, Individual i2) {
        double distance = 0.;
        for(int i = 0; i < i1.getGenome().length; ++i) {
            distance += Math.pow(i1.getGenome()[i] - i2.getGenome()[i], 2);
        }
        return Math.sqrt(distance);
    }

}
