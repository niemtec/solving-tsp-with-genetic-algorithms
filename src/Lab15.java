/**
 * The Travelling Salesperson Problem
 * Laboratory 15 Worksheet
 * CS2004 Algorithms and their Applications
 * Brunel University London
 * Created by Jakub Adrian Niemiec (@niemtec) on 2017-03-25.
 * Student ID: 1500408
 */

import javax.rmi.CORBA.Util;
import java.util.Arrays;
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
		int numberOfRepeats = 1;
		int numberOfIterations = 100;
		List<Integer> tour;

		Utilities.LoadDataFile(48);
		//Generate the first base tour
		tour = Utilities.PopulateCities(matrixSize);

		//Set a random point in the search space
		tour = Utilities.PermuteTour(tour);
		System.out.println("Tour: " + tour);
		RMHC(tour, numberOfIterations, true);

	}

	private static List<Integer> RMHC(List<Integer> tour, int numberOfIterations, boolean printIterations) {
		List<Integer> oldTour, currentTour;
		double oldFitness, currentFitness;

		currentTour = tour;
		currentFitness = Utilities.FitnessFunction(currentTour);

		System.out.println("=== Computing RMHC... Quiet Please ===");

		for (int i = 1; i <= numberOfIterations; i++) {
			if (printIterations == true) {
				System.out.println("Tour: " + currentTour);
				System.out.println(i + ") Fitness: " + currentFitness);
			}
			//Save old values before making any changes
			oldTour = currentTour;
			oldFitness = currentFitness;

			//Make a small change
			currentTour = Utilities.Swap(oldTour);
			System.out.println("NEW TOUR: " + currentTour);
			//Calculate newest fitness
			currentFitness = Utilities.FitnessFunction(currentTour);

			//We want to get the lowest possible tour length
			if (currentFitness >= oldFitness) {
				currentFitness = oldFitness;
				currentTour = oldTour;
			}
		}
		System.out.println("Fitness: " + currentFitness);
		return currentTour;
	}
}
