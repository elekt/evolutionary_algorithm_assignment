import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.HashMap;
import java.util.Map;
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
	
	public void setSeed(long seed) { }

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

    public void run() {

        double diversity = 0.0;
        int diversity_count = 0;
        Map<String, Double> paramMap = getAlgorithmParams();

        // initialize population
        population = new Population(paramMap.get("PopulationSize").intValue(), rnd, evaluation, paramMap);

        // next generation until evals limit is reached (termination)
        while(population.getEvaluationCount() < evaluations_limit){

            try {
                population.nextGeneration();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                break;
            }

            diversity += population.getDiversity();
            diversity_count += 1;

        }

        // calc diversity op population
        System.out.print("Final Diversity: ");
        System.out.println(population.getDiversity());
        System.out.print("Mean Diversity: ");
        System.out.println(diversity/diversity_count);
        System.out.print("Best ");
	}

    private Map<String, Double> getAlgorithmParams() {
        Map<String, Double> ret = new HashMap<>();

        String s = System.getProperty("var1");

        String[] pairs = s.split(",");
        for (int i=0; i<pairs.length; i++) {
            String pair = pairs[i];
            String[] keyValue = pair.split(":");
            ret.put(keyValue[0], Double.valueOf(keyValue[1]));
        }
        return ret;
    }
}