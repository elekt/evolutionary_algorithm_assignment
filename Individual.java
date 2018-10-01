import java.util.Random;

public class Individual implements Comparable<Individual>{


    private double[] genome;
    private Double fitness = -1.0;
    private double maxValue = 5;
    private double minValue = -5;
    private int dimensions = 10;
    public int wins = 0;
    public int subPopulation = 1;

    public Individual(Random _rnd, int subPopulation) {
        genome = new double[dimensions];
        for(int i=0; i<dimensions; ++i){
            genome[i] = _rnd.nextDouble()*(maxValue-minValue) - maxValue;
        }
    this.setSubPopulation(subPopulation);
    }

    public Individual(double[] _genome) {
        genome = _genome;
    }

    public double[] getGenome() {
        return genome;
    }

    public void setGene(int i, double value) {

        if(i > genome.length || i < 0) {
            throw new IllegalArgumentException("The genome has 10 genes. Trying to set on an invalid position");
        }

        if(Double.compare(value, maxValue) > 0) {
            genome[i] = maxValue;
        } else if(Double.compare(value, minValue) < 0) {
            genome[i] = minValue;
        } else {
            genome[i] = value;
        }

        // since it changed the fitness is invalid
        fitness = -1.0;
    }

    public double getGene(int i) {
        if(i > genome.length || i < 0) {
            throw new IllegalArgumentException("The genome has 10 genes. Trying to get from an invalid position");
        }
        return genome[i];
    }

    public Double getFitness() {
        return fitness;
    }

    public void setFitness(Double fitness) {
        this.fitness = fitness;
    }

    @Override
    public int compareTo(Individual other) {
        return other.fitness.compareTo(this.fitness);
    }

    public double getMaxValue() {
        return maxValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setSubPopulation(int subPopulation) {
    this.subPopulation = subPopulation;
    }

    public int getSubPopulation() {
    return subPopulation;
    }

}