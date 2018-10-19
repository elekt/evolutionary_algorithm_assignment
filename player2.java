import org.vu.contest.ContestEvaluation;
import org.vu.contest.ContestSubmission;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class player2 implements ContestSubmission
{
	private Random rnd;
	private ContestEvaluation evaluation;
    private int evaluations_limit;
	private Population population;
    private int islands;
    private int popSize;
    private int subPop;

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

        subPop = getAlgorithmParams().get("subPopulationSize").intValue();
        islands = getAlgorithmParams().get("numberOfIslands").intValue();
//        // Do sth with property values, e.g. specify relevant settings of your algorithm
//        if(!isMultimodal){
//            islands = 1;
//        }

        popSize = islands * subPop;
    }
    
	public void run()
	{
		int generation = 0;

        Map<String, Double> paramMap = getAlgorithmParams();

        // init population
        population = new Population(popSize, rnd, evaluation, islands, paramMap);
	double diversity = 0.0;
	int diversity_count = 0;
        while(population.getEvaluationCount() < evaluations_limit){
            // Select parents
            double currentFitness = population.evaluatePopulation();
            try {
                if (islands > 1){
                    population.nextGenerationIslands(generation);
                } else {
                    population.nextGeneration();
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                break;
            }
            generation += 1;

            if(generation % 100 == 0) {
//                System.out.print("Generation: ");
//                System.out.print(generation);
//                System.out.print(", Score: ");
//                System.out.println(population.getFittest().getFitness());
//                //System.out.println(String.format("Eval: %d Score: %f", population.getEvaluationCount(), population.getFittest().getFitness()));
//                System.out.print("Score: ");
//                System.out.println(currentFitness);
		diversity += population.getDiversity();
		diversity_count += 1;
            }
		}
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
