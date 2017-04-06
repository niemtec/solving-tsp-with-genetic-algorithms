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
		int temperature;
		List<Integer> tour;

		Utilities.LoadDataFile(48);
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

		for (int i = 0; i < 4; i++) {
			System.out.println("=== Computing SHC... Quiet Please ===");
			Algorithms.SHC(tour, numberOfIterations, temperature, true);
		}
		System.out.println("=== Computing RMHC... Quiet Please ===");
		Algorithms.RMHC(tour, numberOfIterations);
	}

	public static int CalculateStochasticTemperature(double[][] distanceMatrix, int numberOfIterations, List<Integer> tour) {
		int temperature = 0;
		double totalDistance = GetTotalDistance(distanceMatrix);
		double t, currentFitness, bestK = 0;

		Map<Integer, Double> map = new HashMap<>();

		for (int k = 10; k < 100000; k++) {
			t = totalDistance / k;
			double fitnessScore = Algorithms.SHC(tour, 1000, t, false);

			//Store the value in the map
			map.put(k, fitnessScore);
		}

		double smallestValue = map.values().stream().min(Double::compare).get();
		int k = GetSmallestKey(map);
		System.out.println("Smallest Value: " + smallestValue);
		System.out.println("Smallest Key: " + k);

//		temperature = (int) (totalDistance / k);
//		System.out.println("K: " + k);
//		System.out.println("Temp: " + temperature);

		temperature = (int) (totalDistance / k);

		return temperature;
	}

	private static double GetTotalDistance(double[][] distanceMatrix) {
		int yLength = distanceMatrix[0].length;
		int xLength = distanceMatrix[1].length;
		double totalDistance = 0.0;
		double t;

		for (int y = 0; y < yLength; y++) {
			for (int x = 0; x < xLength; x++) {
				totalDistance = totalDistance + distanceMatrix[y][x];
			}
		}
		return totalDistance;
	}

	private static int GetSmallestKey(Map<Integer, Double> map) {
		int minKey = Integer.MAX_VALUE;
		double minValue = Integer.MAX_VALUE;
		for (Map.Entry<Integer, Double> entry : map.entrySet()) {
			int key = entry.getKey();
			double value = entry.getValue();
			if (value < minValue) {
				minValue = value;
				minKey = key;
			}
		}

		return minKey;
	}

	private static double FindLowestValue(double[][] valueArray) {
		double key, lowestKey = Integer.MAX_VALUE;
		double value, lowestValue = Integer.MAX_VALUE;

		//Find the lowest value in the array
		for (int i = 2; i < valueArray.length; i++) {
			key = valueArray[i][0];
			value = valueArray[i][1];

			if (value < lowestValue) {
				lowestKey = key;
				lowestValue = value;
			}
		}

		return lowestKey;
	}
}