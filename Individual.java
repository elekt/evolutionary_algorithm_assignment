import java.util.Random;

public class Individual implements Comparable<Individual>{


    private double[] genome;
    private Double fitness = 0.0;
    private int maxValue = 5;
    private int minValue = -5;
    private int dimensions = 10;

    public Individual(Random _rnd) {
        genome = new double[dimensions];
        for(int i=0; i<dimensions; ++i){
            genome[i] = _rnd.nextDouble()*(maxValue-minValue) - maxValue;
        }
    }

    public Individual(double[] _genome) {
        genome = _genome;
    }

    public double[] getGenome() {
        return genome;
    }

    public Double getFitness() {
        return fitness;
    }

    public void setFitness(Double fitness) {
        this.fitness = fitness;
    }

    @Override
    public int compareTo(Individual other) {
        return this.fitness.compareTo(other.fitness);
    }
}