import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.Properties;

public class player2 implements ContestSubmission
{
	private Random rnd;
	private ContestEvaluation evaluation;
    private int evaluations_limit;
	private Population population;

	public player2()
	{
		rnd = new Random();
	}
	
	public void setSeed(long seed)
	{
		// Set seed of algortihms random process
		rnd.setSeed(seed);
	}

	public void setEvaluation(ContestEvaluation evaluation)
	{
		// Set evaluation problem used in the run
		this.evaluation = evaluation;
		
		// Get evaluation properties
		Properties props = evaluation.getProperties();
        // Get evaluation limit
        evaluations_limit = Integer.parseInt(props.getProperty("Evaluations"));
	evaluations_limit = 2000;
	System.out.println(evaluations_limit);
		// Property keys depend on specific evaluation
		// E.g. double param = Double.parseDouble(props.getProperty("property_name"));
        boolean isMultimodal = Boolean.parseBoolean(props.getProperty("Multimodal"));
        boolean hasStructure = Boolean.parseBoolean(props.getProperty("Regular"));
        boolean isSeparable = Boolean.parseBoolean(props.getProperty("Separable"));

		// Do sth with property values, e.g. specify relevant settings of your algorithm
        if(isMultimodal){
            // Do sth
        }else{
            // Do sth else
        }
    }
    
	public void run()
	{
	    int evals = 0;
	    int generation = 0;


        // init population
        population = new Population(50, rnd, evaluation);
        // calculate fitness
        while(population.getFittest().getFitness() >= 9.0 || evals < evaluations_limit){
            // Select parents
			double currentFitness = population.evaluatePopulation();

            try {
                population.nextGeneration();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                break;
            }

            evals += population.getPopulationSize();
	    generation += 1;


            if(evals % 50 == 0) {
                System.out.print("Score: ");
                System.out.println(currentFitness);
		System.out.print("Generation: ");
		System.out.println(generation);
            }

        }
        System.out.print("Best ");
	}
}
