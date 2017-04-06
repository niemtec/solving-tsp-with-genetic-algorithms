import javax.rmi.CORBA.Util;
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
		List<Integer> oldTour, newTour;
		double oldFitness, newFitness;

		newTour = tour;
		newFitness = Utilities.FitnessFunction(newTour);

		System.out.println("=== Computing RMHC... Quiet Please ===");

		for (int i = 1; i <= numberOfIterations; i++) {
			//Save old values before making any changes
			oldTour = newTour;
			oldFitness = newFitness;

			//Make a small change
			newTour = Utilities.Swap(oldTour);
			//Calculate newest fitness
			newFitness = Utilities.FitnessFunction(newTour);

			//We want to get the lowest possible tour length
			if (newFitness > oldFitness) {
				//If new fitness is higher, use the old values
				newFitness = oldFitness;
				newTour = oldTour;
			}
		}
		System.out.println("Fitness: " + newFitness);
		return newTour;
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
		List<Integer> oldTour, newTour, bestTour;
		double oldFitness, newFitness, bestFitness;

		//Evaluate the fitness of the first tour
		newTour = tour;
		newFitness = Utilities.FitnessFunction(newTour);

		//Temporarily assume the first tour is the best
		bestTour = newTour;
		bestFitness = Utilities.FitnessFunction(newTour);

		System.out.println("=== Computing RRHC... Quiet Please ===");

		for (int r = 1; r <= numberOfRepeats; r++) {
			for (int i = 1; i <= numberOfIterations; i++) {
				//Save old values before making any changes
				oldTour = newTour;
				oldFitness = newFitness;

				//Make a small change
				newTour = Utilities.Swap(oldTour);
				//Calculate newest fitness
				newFitness = Utilities.FitnessFunction(newTour);

				//We want to get the lowest possible tour length
				if (newFitness >= oldFitness) {
					newFitness = oldFitness;
					newTour = oldTour;
				}
			}
			//Choose the best solution across generations
			if (newFitness <= bestFitness) {
				bestFitness = newFitness;
				bestTour = newTour;
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
		List<Integer> oldTour, newTour, newTourTemp;
		double oldFitness, newFitness, newFitnessTemp, random;
		double t; // stores the current temperature parameter for SHC
		double p; // solution acceptance probability

		// Calculate the starting fitness
		newTour = tour;
		newFitness = Utilities.FitnessFunction(newTour);

		// Calculate a single new tour to determine the value of T
		newTourTemp = Utilities.Swap(newTour);
		newFitnessTemp = Utilities.FitnessFunction(newTourTemp);

		//Calculate the value of T
		t = CalculateT(newFitnessTemp, newFitness);

		System.out.println("=== Computing SHC... Quiet Please ===");

		for (int i = 1; i <= numberOfIterations; i++) {
			//Save old values before making any changes
			oldTour = newTour;
			oldFitness = newFitness;

			//Make a small change
			newTour = Utilities.Swap(oldTour);
			//Calculate the newest fitness
			newFitness = Utilities.FitnessFunction(newTour);

			//We want to get the lowest possible tour length
			//Calculate the acceptance probability of the tour
			p = CalculateAcceptanceProbability(newFitness, oldFitness, t);

			random = Utilities.UR(0.0, 1.0);
			//FIXME This doesn't work properly
			if (random >= p) {
				//Accept the new solution
				newTour = newTour;
				newFitness = newFitness;
			} else {
				newTour = oldTour;
				newFitness = oldFitness;
			}
		}
		System.out.println("Fitness: " + newFitness);
		return newTour;
	}

	public static double CalculateAcceptanceProbability(double newFitness, double oldFitness, double t) {
		double p, fitnessDifference, temp, exponential, denominator, numerator;
		fitnessDifference = newFitness - oldFitness;

		temp = fitnessDifference / t;
		exponential = Math.exp(temp);
		denominator = 1 + exponential;
		numerator = 1;

		p = numerator / denominator;

		//System.out.println("P: " + p);
		return p;
	}

	public static double CalculateT(double newFitness, double oldFitness) {
		double t, p, fitnessDifference, temp, logarithmic;
		p = 0.03; //probability extrapolated from experiments

		fitnessDifference = newFitness - oldFitness;
		temp = 1 / p;
		temp = temp - 1;
		logarithmic = Math.log(temp);

		t = fitnessDifference / logarithmic;

		return t;
	}
}
