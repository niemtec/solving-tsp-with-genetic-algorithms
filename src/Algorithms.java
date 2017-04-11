import java.util.ArrayList;
import java.util.Collections;

public class Algorithms {
	/**
	 * Random Restart Hill Climber
	 *
	 * @param tour               starting tour
	 * @param numberOfIterations number of times to run the algorithm for
	 * @param printReport        decides whether to print fitness or not
	 * @return returns the most efficient tour found
	 */
	static ArrayList<Integer> RRHC(ArrayList<Integer> tour, int numberOfIterations, boolean printReport) {
		ArrayList<Integer> currentTour; //Stores current tour from the RMHC
		ArrayList<Integer> bestTour = new ArrayList<>(); //Stores best tour
		int numberOfRepeats = 2;

		//Get temporary fitness as best fitness
		double bestFitness = Performance.CalculateFitness(tour);
		double fitness; //Current fitness
		for (int r = 0; r < numberOfRepeats; r++) {
			//Generate a random starting point for the next RMHC tour
			Collections.shuffle(tour);
			currentTour = RMHC(tour, numberOfIterations / numberOfRepeats, false);
			fitness = Performance.CalculateFitness(currentTour);

			if (fitness < bestFitness) {
				//Save better fitness and tour
				bestFitness = fitness;
				bestTour = (ArrayList<Integer>) currentTour.clone();
			}
		}

		if (printReport == true) {
			//Calculate efficiency against optimal settings
			double efficiency = Performance.CalculateEfficiency(bestFitness);
			System.out.println("Fitness: " + bestFitness + " | Efficiency: " + efficiency + "%");
		}
		return bestTour;
	}

	/**
	 * Random Mutation Hill Climber
	 *
	 * @param tour               starting tour
	 * @param numberOfIterations number of times to run the algorithm for
	 * @param printReport        decides whether to print fitness or not
	 * @return returns the most efficient tour found
	 */
	static ArrayList<Integer> RMHC(ArrayList<Integer> tour, int numberOfIterations, boolean printReport) {
		double newFitness; //Fitness score after making a small change
		double fitness = 0; //Starting fitness
		ArrayList<Integer> oldTour; //Stores the old tour prior to making a small change

		//Run the algorithm for a set number of iterations
		for (int i = 1; i <= numberOfIterations; i++) {
			//Save the current tour as a copy into the old tour
			oldTour = (ArrayList<Integer>) tour.clone();
			//Calculate the current fitness (before change)
			fitness = Performance.CalculateFitness(tour);

			//Cause a small change to the tour
			tour = Tools.SmallChange(tour);
			//Calculate new fitness after causing the small change
			newFitness = Performance.CalculateFitness(tour);

			if (newFitness > fitness) {
				//If the old fitness was better, restore the old tour
				tour = oldTour;
			}
		}
		if (printReport == true) {
			//Calculate efficiency against optimal settings
			double efficiency = Performance.CalculateEfficiency(fitness);
			System.out.println("Fitness: " + fitness + " | Efficiency: " + efficiency + "%");
		}
		return tour;
	}

	/**
	 * Stochastic Hill Climber
	 *
	 * @param tour               starting tour
	 * @param numberOfIterations number of times to run the algorithm for
	 * @param temperature        temperature for the acceptance function (usually 25% of n)
	 * @param printReport        decides whether to print fitness or not
	 * @return returns the most efficient tour found
	 */
	static ArrayList<Integer> SHC(ArrayList<Integer> tour, int numberOfIterations, double temperature, boolean printReport) {
		double newFitness; //Fitness score after making a small change
		double fitness = 0; //Starting fitness
		double p;
		ArrayList<Integer> oldTour; //Stores the old tour prior to making a small change

		//Run the algorithm for a set number of iterations
		for (int i = 1; i <= numberOfIterations; i++) {
			//Save the current tour as a copy into the old tour
			oldTour = (ArrayList<Integer>) tour.clone();
			//Calculate the current fitness (before change)
			fitness = Performance.CalculateFitness(tour);

			//Cause a small change to the tour
			tour = Tools.SmallChange(tour);
			//Calculate new fitness after causing the small change
			newFitness = Performance.CalculateFitness(tour);

			//Select new tour based on probability
			//If new tour is better then p = 1
			p = Tools.CalculateAcceptanceProbability(newFitness, fitness, temperature);

			double random = Tools.UR(0.0, 1.0);

			//Use the old tour if acceptance probability matches
			if (p < random) {
				//Use the old tour
				tour = oldTour;
			}
		}

		if (printReport == true) {
			//Calculate efficiency against optimal settings
			double efficiency = Performance.CalculateEfficiency(fitness);
			System.out.println("Fitness: " + fitness + " | Efficiency: " + efficiency + "%");
		}
		return tour;
	}

	static ArrayList<Integer> SA(ArrayList<Integer> tour, double temperature, int numberOfIterations, double coolingRate, boolean printReport) {
		ArrayList<Integer> oldTour;
		double fitness = 0, newFitness, p;

		for (int i = 0; i < numberOfIterations; i++) {
			//Save the current tour as a copy into the old tour
			oldTour = (ArrayList<Integer>) tour.clone();

			//Calculate the current fitness (before change)
			fitness = Performance.CalculateFitness(tour);

			//Cause a small change to the tour
			tour = Tools.SmallChange(tour);

			//Calculate new fitness after causing the small change
			newFitness = Performance.CalculateFitness(tour);

			if (newFitness > fitness) {
				//Calculate p
				p = Tools.PR(newFitness, fitness, temperature);
				if (p < Tools.UR(0.0, 1.0)) {
					//Reject the change and restore previous tour
					tour = oldTour;
				} else {
					//Accept the change
					//Don't do anything
				}
			} else {
				//Accept the change
				//Don't do anything
			}
			temperature = coolingRate * temperature;
		}

		if (printReport == true) {
			//Calculate efficiency against optimal settings
			double efficiency = Performance.CalculateEfficiency(fitness);
			System.out.println("Fitness: " + fitness + " | Efficiency: " + efficiency + "%");
		}
		return tour;
	}
}
