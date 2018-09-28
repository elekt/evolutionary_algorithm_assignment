import org.vu.contest.ContestEvaluation;

import java.util.*;
import java.lang.Math; 

public class Population {
    private List<Individual> individuals;
    private int expectedPopulationSize;
    private ContestEvaluation evaluation;
    private Random rnd;
    private Crossover crossover;
    private Mutation[] mutations;
    private Selection[] parentSelection;
    private Selection[] survivorSelection;
    private int evals;
    private int evalsLimit;

    public Population(int _size, Random _rnd, ContestEvaluation _evaluation) {
        expectedPopulationSize = _size;
        evaluation = _evaluation;
        individuals = new ArrayList<>();
        rnd = _rnd;
        Properties props = evaluation.getProperties();
        evalsLimit = Integer.parseInt(props.getProperty("Evaluations"));

        // Choose Crossover method: OnePointCrossover, TwoPointCrossover, UniformCrossover or BlendCrossover
        crossover = new OnePointCrossover(); // OnePoint is default

        mutations = new Mutation[] {    new InversionMutation(0.6),
                                        new SimpleMutation(0.6, 0.5),
                                        new SwapMutation(0.6, 2),
                                        new ScrambleMutation(0.6) };

        parentSelection = new Selection[] { new RankingSelectionSUS(2, 1.3),
                                            new TournamentSelection(2, 4),
                                            new UniformParentSelection(2)};
        survivorSelection = new Selection[] {    new RoundRobinSelection()};


        for(int i = 0; i< expectedPopulationSize; ++i){
            individuals.add(new Individual(rnd));
        }
      evals = 0;
    }

    public double evaluatePopulation() {
        double maxFitness = 0.0;
        for (Individual individual : individuals) {
            if(evals <= evalsLimit) {
                if(individual.getFitness() < 0.0) {
                    double fitness = (double) evaluation.evaluate(individual.getGenome());
                    ++evals;
                    individual.setFitness(fitness);
                    if (fitness > maxFitness) {
                        maxFitness = fitness;
                    }
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

        parentSelection[2].selectIndividuals(individuals, expectedPopulationSize);

        // parent selection
        Collections.sort(individuals);

        List<Individual> parents = individuals.subList(0, 2);
        List<Individual> children = crossover.crossover(parents);
        parents = individuals.subList(2, 4);
        children.addAll(crossover.crossover(parents));
        

        // select random mutation
        mutations[rnd.nextInt(mutations.length)].mutateIndividuals(children);
	individuals.addAll(children);
        // before selection update fitness values
        evaluatePopulation();

        // survivor selection
        individuals = survivorSelection[0].selectIndividuals(individuals, expectedPopulationSize);

        //System.out.println("After selection");
        //for(Double d : getFitnessList()) {
            //System.out.print(String.format("%.4f ", d));
        //}
        //System.out.println();

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
    
     public int getEvaluationCount() {		
         return evals;		
     }
}
