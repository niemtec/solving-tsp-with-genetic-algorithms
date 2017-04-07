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
		double oldFitness, newFitness;
		ArrayList<Double> temperature = new ArrayList<>();

		//TODO Determine the starting temperature

		newTour = tour;
		newFitness = Utilities.FitnessFunction(newTour);

		for (int i = 0; i < numberOfIterations - 1; i++) {
			//Save old values before making any changes
			oldTour = newTour;
			oldFitness = newFitness;

			//Make a small change
			newTour = Utilities.Swap(oldTour);
			//Calculate newest fitness
			newFitness = Utilities.FitnessFunction(newTour);

			double changeInFitness = Math.abs(newFitness - oldFitness);
			double coolingRate = CalculateCoolingRate(0.001, numberOfIterations);
			temperature.add(0, changeInFitness);


			double temperatureValue = temperature.get(i);

			if (newFitness > oldFitness) {
				//Calculate the probability of accepting new solution
				double p = Efficiency.PR(newFitness, oldFitness, temperatureValue);

				if (p < Utilities.UR(0, 1)) {
					//Reject the change
					newFitness = oldFitness;
					newTour = oldTour;
				} else {
					//Accept the change
					newFitness = newFitness;
					newTour = newTour;
				}
			} else {
				oldFitness = newFitness;
				oldTour = newTour;
			}
			temperature.add(i + 1, coolingRate * (temperature.get(i)));
		}
		System.out.println("Fitness: " + newFitness);

	}

	private static double CalculateCoolingRate(double startingTemperature, int numberOfIterations) {
		//TODO: Completely wrong: fix.
		double squareRoot = 0.001 / startingTemperature;
		double powerValue = 1 / numberOfIterations;
		double coolingRate = Math.pow(squareRoot, powerValue);

		return coolingRate;
	}
}