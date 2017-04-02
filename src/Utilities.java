import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
}