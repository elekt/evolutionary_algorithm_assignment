import java.util.Random;

public class Entity implements Comparable<Entity>{


    private double[] genome;
    private Double fitness = 0.0;
    private int maxValue = 5;
    private int minValue = -5;
    private int dimensions = 10;

    public Entity(Random _rnd) {
        genome = new double[dimensions];
        for(int i=0; i<dimensions; ++i){
            genome[i] = _rnd.nextDouble()*(maxValue-minValue) - maxValue;
        }
    }

    public Entity(double[] _genome) {
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
    public int compareTo(Entity other) {
        return this.fitness.compareTo(other.fitness);
    }
}