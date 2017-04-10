/**
 * The Travelling Salesperson Problem
 * Laboratory 15 Worksheet
 * CS2004 Algorithms and their Applications
 * Brunel University London
 * Created by Jakub Adrian Niemiec (@niemtec) on 2017-03-25.
 * Student ID: 1500408
 */

import java.util.*;

/**
 * SA, RRHC, SHC, RMHC
 */

public class Lab15 {
	//Determine the length of the array first by loading it temporarily and measuring it
	static double[][] matrixDimensionCountArray;
	static int matrixSize;
	public static double[][] distanceArray;
	public static int numberOfCities = 48;

	public static void main(String args[]) {
		int numberOfRepeats = 5;
		int numberOfIterations = 5000;
		double temperature, coolingRate, temperatureAnnealing;
		int[] tour = new int[numberOfCities];


		Utilities.LoadDataFile(numberOfCities);
		System.out.println("NUMBER OF CITIES: " + numberOfCities);
		System.out.println("NUMBER OF ITERATIONS: " + numberOfIterations);

		//TODO REMOVE AFTER TESTING
		ArrayList<Integer> optimalTourList = TSP.ReadIntegerFile("data/TSP_48_OPT.txt");
		//TODO Convert ArrayList into an array
		int listLength = optimalTourList.size();
		int[] optimalTour = new int[listLength];
		for (int i = 0; i < listLength; i++) {
			optimalTour[i] = optimalTourList.get(i);
		}


		//Generate the first base tour
		tour = Utilities.PopulateCities(matrixSize);
		System.out.println("INITIAL ROUTE: ");
		Utilities.Print1DArray(tour);

		//Set a random point in the search space
		tour = Utilities.RandomiseArray(tour);
		System.out.println("STARTING ROUTE: ");
		Utilities.Print1DArray(tour);

		//TODO REMOVE AFTER TESTING
		tour = optimalTour;
		Utilities.Print1DArray(tour);

		//Determine heuristically optimal temperature setting for the SHC
		System.out.println("=== Calculating Temperature for SHC ===");
		temperature = Utilities.CalculateStochasticTemperature(distanceArray, tour, numberOfIterations);
		System.out.println("Best Temperature: " + temperature);

		System.out.println("=== Calculating Cooling Rate ===");
		coolingRate = CalculateCoolingRate(temperature, numberOfIterations);
		System.out.println("Cooling Rate: " + coolingRate);

		for (int i = 0; i < numberOfRepeats; i++) {
			System.out.println();
			System.out.println("=== Computing SA... Quiet Please ===");
			SA(tour, numberOfIterations, temperature, coolingRate, true);

			System.out.println("=== Computing RRHC... Quiet Please ===");
			Algorithms.RRHC(tour, numberOfIterations / 10);

			System.out.println("=== Computing SHC... Quiet Please ===");
			Algorithms.SHC(tour, numberOfIterations, temperature, true);

			System.out.println("=== Computing RMHC... Quiet Please ===");
			Algorithms.RMHC(tour, numberOfIterations);
		}
	}

	static double SA(int[] tour, int numberOfIterations, double startingTemperature, double coolingRate, boolean printFitness) {
		//List<Integer> oldTour, newTour, bestTour;
		List<Double> temperature = new ArrayList<>();
		double oldFitness, newFitness, bestFitness, temp;
		double p; // solution acceptance probability

		int[] oldTour = new int[numberOfCities];
		int[] newTour = new int[numberOfCities];
		int[] bestTour = new int[numberOfCities];

		temperature.add(0, startingTemperature);

		// Calculate the starting fitness
		newTour = tour.clone();
		newFitness = Utilities.FitnessFunction(newTour);
		bestFitness = newFitness;

		for (int i = 0; i < numberOfIterations; i++) {
			//Save old values before making any changes
			oldTour = newTour.clone();
			oldFitness = newFitness;

			//Make a small change and calculate the newest fitness
			newTour = Utilities.SmallChange(oldTour);
			newFitness = Utilities.FitnessFunction(newTour);

			if (newFitness > oldFitness) {
				//p = Efficiency.PR(newFitness, oldFitness, temperature);
				p = Efficiency.PR(newFitness, oldFitness, temperature.get(i));

				//Decide whether to reject the change
				if (p < Utilities.UR(0.0, 1.0)) {
					//Reject Change, keep x and f
					newFitness = oldFitness;
					newTour = oldTour.clone();
				} else {
					//Accept the change, use x' and f'
					newFitness = newFitness;
					newTour = newTour.clone();
				}

			} else {
				oldFitness = newFitness;
				oldTour = newTour.clone();
			}

			double currentTemperature = temperature.get(i);
			temp = coolingRate * currentTemperature;
			temperature.add(i + 1, temp);
		}
		if (printFitness == true) {
			System.out.println("Fitness: " + bestFitness);

		}

		return bestFitness;
	}


	private static double CalculateCoolingRate(double startingTemperature, int numberOfIterations) {
		double tIter, tValue, coolingRate, powerValue;
		int iter;

		tIter = 0.001;
		iter = numberOfIterations;

		tValue = tIter / startingTemperature;
		powerValue = 1.0 / iter;

		coolingRate = Math.pow(tValue, powerValue);

		return coolingRate;
	}
}