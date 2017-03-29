import java.util.List;

/**
 * The Travelling Salesperson Problem
 * Laboratory 15 Worksheet
 * CS2004 Algorithms and their Applications
 * Brunel University London
 * Created by Jakub Adrian Niemiec (@niemtec) on 2017-03-25.
 * Student ID: 1500408
 */

public class Lab15 {
	public static void main(String args[]) {
		//Determine the length of the array first by loading it temporarily and measuring it
		double[][] matrixDimensionCountArray = TSP.ReadArrayFile("data/TSP_48.txt", " ");
		int matrixSize = matrixDimensionCountArray.length;

		//Create the array object
		double[][] citiesArray = new double[matrixSize][matrixSize];
		//Load the array to memory
		citiesArray = TSP.ReadArrayFile("data/TSP_48.txt", " ");

		//Representation vector
		int[] representation = new int[matrixSize];

	}

	/**
	 * Fitness Function for the Travelling Salesman Problem
	 *
	 * @param numberOfCities number of cities to visit within the tour
	 * @param tour           a list of integers of size N (tour taken by the salesman)
	 * @param matrix         an NxN matrix containing the distances between each city
	 * @return the tour length s (the fitness, lower is better)
	 */
	private static int FitnessFunction(int numberOfCities, int[] tour, double[][] matrix) {
		int s = 0;
		for (int i = 1; i <= numberOfCities - 1; i++) {
			int a = tour[i];
			int b = tour[i + 1];
			s = s + GetDistance(a, b);
		}
		int endCity = tour[numberOfCities];
		int startCity = tour[1];
		s = s + GetDistance(endCity, startCity);

		return s;
	}

	private static void GenerateCityNumbers(int numberOfCities) {
		//double[]
	}

	private static void RandomPermutation(int numberOfCities) {
		List<Integer> p;
		List<Integer> t; //empty list
	}

	/**
	 * Prints array as a matrix format
	 *
	 * @param array input array to be printed
	 */
	private static void PrintArray(double[][] array) {
		int yLength = array[0].length;
		int xLength = array[1].length;
		int y, x;

		try {
			for (y = 0; y < yLength; y++) {
				for (x = 0; x < xLength; x++) {
					System.out.print(array[y][x] + " ");
				}
				System.out.println();
			}
			System.out.println("==============================");
		} catch (ArrayIndexOutOfBoundsException a) {
			System.out.println();
			System.out.println("WARNING: INCORRECT MATRIX ENTERED. INDEX OUT OF BOUNDS.");
			System.out.println();
		}
	}
}
