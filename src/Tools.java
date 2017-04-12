import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class Tools {
	//Shared random object
	static private Random rand;

	static ArrayList<Integer> SmallChange(ArrayList<Integer> tour) {
		int i = 0, j = 0;
		int tourSize = tour.size();
		//Choose two random elements of T where i ≠ j
		while (i == j) {
			i = UI(0, tourSize - 1);
			j = UI(0, tourSize - 1);
		}

		//Swap the elements
		int temp = tour.get(i);
		int temp2 = tour.get(j);

		//Replace the first element
		tour.set(i, temp2);
		tour.set(j, temp);

		return tour;
	}

	//Create a uniformly distributed random integer between aa and bb inclusive
	private static int UI(int aa, int bb) {
		int a = Math.min(aa, bb);
		int b = Math.max(aa, bb);
		if (rand == null) {
			rand = new Random();
			rand.setSeed(System.nanoTime());
		}
		int d = b - a + 1;
		int x = rand.nextInt(d) + a;
		return (x);
	}

	/**
	 * Method used to load the TSP distance data files of various sizes
	 *
	 * @param dataSize the size of the data file
	 */
	static double[][] LoadDataFile(int dataSize) {
		String baseFileName = "data/TSP_";
		String baseFileExtension = ".txt";
		String baseFileLocation = baseFileName + Integer.toString(dataSize) + baseFileExtension;

		double[][] array;
		array = TSP.readArrayFile(baseFileLocation, " ");

		System.out.println("> TSP DATA FILE LOADED.");

		return array;
	}

	/**
	 * Populates an array list from 0 to n
	 *
	 * @param numberOfCities number of cities to generate from 0 to n
	 * @return 0 to n array list of increasing integers
	 */
	public static ArrayList<Integer> PopulateCities(int numberOfCities) {
		ArrayList<Integer> tour = new ArrayList<>();
		//Populate startingPermutation with city numbers
		for (int i = 0; i < numberOfCities; i++) {
			tour.add(i);
		}
		return tour;
	}

	//Create a uniformly distributed random double between a and b inclusive
	static double UR(double a, double b) {
		if (rand == null) {
			rand = new Random();
			rand.setSeed(System.nanoTime());
		}
		return ((b - a) * rand.nextDouble() + a);
	}

	// Rounds decimals to 3d.p. then returns as string for printing
	static double RoundDecimal(double decimal) {
		double outputAsDouble;

		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		String output = df.format(decimal);

		outputAsDouble = Double.parseDouble(output);
		return outputAsDouble;
	}


	/**
	 * Rings a system bell, for when you have to run long calculations
	 */
	public static void RingSystemBell() {
		java.awt.Toolkit.getDefaultToolkit().beep();
	}

	/**
	 * Calculates the average of a given ArrayList of doubles
	 *
	 * @param array input array list
	 * @return average of a given array list
	 */
	private static double CalculateAverage(ArrayList<Double> array) {
		double total = 0, length;
		length = array.size();

		for (int i = 0; i < length; i++) {
			total = total + array.get(i);
		}

		double average = total / length;

		return average;
	}

	/**
	 * Calculates the probability of accepting a solution given fitness values and temperature
	 *
	 * @param newFitness  fitness of modified tour
	 * @param oldFitness  fitness of tour prior to modification
	 * @param temperature temperature for the given algorithm
	 * @return acceptence probability of the given solution
	 */
	static double CalculateAcceptanceProbability(double newFitness, double oldFitness, double temperature) {
		double deltaFitness = newFitness - oldFitness;
		double p = 1 / (1 + Math.exp(deltaFitness / temperature));

		return p;
	}

	static double CalculateCoolingRate(double startingTemperature, int numberOfIterations) {
		double tIter, tValue, coolingRate, powerValue;
		int iter;

		tIter = 0.001; //Number from lecture slides
		iter = numberOfIterations;

		tValue = tIter / startingTemperature;
		powerValue = 1.0 / iter;

		coolingRate = Math.pow(tValue, powerValue);

		return coolingRate;
	}

	static double PR(double newFitness, double oldFitness, double temperature) {
		double changeInFitness = Math.abs(newFitness - oldFitness);
		changeInFitness = -1 * changeInFitness;
		double prScore = Math.exp(changeInFitness / temperature);
		return prScore;
	}

	private static double CalculateStochasticTemperature(ArrayList<Integer> tour) {
		ArrayList<Double> allTemperatures = new ArrayList<>();
		System.out.println("Calculating Average Stochastic Temperature");

		for (int i = 0; i < 5; i++) {
			System.out.print(".");
			for (int t = 5000; t < 10000; t++) {
				double fitness = Performance.CalculateFitness(Algorithms.SHC(tour, 1000, t, false));
				allTemperatures.add(fitness);
			}
		}
		double t = CalculateAverage(allTemperatures);
		System.out.println("Tep: " + t);
		System.out.println();
		return t;
	}

	/**
	 * Method handling file saving for each experiment ran, data exported a CSV txt file
	 *
	 * @param tour       tour to calculate the fitness for
	 * @param fileName   name of the file to save the results as
	 * @param appendMode appending mode (triggers addition of data or complete overwrite)
	 */
	static void saveResults(ArrayList<Integer> tour, String fileName, boolean appendMode) {
		double fitness = Performance.CalculateFitness(tour);
		printToFile(fitness,
				  Performance.CalculateEfficiencyOfMST(fitness),
				  Performance.CalculateEfficiency(fitness),
				  "results/" + Main.numberOfCities + "_" + Main.numberOfIterations + "_" + fileName + ".txt", appendMode);
	}

	/**
	 * Prints the fitness, mst, and op to a text file in the form of CSV
	 *
	 * @param fitness    fitness to be saved
	 * @param mst        mst to be saved
	 * @param op         op to be saved
	 * @param filename   name of the file
	 * @param appendMode appending mode (triggers addition of data or complete overwrite)
	 */
	private static void printToFile(double fitness, double mst, double op, String filename, boolean appendMode) {
		String fitnessString = Double.toString(fitness);
		String mstString = Double.toString(mst);
		String opString = Double.toString(op);

		try {
			FileWriter writer = new FileWriter(filename, appendMode);
			writer.write(fitnessString + "," + mstString + "," + opString);
			writer.write("\r\n");   // New Line
			writer.close();
		} catch (IOException e) {
			System.out.println("ERROR. SPECIFIED OUTPUT FILE NOT FOUND.");
			e.printStackTrace();
		}
	}
}
