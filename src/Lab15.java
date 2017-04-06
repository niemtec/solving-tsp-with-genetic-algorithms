/**
 * The Travelling Salesperson Problem
 * Laboratory 15 Worksheet
 * CS2004 Algorithms and their Applications
 * Brunel University London
 * Created by Jakub Adrian Niemiec (@niemtec) on 2017-03-25.
 * Student ID: 1500408
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		int temperature;
		List<Integer> tour;

		Utilities.LoadDataFile(200);
		//Generate the first base tour
		tour = Utilities.PopulateCities(matrixSize);

		//Set a random point in the search space
		tour = Utilities.PermuteTour(tour);
		System.out.println("Tour: " + tour);

		System.out.println("=== Computing RRHC... Quiet Please ===");
		Algorithms.RRHC(tour, numberOfIterations);

		System.out.println("=== Calculating Temperature for SHC ===");
		temperature = CalculateStochasticTemperature(distanceArray, numberOfIterations, tour);
		System.out.println("Best Temperature: " + temperature);

		System.out.println("=== Computing SHC... Quiet Please ===");
		Algorithms.SHC(tour, numberOfIterations, temperature, true);

		System.out.println("=== Computing RMHC... Quiet Please ===");
		Algorithms.RMHC(tour, numberOfIterations);
	}

	public static int CalculateStochasticTemperature(double[][] distanceArray, int numberOfIterations, List<Integer> tour) {
		int temperature;
		ArrayList<Double> fitnessValues = new ArrayList<>();
		for (int i = 0; i < 10000; i++) {
			double currentFitness = Algorithms.SHC(tour, numberOfIterations, i, false);
			fitnessValues.add(currentFitness);
		}

		double minimumFitness = Collections.min(fitnessValues);
		temperature = fitnessValues.indexOf(minimumFitness);
		return temperature;
	}
}