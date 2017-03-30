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
}