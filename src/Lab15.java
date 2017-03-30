import java.util.ArrayList;
import java.util.List;

/**
 * The Travelling Salesperson Problem
 * Laboratory 15 Worksheet
 * CS2004 Algorithms and their Applications
 * Brunel University London
 * Created by Jakub Adrian Niemiec (@niemtec) on 2017-03-25.
 * Student ID: 1500408
 */

/**
 * SA, RRHC, SHC, RMHC
 */

public class Lab15 {
    //Determine the length of the array first by loading it temporarily and measuring it
    static double[][] matrixDimensionCountArray = TSP.ReadArrayFile("data/TSP_48.txt", " ");
    static int matrixSize = matrixDimensionCountArray.length;
    //Create the array object
    public static double[][] distanceArray = new double[matrixSize][matrixSize];

    public static void main(String args[]) {
        //Load the array to memory
        distanceArray = TSP.ReadArrayFile("data/TSP_48.txt", " ");

    }

    private static void LoadDataFile(int dataSize) {
        String baseFileName = "data/TSP_";
        String baseFileExtension = ".txt";
        String baseFileLocation = baseFileName + Integer.toString(dataSize) + baseFileExtension;

        matrixDimensionCountArray = TSP.ReadArrayFile(baseFileLocation, " ");
        matrixSize = matrixDimensionCountArray.length;
        distanceArray = new double[matrixSize][matrixSize];
        distanceArray = TSP.ReadArrayFile(baseFileLocation, " ");

        System.out.println(">>> TSP DATA LOADED <<<");
    }

}
