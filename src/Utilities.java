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
        for (int i = 1; i <= numberOfCities; i++) {
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
     *
     * @param numberOfCities number of cities to visit within the tour
     * @param tour           a list of integers of size N (tour taken by the salesman)
     * @param matrix         an NxN matrix containing the distances between each city
     * @return the tour length s (the fitness, lower is better)
     */
    private static double FitnessFunction(int numberOfCities, int[] tour, double[][] matrix) {
        double s = 0.0;
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

    public static List<Integer> Swap(List<Integer> tour) {
        int i = 0, j = 0;
        while (i == j) {
            i = UI(1, tour.size());
            j = UI(1, tour.size());
        }

        int temp = tour.get(i);
        tour.set(i, tour.get(i));
        tour.set(j, temp);

        return tour;
    }

    public static double ScoreTour(List<Integer> tour) {
        double score = 0;
        int tourSize = tour.size();
        int cityA, cityB;
        for (int i = 0; i <= tourSize; i++) {
            cityA = tour.get(i);
            cityB = tour.get(i + 1);
            score = score + GetDistance(cityA, cityB);
        }

        //Return to the starting city
        cityA = tour.get(tourSize);
        cityB = tour.get(0);
        score = score + GetDistance(cityA, cityB);

        return score;
    }

    /**
     * Calculates the distance between city A and B
     *
     * @param a city A
     * @param b city B
     * @return distance between city A and B
     */
    private static double GetDistance(int a, int b) {
        //Remember: Java is ROW major, this means that ROWs come first
        double distance = Lab15.distanceArray[a][b];
        return distance;
    }
}