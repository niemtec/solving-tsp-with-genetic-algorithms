import java.util.ArrayList;
import java.util.Collections;

/**
 * The Travelling Salesperson Problem
 * Laboratory 15 Worksheet
 * CS2004 Algorithms and their Applications
 * Brunel University London
 * Created by Jakub Adrian Niemiec (@niemtec) on 2017-03-25.
 * Student ID: 1500408
 */

public class Main {
	static int numberOfCities = 442;
	static int numberOfIterations = 10000000;
	static int numberOfRepetitions = 0;
	static double[][] distanceArray;

	public static void main(String args[]) {
		ArrayList<Integer> tour, saTour, rrhcTour, rmhcTour, shcTour;

		while (numberOfIterations <= 50000) {
			for (numberOfRepetitions = 0; numberOfRepetitions < 20; numberOfRepetitions++) {
				System.out.println("> RUNNING REPETITION NUMBER " + numberOfRepetitions + ".");

				distanceArray = Tools.LoadDataFile(numberOfCities);

				System.out.println("> NUMBER OF ITERATIONS SELECTED: " + numberOfIterations + ".");

				//Populate the cities
				tour = Tools.PopulateCities(numberOfCities);
				//Pick a random starting point in the search space
				Collections.shuffle(tour);

				//Starting temperature is 2.5% of the total problem space as per lecture slides
				double stochasticTemperature = numberOfCities * 0.025;

				System.out.println("> CALCULATING COOLING RATE.");
				double coolingRate = Tools.CalculateCoolingRate(stochasticTemperature, numberOfIterations);
				System.out.println("Cooling Rate: " + coolingRate);

				System.out.println("> SIMULATED ANNEALING RUNNING.");
				//Starting temperature derived from running various experiments
				saTour = Algorithms.SA(tour, 10000.0, numberOfIterations, coolingRate, true);
				Tools.saveResults(saTour, "SimulatedAnnealing", true);

				System.out.println("> RANDOM RESTART HILL CLIMBER RUNNING.");
				rrhcTour = Algorithms.RRHC(tour, numberOfIterations, true);
				Tools.saveResults(rrhcTour, "RandomRestartHillClimber", true);

				System.out.println("> STOCHASTIC HILL CLIMBER RUNNING.");
				shcTour = Algorithms.SHC(tour, numberOfIterations, stochasticTemperature, true);
				Tools.saveResults(shcTour, "StochasticHillClimber", true);

				System.out.println("> RANDOM MUTATION HILL CLIMBER RUNNING.");
				rmhcTour = Algorithms.RMHC(tour, numberOfIterations, true);
				Tools.saveResults(rmhcTour, "RandomMutationHillClimbing", true);
			}
			numberOfIterations = numberOfIterations + 1000;
		}

		System.out.println("> CALCULATIONS COMPLETE.");
	}

}

