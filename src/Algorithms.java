import java.util.ArrayList;
import java.util.List;

public class Algorithms {
	/**
	 * Random Mutation Hill Climbing Method used to generate a number of solutions based on the fitness score.
	 *
	 * @param tour               starting tour - will be modified using small change
	 * @param numberOfIterations the total number of iterations to run the algorithm for
	 * @return returns the most optimal tour after a given number of iterations
	 */
	public static ArrayList<Integer> RMHC(ArrayList<Integer> tour, int numberOfIterations) {
		ArrayList<Integer> oldTour, newTour;
		double oldFitness, newFitness;

		newTour = new ArrayList<>(tour);
		newFitness = Utilities.FitnessFunction(newTour);


		for (int i = 1; i <= numberOfIterations; i++) {
			//Save old values before making any changes
			oldTour = new ArrayList<>(newTour);
			oldFitness = newFitness;

			//Make a small change
			newTour = new ArrayList<>(Utilities.Swap(oldTour));
			//Calculate newest fitness
			newFitness = Utilities.FitnessFunction(newTour);

			//We want to get the lowest possible tour length
			if (newFitness > oldFitness) {
				//If new fitness is higher, use the old values
				newFitness = oldFitness;
				newTour = new ArrayList<>(oldTour);
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
	public static ArrayList<Integer> RRHC(ArrayList<Integer> tour, int numberOfIterations) {
		int numberOfRepeats = numberOfIterations / 10;
		ArrayList<Integer> oldTour, newTour, bestTour;
		double oldFitness, newFitness, bestFitness;

		//Evaluate the fitness of the first tour
		newTour = new ArrayList<>(tour);
		newFitness = Utilities.FitnessFunction(newTour);

		//Temporarily assume the first tour is the best
		bestTour = newTour;
		bestFitness = Utilities.FitnessFunction(newTour);

		for (int r = 1; r <= numberOfRepeats; r++) {
			for (int i = 1; i <= numberOfIterations; i++) {
				//Save old values before making any changes
				oldTour = new ArrayList<>(newTour);
				oldFitness = newFitness;

				//Make a small change
				newTour = new ArrayList<>(Utilities.Swap(oldTour));
				//Calculate newest fitness
				newFitness = Utilities.FitnessFunction(newTour);

				//We want to get the lowest possible tour length
				if (newFitness >= oldFitness) {
					newFitness = oldFitness;
					newTour = new ArrayList<>(oldTour);
				}
			}
			//Choose the best solution across generations
			if (newFitness <= bestFitness) {
				bestFitness = newFitness;
				bestTour = new ArrayList<>(newTour);
			}
		}
		System.out.println("Fitness: " + bestFitness);
		return bestTour;
	}


	public static double SHC(ArrayList<Integer> tour, int numberOfIterations, double t, boolean reporting) {
		ArrayList<Integer> oldTour, newTour, newTourTemp;
		double oldFitness, newFitness, newFitnessTemp, random;
		double p; // solution acceptance probability

		// Calculate the starting fitness
		newTour = new ArrayList<>(tour);
		newFitness = Utilities.FitnessFunction(newTour);

		for (int i = 1; i <= numberOfIterations; i++) {
			//Save old values before making any changes
			oldTour = new ArrayList<>(newTour);
			oldFitness = newFitness;

			//Make a small change and calculate the newest fitness
			newTour = new ArrayList<>(Utilities.Swap(oldTour));
			newFitness = Utilities.FitnessFunction(newTour);

			//If the current solution is worse than the previous one
			if (newFitness > oldFitness) {
				// Determine if the old solution should be accepted
				p = CalculateAcceptanceProbability(newFitness, oldFitness, t);

				if (p >= Utilities.UR(0.0, 1.0)) {
					// If the probability of accepting worse solution is high ... accept it
					newFitness = newFitness;
					newTour = new ArrayList<>(newTour);
				} else {
					// Do not accept the worse solution
					newFitness = oldFitness;
					newTour = new ArrayList<>(oldTour);
				}
				// If fitness is the same
			} else if (newFitness == oldFitness) {
				// 50-50 chance of choosing old solution
				int r = Utilities.UI(0, 1);
				if (r == 0) {
					// Choose new solution
					newFitness = newFitness;
					newTour = new ArrayList<>(newTour);
				} else {
					// Choose old solution
					newFitness = oldFitness;
					newTour = new ArrayList<>(oldTour);
				}
			} else {
				// Accept the new solution
				newFitness = newFitness;
				newTour = new ArrayList<>(newTour);
			}
		}

		if (reporting == true) {
			System.out.println("Fitness: " + newFitness);
		}
		return newFitness;
	}

	public static double CalculateAcceptanceProbability(double newFitness, double oldFitness, double t) {
		double p, fitnessDifference, temp, exponential, denominator, numerator;
		fitnessDifference = newFitness - oldFitness;

		temp = fitnessDifference / t;
		exponential = Math.exp(temp);
		denominator = 1 + exponential;
		numerator = 1;

		p = numerator / denominator;

		return p;
	}

	public static double CalculateT(double[][] distanceMatrix, double newFitness, double oldFitness) {
		int yLength = distanceMatrix[0].length;
		int xLength = distanceMatrix[1].length;
		double k = 0.0;
		double totalDistance = 0.0;
		double t;

		for (int y = 0; y < yLength; y++) {
			for (int x = 0; x < xLength; x++) {
				totalDistance = totalDistance + distanceMatrix[y][x];
			}
		}

		t = totalDistance / k;

		return t;
	}
}
