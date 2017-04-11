import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The Travelling Salesperson Problem
 * Laboratory 15 Worksheet
 * CS2004 Algorithms and their Applications
 * Brunel University London
 * Created by Jakub Adrian Niemiec (@niemtec) on 2017-03-25.
 * Student ID: 1500408
 */

public class Main {
	static int numberOfCities = 48;
	static int numberOfIterations = 10000;
	static double[][] distanceArray;

	public static void main(String args[]) {
		ArrayList<Integer> tour; //Stores the current tour

		distanceArray = Tools.LoadDataFile(numberOfCities);

		//Populate the cities
		tour = Tools.PopulateCities(numberOfCities);
		//Pick a random starting point in the search space
		Collections.shuffle(tour);

		//Starting temperature is 2.5% of the total problem space as per lecture slides
		double stochasticTemperature = numberOfCities * 0.025;

		System.out.println("Calculating the cooling rate");
		double coolingRate = Tools.CalculateCoolingRate(stochasticTemperature, numberOfIterations);
		System.out.println("Cooling Rate: " + coolingRate);

		System.out.println("Simulated Annealing");
		//Starting temperature derived from running various experiments
		ArrayList<Integer> saTour = Algorithms.SA(tour, 10000.0, numberOfIterations, coolingRate, true);
		printToFile(Performance.CalculateFitness(saTour), "results/SimulatedAnnealing.txt");

		System.out.println("Random Restart Hill Climber");
		ArrayList<Integer> rrhcTour = Algorithms.RRHC(tour, numberOfIterations, true);
		printToFile(Performance.CalculateFitness(rrhcTour), "results/RandomRestartHillClimbing.txt");

		System.out.println("Stochastic Hill Climber");
		ArrayList<Integer> shcTour = Algorithms.SHC(tour, numberOfIterations, stochasticTemperature, true);
		printToFile(Performance.CalculateFitness(shcTour), "results/StochasticHillClimber.txt");

		System.out.println("Random Mutation Hill Climber");
		ArrayList<Integer> rmhcTour = Algorithms.RMHC(tour, numberOfIterations, true);
		printToFile(Performance.CalculateFitness(rmhcTour), "results/RandomMutationHillClimber.txt");
	}

	private static void printToFile(double fitness, String filename) {
		String fitnessString = Double.toString(fitness);

		try {
			FileWriter writer = new FileWriter(filename, true);
			writer.write(fitnessString);
			writer.write("\r\n");   // New Line
			writer.close();
		} catch (IOException e) {
			System.out.println("ERROR. SPECIFIED OUTPUT FILE NOT FOUND.");
			e.printStackTrace();
		}
	}
}

