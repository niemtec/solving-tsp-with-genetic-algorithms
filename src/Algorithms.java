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
}
