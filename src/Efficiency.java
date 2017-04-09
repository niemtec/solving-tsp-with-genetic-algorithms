import java.util.ArrayList;

/**
 * Created by Jakub Adrian Niemiec (@niemtec) on 2017-04-07.
 */
public class Efficiency {
	/**
	 * Calculates the minimum spanning tree using PrimsMST
	 *
	 * @param distanceArray array of distances used to calculate the minimum tree for
	 * @return 2d array (double) of the minimum spanning tree
	 */
	private static double[][] CalculateMST(double[][] distanceArray) {
		double[][] MSTofArray = MST.PrimsMST(distanceArray);
		return MSTofArray;
	}

	/**
	 * Calculates the overall efficiency of the MST (its fitness)
	 *
	 * @param distanceArray array of distances used for fitness calculations
	 * @param fitness       comparative fitness to determine the efficiency of MST
	 * @return efficiency of the mst (as percentage)
	 */
	private static double CalculateEfficiencyOfMST(double[][] distanceArray, double fitness) {
		double[][] mstArray = CalculateMST(distanceArray);
		double mstFitnessScore = CalculateFitnessOfMST(mstArray);
		double efficiency = (mstFitnessScore / fitness) * 100;

		return efficiency;
	}

	/**
	 * Calculates the fitness of a given minimum spanning tree
	 *
	 * @param mstArray an MST array
	 * @return fitness of the given MST
	 */
	private static double CalculateFitnessOfMST(double[][] mstArray) {
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
	 * Calculates the overall efficiency of a given algorithm
	 *
	 * @param distanceArray array of distances within the given dataset
	 * @param fitness       fitness used to calculate the efficiency of MST
	 * @param datasetSize   size of the current dataset (48, 200, etc...)
	 * @return efficiency score of the given algorithm (double)
	 */
	private static double CalculateEfficiencyOfAlgorithm(double[][] distanceArray, double fitness, int datasetSize) {
		double efficiencyScore;

		ArrayList<Integer> optimalTour = TSP.ReadIntegerFile("data/TSP_" + datasetSize + "_OTP.txt");

		int tourSize = optimalTour.size();
		if (tourSize == 0) {
			efficiencyScore = CalculateEfficiencyOfMST(distanceArray, fitness);
		} else {
			efficiencyScore = CalculateEfficiencyOfOptimalTour(fitness, optimalTour);
		}

		return efficiencyScore;
	}

	/**
	 * Calculates the overall efficiency of the given optimal tour
	 *
	 * @param fitness     fitness to compare against
	 * @param optimalTour tour to be calculated
	 * @return overall efficiency (as percentage)
	 */
	private static double CalculateEfficiencyOfOptimalTour(double fitness, ArrayList<Integer> optimalTour) {
		double optimalTourFitness = Utilities.FitnessFunction(optimalTour);
		double efficiency = (optimalTourFitness / fitness) * 100;

		return efficiency;
	}

	static double PR(double newFitness, double oldFitness, double temperature) {
		double changeInFitness = Math.abs(newFitness - oldFitness);
		changeInFitness = -1 * changeInFitness;
		double prScore = Math.exp(changeInFitness / temperature);
		return prScore;
	}
}
