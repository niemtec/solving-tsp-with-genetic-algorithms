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

	public static void main(String args[]) {
		int numberOfRepeats = 5;
		int numberOfIterations = 10000;
		double temperature, coolingRate, coolingRateSA, temperatureAnnealing;
		ArrayList<Integer> tour;

		Utilities.LoadDataFile(48);
		//Generate the first base tour
		tour = Utilities.PopulateCities(matrixSize);

		//Set a random point in the search space
		tour = Utilities.PermuteTour(tour);
		System.out.println("Tour: " + tour);

		//TODO TESTING
		//tour = TSP.ReadIntegerFile("data/TSP_48_OPT.txt");
		//System.out.println("Tour: " + tour);


		//Determine heuristically optimal temperature setting for the SHC
		System.out.println("=== Calculating Temperature for SHC ===");
		//temperature = Utilities.CalculateStochasticTemperature(distanceArray, tour, numberOfIterations);
		//System.out.println("Best Temperature: " + temperature);

		System.out.println("=== Calculating Cooling Rate ===");
		//coolingRate = CalculateCoolingRateSA(temperature, numberOfIterations);
		//coolingRate = CalculateCoolingRate(numberOfIterations);
		//System.out.println("Cooling Rate: " + coolingRate);

			System.out.println();
			System.out.println("=== Computing SA... Quiet Please ===");
		//SA(tour, numberOfIterations, temperature, coolingRate, true);

			System.out.println("=== Computing RRHC... Quiet Please ===");
			Algorithms.RRHC(tour, numberOfIterations / 10);

			System.out.println("=== Computing SHC... Quiet Please ===");
		//Algorithms.SHC(tour, numberOfIterations, temperature, true);

			System.out.println("=== Computing RMHC... Quiet Please ===");
			Algorithms.RMHC(tour, numberOfIterations);
	}

	static double SA(ArrayList<Integer> tour, int numberOfIterations, double startingTemperature, double coolingRate, boolean printFitness) {
		ArrayList<Integer> oldTour, newTour, bestTour;
		ArrayList<Double> temperature = new ArrayList<>();
		double oldFitness, newFitness, bestFitness, temp;
		double p; // solution acceptance probability

		temperature.add(0, startingTemperature);

		// Calculate the starting fitness
		newTour = tour;
		newFitness = Utilities.FitnessFunction(newTour);
		bestFitness = newFitness;

		for (int i = 0; i < numberOfIterations; i++) {
			//Save old values before making any changes
			oldTour = new ArrayList<>(newTour);
			oldFitness = newFitness;

			//Make a small change and calculate the newest fitness
			newTour = new ArrayList<>(Utilities.Swap(oldTour));
			newFitness = Utilities.FitnessFunction(newTour);

			if (newFitness > oldFitness) {
				//p = Efficiency.PR(newFitness, oldFitness, temperature);
				p = Efficiency.PR(newFitness, oldFitness, temperature.get(i));

				//Decide whether to reject the change
				if (p < Utilities.UR(0.0, 1.0)) {
					//Reject Change, keep x and f
					newFitness = oldFitness;
					newTour = new ArrayList<>(oldTour);
				} else {
					//Accept the change, use x' and f'
					newFitness = newFitness;
					newTour = new ArrayList<>(newTour);
				}

			} else {
				oldFitness = newFitness;
				oldTour = new ArrayList<>(newTour);
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

	private static double CalculateCoolingRate(int numberOfIterations) {
		double coolingRate = CalculateCoolingRateSA(numberOfIterations, numberOfIterations);
		return coolingRate;
	}

	private static double CalculateCoolingRateSA(double startingTemperature, int numberOfIterations) {
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