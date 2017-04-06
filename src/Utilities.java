import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class Utilities {
	//Shared random object
	static private Random rand;

	//Create a uniformly distributed random integer between aa and bb inclusive
	public static int UI(int aa, int bb) {
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

	//Create a uniformly distributed random double between a and b inclusive
	public static double UR(double a, double b) {
		if (rand == null) {
			rand = new Random();
			rand.setSeed(System.nanoTime());
		}
		return ((b - a) * rand.nextDouble() + a);
	}

	public static ArrayList<Integer> PopulateCities(int numberOfCities) {
		ArrayList<Integer> tour = new ArrayList<>();
		//Populate startingPermutation with city numbers
		for (int i = 0; i < numberOfCities; i++) {
			tour.add(i);
		}
		return tour;
	}

	public static List<Integer> PermuteTour(List<Integer> tour) {
		List<Integer> resultantPermutation = new ArrayList<>();
		System.out.println("Initial List: " + tour);
		Collections.shuffle(tour);
		System.out.println("Shuffled List: " + tour);
		return tour;
	}

	/**
	 * Fitness Function for the Travelling Salesman Problem
	 * Calculates the total distance travelled by the salesman
	 *
	 * @param tour a list of integers of size N (tour taken by the salesman)
	 * @return the tour length s (the fitness, lower is better)
	 */
	public static double FitnessFunction(List<Integer> tour) {
		int numberOfCities = Lab15.matrixSize;
		int startCity, endCity, cityA, cityB;
		double s = 0.0;

		for (int i = 0; i < numberOfCities; i++) {
			if (i != numberOfCities - 1) {
				cityA = tour.get(i);
				cityB = tour.get(i + 1);
				s = s + GetDistance(cityA, cityB);
			} else {
				break;
			}
		}
		startCity = tour.get(0);
		endCity = tour.get(numberOfCities - 1);
		//Add the return trip
		s = s + GetDistance(endCity, startCity);
		return s;
	}

	/**
	 * Prints array as a matrix format
	 *
	 * @param array input array to be printed
	 */
	public static void PrintArray(double[][] array) {
		int xLength = array[0].length;
		int yLength = array[1].length;
		int y, x;

		try {
			for (x = 0; x < xLength; y++) {
				for (y = 0; y < yLength; y++) {
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

	/**
	 * Performs a random small change (a swap) on a given tour
	 *
	 * @param tour tour to be altered
	 * @return altered version of the tour
	 */
	public static List<Integer> Swap(List<Integer> tour) {
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


	/**
	 * Calculates the distance between city A and B
	 *
	 * @param a city A
	 * @param b city B
	 * @return distance between city A and B
	 */
	public static double GetDistance(int a, int b) {
		//Remember: Java is ROW major, this means that ROWs come first
		double distance = Lab15.distanceArray[a][b];
		//System.out.println("Distance: " + distance);
		return distance;
	}

	/**
	 * Method used to load the TSP distance data files of various sizes
	 *
	 * @param dataSize the size of the data file
	 */
	static void LoadDataFile(int dataSize) {
		String baseFileName = "data/TSP_";
		String baseFileExtension = ".txt";
		String baseFileLocation = baseFileName + Integer.toString(dataSize) + baseFileExtension;

		try {
			Lab15.matrixDimensionCountArray = TSP.ReadArrayFile(baseFileLocation, " ");
		} catch (Exception e) {
			System.out.println(e.getClass() + " === FILE NOT FOUND ===");
		}
		Lab15.matrixSize = Lab15.matrixDimensionCountArray.length;
		Lab15.distanceArray = new double[Lab15.matrixSize][Lab15.matrixSize];
		Lab15.distanceArray = TSP.ReadArrayFile(baseFileLocation, " ");

		System.out.println("=== TSP DATA LOADED ===");
	}


	// Rounds decimals to 3d.p. then returns as string for printing
	public static String RoundDecimal(double decimal) {
		DecimalFormat df = new DecimalFormat("#.###");
		df.setRoundingMode(RoundingMode.CEILING);
		String output = df.format(decimal);
		return output;
	}

	public static void RingSystemBell() {
		java.awt.Toolkit.getDefaultToolkit().beep();
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