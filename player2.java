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
	    int generation = 0;


        // init population
        population = new Population(20, rnd, evaluation);
        // calculate fitness

        while(population.getEvaluationCount() < evaluations_limit){
            // Select parents
			double currentFitness = population.evaluatePopulation();

      

            try {
                population.nextGeneration();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                break;
            }


            if(population.getEvaluationCount() % 1 == 0) {
                System.out.println(String.format("Eval: %d Score: %f", population.getEvaluationCount(), population.getFittest().getFitness()));

            }

        }
        System.out.print("Best ");
	}
}
