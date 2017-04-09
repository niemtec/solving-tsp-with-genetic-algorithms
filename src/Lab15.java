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
		int numberOfRepeats;
		int numberOfIterations = 10000;
		double temperature, coolingRate;
		List<Integer> tour;

		Utilities.LoadDataFile(48);
		//Generate the first base tour
		tour = Utilities.PopulateCities(matrixSize);

		//Set a random point in the search space
		tour = Utilities.PermuteTour(tour);
		System.out.println("Tour: " + tour);

		//Determine heuristically optimal temperature setting for the SHC
		System.out.println("=== Calculating Temperature ===");
		temperature = Utilities.CalculateStochasticTemperature(distanceArray, tour);
		System.out.println("Best Temperature: " + temperature);

		System.out.println("=== Calculating Cooling Rate ===");
		coolingRate = CalculateCoolingRate(temperature, numberOfIterations);
		System.out.println("Cooling Rate: " + coolingRate);

		System.out.println("=== Computing SA... Quiet Please ===");
		SA(tour, numberOfIterations, temperature, coolingRate);

		System.out.println("=== Computing RRHC... Quiet Please ===");
		Algorithms.RRHC(tour, numberOfIterations / 10);


		System.out.println("=== Computing SHC... Quiet Please ===");
		Algorithms.SHC(tour, numberOfIterations, temperature, true);

		System.out.println("=== Computing RMHC... Quiet Please ===");
		Algorithms.RMHC(tour, numberOfIterations);
	}

	private static void SA(List<Integer> tour, int numberOfIterations, double startingTemperature, double coolingRate) {
		List<Integer> oldTour, newTour, newTourTemp;
		double oldFitness, newFitness, newFitnessTemp, random;
		double p; // solution acceptance probability

		double temperature = startingTemperature;

		// Calculate the starting fitness
		newTour = tour;
		newFitness = Utilities.FitnessFunction(newTour);

		for (int i = 1; i <= numberOfIterations; i++) {
			//Save old values before making any changes
			oldTour = newTour;
			oldFitness = newFitness;

			//Make a small change and calculate the newest fitness
			newTour = Utilities.Swap(oldTour);
			newFitness = Utilities.FitnessFunction(newTour);

			if (newFitness > oldFitness) {
				p = Efficiency.PR(newFitness, oldFitness, temperature);

				if (p < Utilities.UR(0.0, 1.0)) {
					//Reject Change, keep x and f
					newFitness = oldFitness;
					newTour = oldTour;
				} else {
					//Accept the change
					newFitness = newFitness;
					newTour = newTour;
				}
			} else {
				newFitness = newFitness;
				newTour = newTour;
			}
			temperature = temperature * coolingRate;
		}
		System.out.println("Fitness: " + newFitness);
	}

	private static double CalculateCoolingRate(double startingTemperature, int numberOfIterations) {
		double tIter, tValue, coolingRate;
		int iter;

		tIter = 0.001;
		iter = numberOfIterations;

		tValue = tIter / startingTemperature;

		coolingRate = Math.pow(tValue, 1 / iter);

		return coolingRate;
	}
}