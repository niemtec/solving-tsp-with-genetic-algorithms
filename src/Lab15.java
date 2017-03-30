/**
 * The Travelling Salesperson Problem
 * Laboratory 15 Worksheet
 * CS2004 Algorithms and their Applications
 * Brunel University London
 * Created by Jakub Adrian Niemiec (@niemtec) on 2017-03-25.
 * Student ID: 1500408
 */

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
		int numberOfRepeats = 0;
		int numberOfIterations = 0;
		Utilities.LoadDataFile(48);

		//Generate the first base tour
		List<Integer> tour = Utilities.PopulateCities(matrixSize);
		System.out.println(tour);
		//TODO Distance Array not loading
		System.out.println(distanceArray);
		RMHC(tour, numberOfIterations);


	}

	private static List<Integer> RMHC(List<Integer> tour, int numberOfIterations) {
		List<Integer> oldTour, newTour, currentTour;
		double oldFitness, newFitness, currentFitness;

		//TODO Get the starting fitness of the current tour
		currentFitness = Utilities.ScoreTour(tour);
		currentTour = tour;

		System.out.println("=== Computing RMHC... Quiet Please ===");

		for (int i = 1; i <= numberOfIterations; i++) {
			//Save old values before making any changes
			oldFitness = currentFitness;
			oldTour = currentTour;

			//TODO Make a small change
			currentTour = Utilities.Swap(tour);
			//TODO Get Fitness of new tour
			currentFitness = Utilities.ScoreTour(currentTour);
			//TODO Compare fitnesses
			//We want to get the lowest possible tour length
			if (currentFitness >= oldFitness) {
				currentFitness = oldFitness;
				currentTour = oldTour;
			}
			//TODO choose next values
		}
		System.out.println("Fitness: " + currentFitness);
		return currentTour;
	}
}
