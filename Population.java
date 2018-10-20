import org.vu.contest.ContestEvaluation;

import java.util.*;

public class Population {
    private List<Individual> individuals;
    private int expectedPopulationSize;
    private ContestEvaluation evaluation;
    private Map<String, Double> paramMap;
    private Random rnd;
    private Crossover[] crossover;
    private Mutation[] mutations;
    private Selection[] parentSelection;
    public static int evals;
    private int evalsLimit;
    private int crossoverMethod;
    private int mutationMethod;
    private int parentSelectionMethod;
    private Selection survivorSelection;

    public Population(int _size, Random _rnd, ContestEvaluation _evaluation, Map<String, Double> paramMap) {
        expectedPopulationSize = _size;
        evaluation = _evaluation;
        this.paramMap = paramMap;
        individuals = new ArrayList<>();
        rnd = _rnd;
        Properties props = evaluation.getProperties();
        evalsLimit = Integer.parseInt(props.getProperty("Evaluations"));

        crossover = new Crossover[] {
                new TwoPointCrossover(),
                new UniformCrossover(),
                new BlendCrossover(),
                new WholeArithmeticCrossover(0.5)};

        mutations = new Mutation[] {
                new UniformMutation(paramMap.get("MutationProbability"), paramMap.get("MutationSpeed"), evalsLimit),
                new NonUniformMutation(paramMap.get("MutationProbability"))};

        parentSelection = new Selection[] {
                new TournamentSelection(paramMap.get("MatingPoolSize").intValue(), paramMap.get("NumberOfParticipants").intValue())};


        survivorSelection = new RoundRobinSelection(paramMap.get("SurvivorSelectionSize").intValue());

        parentSelectionMethod = 0;
        crossoverMethod = paramMap.get("crossoverMethod").intValue();
        mutationMethod = paramMap.get("mutationMethod").intValue();


        for(int i = 0; i< expectedPopulationSize; ++i){
            individuals.add(new Individual(rnd));
        }
        evals = 0;
    }

    public double evaluatePopulation() {
        double maxFitness = 0.0;
        for (Individual individual : individuals) {
            if(evals <= evalsLimit) {
                double fitness;
                if(individual.getFitness() < 0.0) {
                    fitness = (double) evaluation.evaluate(individual.getGenome());
                    ++evals;
                    individual.setFitness(fitness);
                } else {
                    fitness = individual.getFitness();
                }
                if (fitness > maxFitness) {
                    maxFitness = fitness;
                }
            } else {
                System.out.println("Run out eval cycles");
                break;
            }
        }
        return maxFitness;
    }

    public void nextGeneration() throws IllegalArgumentException {

        // TODO: create a better way of parent selection instead of just ranking and select the best individuals
        // TODO: have a look at how to pair parents for crossover (e.g. always pair the best ones or pair randomly)

        evaluatePopulation();

        parentSelection[parentSelectionMethod].selectIndividuals(individuals, expectedPopulationSize);

        // parent selection
        Collections.sort(individuals);

        List<Individual> parents = individuals.subList(0, 2);
        List<Individual> children = crossover[crossoverMethod].crossover(parents);
        parents = individuals.subList(2, 4);
        children.addAll(crossover[crossoverMethod].crossover(parents));
        individuals.addAll(children);

        // select random mutation
        mutations[mutationMethod].mutateIndividuals(individuals);

        // before selection update fitness values
        evaluatePopulation();

        // survivor selection
        individuals = survivorSelection.selectIndividuals(individuals, expectedPopulationSize);
    }

    public Individual getFittest() {
        Collections.sort(individuals);
        return individuals.get(0);
    }

    public int getPopulationSize() {
        return individuals.size();
    }

    private double[] getFitnessList() {
        double[] ret = new double[individuals.size()];

        for(int i = 0; i<individuals.size(); ++i){
            ret[i] = individuals.get(i).getFitness();
        }

        return ret;
    }

    public double getDiversity() {
        double maxFitness = 0.0;
        double score = 0.0;
        Individual fittest = getFittest();
        double[] fittestGenome = fittest.getGenome();
        double[] compareGenome;
        for (Individual individual : individuals) {
            compareGenome = individual.getGenome();
            for(int i=0; i<10; ++i){
                score += Math.abs(compareGenome[i] - fittestGenome[i]);
            }
        }
        return (score/getPopulationSize());
    }

    public int getEvaluationCount() {
        return evals;
    }
}
