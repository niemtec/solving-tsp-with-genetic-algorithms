import java.util.List;

public class Algorithms {
	/**
	 * Random Mutation Hill Climbing Method used to generate a number of solutions based on the fitness score.
	 *
	 * @param tour               starting tour - will be modified using small change
	 * @param numberOfIterations the total number of iterations to run the algorithm for
	 * @return returns the most optimal tour after a given number of iterations
	 */
	public static List<Integer> RMHC(List<Integer> tour, int numberOfIterations) {
		List<Integer> oldTour, currentTour;
		double oldFitness, currentFitness;

		currentTour = tour;
		currentFitness = Utilities.FitnessFunction(currentTour);

		System.out.println("=== Computing RMHC... Quiet Please ===");

		for (int i = 1; i <= numberOfIterations; i++) {
			//Save old values before making any changes
			oldTour = currentTour;
			oldFitness = currentFitness;

			//Make a small change
			currentTour = Utilities.Swap(oldTour);
			//Calculate newest fitness
			currentFitness = Utilities.FitnessFunction(currentTour);

			//We want to get the lowest possible tour length
			if (currentFitness >= oldFitness) {
				currentFitness = oldFitness;
				currentTour = oldTour;
			}
		}
		System.out.println("Fitness: " + currentFitness);
		return currentTour;
	}

	/**
	 * Random Restart Hill Climber
	 *
	 * @param tour               starting tour - will be modified using small change
	 * @param numberOfIterations number of generations to run the algorithm for
	 * @return returns the most optimal tour after a given number of iterations
	 */
	public static List<Integer> RRHC(List<Integer> tour, int numberOfIterations) {
		int numberOfRepeats = 1000;
		List<Integer> oldTour, currentTour, bestTour;
		double oldFitness, currentFitness, bestFitness;

		//Evaluate the fitness of the first tour
		currentTour = tour;
		currentFitness = Utilities.FitnessFunction(currentTour);

		//Temporarily assume the first tour is the best
		bestTour = currentTour;
		bestFitness = Utilities.FitnessFunction(currentTour);

		System.out.println("=== Computing RRHC... Quiet Please ===");

		for (int r = 1; r <= numberOfRepeats; r++) {
			for (int i = 1; i <= numberOfIterations; i++) {
				//Save old values before making any changes
				oldTour = currentTour;
				oldFitness = currentFitness;

				//Make a small change
				currentTour = Utilities.Swap(oldTour);
				//Calculate newest fitness
				currentFitness = Utilities.FitnessFunction(currentTour);

				//We want to get the lowest possible tour length
				if (currentFitness >= oldFitness) {
					currentFitness = oldFitness;
					currentTour = oldTour;
				}
			}
			//Choose the best solution across generations
			if (currentFitness <= bestFitness) {
				bestFitness = currentFitness;
				bestTour = currentTour;
			}
		}
		System.out.println("Fitness: " + bestFitness);
		return bestTour;
	}

	/**
	 * Stochastic Hill Climber
	 *
	 * @param tour               starting tour - will be modified using small change
	 * @param numberOfIterations the total number of iterations to run the algorithm for
	 * @return returns the most optimal tour after a given number of iterations
	 */
	public static List<Integer> SHC(List<Integer> tour, int numberOfIterations) {
		List<Integer> oldTour, currentTour;
		double oldFitness, currentFitness;

		currentTour = tour;
		currentFitness = Utilities.FitnessFunction(currentTour);

		System.out.println("=== Computing SHC... Quiet Please ===");

		for (int i = 1; i <= numberOfIterations; i++) {
			//Save old values before making any changes
			oldTour = currentTour;
			oldFitness = currentFitness;

			//Make a small change
			currentTour = Utilities.Swap(oldTour);
			//Calculate the newest fitness
			currentFitness = Utilities.FitnessFunction(currentTour);

			//We want to get the lowest possible tour length
			int T = 50; //Parameter for the function -- here be magic
			double p = 1 / (1 + Math.exp((currentFitness - oldFitness) / T));
			if (Utilities.UI(0, 1) < p) {
				//Accept the new solution
				currentFitness = oldFitness;
				currentTour = oldTour;
			}
		}
		System.out.println("Fitness: " + currentFitness);
		return currentTour;
	}
}
