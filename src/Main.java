import java.util.ArrayList;
import java.util.Collections;

public class Main {

	static int numberOfCities = 48;
	static int numberOfIterations = 10000;
	static double[][] distanceArray;

	public static void main(String args[]) {
		ArrayList<Integer> tour; //Stores the current tour

		distanceArray = Tools.LoadDataFile(numberOfCities);

		//Populate the cities
		tour = Tools.PopulateCities(numberOfCities);
		//Pick a random starting point in the search space
		Collections.shuffle(tour);

		//Starting temperature is 2.5% of the total problem space
		double stochasticTemperature = numberOfCities * 0.025;

		System.out.println("Calculating the cooling rate");
		double coolingRate = CalculateCoolingRate(stochasticTemperature, numberOfIterations);
		System.out.println("Cooling Rate: " + coolingRate);

		System.out.println("Simulated Annealing");
		//Starting temperature derived from running various experiments
		SA(tour, 10000.0, numberOfIterations, coolingRate, true);
		System.out.println("Random Restart Hill Climber");
		Algorithms.RRHC(tour, numberOfIterations, true);
		System.out.println("Stochastic Hill Climber");
		Algorithms.SHC(tour, numberOfIterations, stochasticTemperature, true);
		System.out.println("Random Mutation Hill Climber");
		Algorithms.RMHC(tour, numberOfIterations, true);


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
				p = PR(newFitness, fitness, temperature);
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

	private static double CalculateCoolingRate(double startingTemperature, int numberOfIterations) {
		double tIter, tValue, coolingRate, powerValue;
		int iter;

		tIter = 0.001; //Number from lecture slides
		iter = numberOfIterations;

		tValue = tIter / startingTemperature;
		powerValue = 1.0 / iter;

		coolingRate = Math.pow(tValue, powerValue);

		return coolingRate;
	}


	static double PR(double newFitness, double oldFitness, double temperature) {
		double changeInFitness = Math.abs(newFitness - oldFitness);
		changeInFitness = -1 * changeInFitness;
		double prScore = Math.exp(changeInFitness / temperature);
		return prScore;
	}


	private static double CalculateStochasticTemperature(ArrayList<Integer> tour) {
		ArrayList<Double> allTemperatures = new ArrayList<>();
		ArrayList<Integer> tempTour = new ArrayList<>();
		double t;

		System.out.println("Calculating Average Stochastic Temperature");

		for (int i = 0; i < 5; i++) {
			System.out.print(".");
			for (t = 5000; t < 10000; t++) {
				tempTour = Algorithms.SHC(tour, 1000, t, false);
				double fitness = Performance.CalculateFitness(tempTour);
				allTemperatures.add(fitness);
			}
		}
		t = Tools.CalculateAverage(allTemperatures);
		System.out.println("Tep: " + t);
		System.out.println();
		return t;
	}
}

