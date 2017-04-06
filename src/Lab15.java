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
		int numberOfRepeats;
		int numberOfIterations = 10000;
		List<Integer> tour;

		Utilities.LoadDataFile(48);
		//Generate the first base tour
		tour = Utilities.PopulateCities(matrixSize);

		//Set a random point in the search space
		tour = Utilities.PermuteTour(tour);
		System.out.println("Tour: " + tour);
		Algorithms.RRHC(tour, numberOfIterations);
		Algorithms.SHC(tour, numberOfIterations);
		Algorithms.RMHC(tour, numberOfIterations);
	}

	public static double CalculateT(double currentFitness, double oldFitness) {
		double t, p, fitnessDifference, temp;

		p = 0.03; //probability extrapolated from experiments
		fitnessDifference = currentFitness - oldFitness;

		temp = 1 / p;
		temp = temp - 1;
		temp = Math.log(temp);

		t = fitnessDifference / temp;

		return t;
	}
}