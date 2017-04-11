import java.util.ArrayList;

public class Performance {
	/**
	 * Calculates the efficiency of a given tour based on its fitness by comparing it to an optimal solution fitness
	 *
	 * @param fitness fitness of the solution you wish to score against optimal tour
	 * @return percentage efficiency of a given fitness
	 */
	static double CalculateEfficiency(double fitness) {
		ArrayList<Integer> optimalTour = new ArrayList<>();
		double efficiency;
		try {
			optimalTour = TSP.ReadIntegerFile("data/TSP_" + Main.numberOfCities + "_OPT.txt");
		} catch (Exception e) {
			optimalTour = new ArrayList<>();
		}

		int tourSize = optimalTour.size();
		if (tourSize == 0) {
			efficiency = CalculateEfficiencyOfMST(fitness);
		} else {
			efficiency = CalculateEfficiencyOfOT(fitness, optimalTour);
		}

		//Round up the efficiency to 2dp
		Tools.RoundDecimal(efficiency);
		return efficiency;
	}

	/**
	 * Calculates the efficiency against the Optimal Tour
	 *
	 * @param fitness     fitness to be compared vs optimal tour
	 * @param optimalTour optimal tour to compare vs fitness
	 * @return efficiency (as percentage)
	 */
	static double CalculateEfficiencyOfOT(double fitness, ArrayList<Integer> optimalTour) {
		double optimalTourFitness = CalculateFitness(optimalTour);
		double efficiency = (optimalTourFitness / fitness) * 100;

		efficiency = Tools.RoundDecimal(efficiency);
		return efficiency;
	}

	/**
	 * Calculates the efficiency against the Minimum Spanning Tree
	 *
	 * @param fitness fitness to be compared vs minimum spanning tree
	 * @return efficiency (as percentage)
	 */
	static double CalculateEfficiencyOfMST(double fitness) {
		double[][] mstArray = CalculateMST();
		double mstFitnessScore = CalculateFitnessOfMST(mstArray);
		double efficiency = (mstFitnessScore / fitness) * 100;

		efficiency = Tools.RoundDecimal(efficiency);
		return efficiency;
	}

	/**
	 * Calculates the fitness of a minimum spanning tree
	 *
	 * @param mstArray minimum spanning tree array to calculate fitness for
	 * @return fitness score of the mst
	 */
	static double CalculateFitnessOfMST(double[][] mstArray) {
		double fitness = 0.0;
		for (int i = 0; i < mstArray.length; i++) {
			for (int j = 0; j < mstArray[i].length; j++) {
				fitness = fitness + mstArray[i][j];
			}
		}
		double fitnessScore = fitness / 2;
		return fitnessScore;
	}

	/**
	 * Calculates the minimum spanning tree using Prims MST method
	 *
	 * @return minimum spanning tree of the distance array
	 */
	static double[][] CalculateMST() {
		double[][] mstOfArray = MST.PrimsMST(Main.distanceArray);
		return mstOfArray;
	}

	/**
	 * Calculates fitness of a given tour
	 *
	 * @param tour tour to calculate fitness for
	 * @return fitness of a given tour
	 */
	static double CalculateFitness(ArrayList<Integer> tour) {
		int startCity, endCity, cityA, cityB;
		double s = 0.0;

		for (int i = 0; i < Main.numberOfCities - 1; i++) {
			cityA = tour.get(i);
			cityB = tour.get(i + 1);
			s = s + Main.distanceArray[cityA][cityB];
		}

		startCity = tour.get(0);
		endCity = tour.get(Main.numberOfCities - 1);
		//Add the return trip
		s = s + Main.distanceArray[endCity][startCity];

		s = Tools.RoundDecimal(s);
		return s;
	}

	/**
	 * Calculates the distance between two given cities
	 *
	 * @param a city A
	 * @param b city B
	 * @return distance between city A and B
	 */
	public static double GetDistance(int a, int b) {
		//Remember: Java is ROW major, this means that ROWs come first
		double distance = Main.distanceArray[a][b];
		//System.out.println("Distance: " + distance);
		return distance;
	}
}
