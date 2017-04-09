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
		double temperature;
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

		System.out.println("=== Computing SA... Quiet Please ===");
		SA(tour, numberOfIterations, temperature);

		System.out.println("=== Computing RRHC... Quiet Please ===");
		Algorithms.RRHC(tour, numberOfIterations / 10);


		System.out.println("=== Computing SHC... Quiet Please ===");
		Algorithms.SHC(tour, numberOfIterations, temperature, true);

		System.out.println("=== Computing RMHC... Quiet Please ===");
		Algorithms.RMHC(tour, numberOfIterations);
	}

	private static void SA(List<Integer> tour, int numberOfIterations, double startingTemperature) {
		List<Integer> oldTour, newTour;
		double oldFitness, newFitness, temperature, coolingRate, p;

		// Set initial starting temperature
		temperature = startingTemperature;

		newTour = tour;
		newFitness = Utilities.FitnessFunction(newTour);

		// Loop until system has cooled
		while (temperature > 1) {
			//Save old values before making any changes
			oldTour = newTour;
			oldFitness = newFitness;

			//Make a small change
			newTour = Utilities.Swap(oldTour);
			//Calculate newest fitness
			newFitness = Utilities.FitnessFunction(newTour);

			// Decide if to accept the neighbour
			if (newFitness > oldFitness) {
				p = Efficiency.PR(newFitness, oldFitness, temperature);
				if (p < Utilities.UR(0.0, 1.0)) {
					//Reject Change
					newFitness = oldFitness;
					newTour = oldTour;
				} else {
					//Accept Change
					newFitness = newFitness;
					newTour = newTour;
				}
			} else {
				oldFitness = newFitness;
				oldTour = newTour;
			}
			coolingRate = CalculateCoolingRate(temperature, numberOfIterations);
			temperature = temperature * (coolingRate - 1);
		}
		System.out.println("Fitness: " + newFitness);
	}

	private static double CalculateCoolingRate(double startingTemperature, int numberOfIterations) {

		double tIter, t0, tValue, coolingRate;
		int iter;

		tIter = 0.001;
		iter = numberOfIterations;

		tValue = tIter / startingTemperature;

		coolingRate = Math.pow(tValue, 1.0 / iter);

		return coolingRate;
	}
}