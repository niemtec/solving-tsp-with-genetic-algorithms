//TODO Modify this to output permutations into a new array
public class Permutation {
    /**
     * Permutation function, will generate all possible permutations of a given array
     *
     * @param array array of integers to be permuted
     */
    public static void PermuteArray(int[] array) {
        PermuteElements(array, 0);
    }

    /**
     * Permutation function which shifts elements to generate permutations
     *
     * @param array array to be permuted recursively
     * @param index starting index, 0 by default
     */
    public static void PermuteElements(int[] array, int index) {
        //If we are at e last element - nothing left to permute
        if (index >= array.length - 1) {
            System.out.print("[");
            for (int i = 0; i < array.length - 1; i++) {
                System.out.print(array[i] + ", ");
            }

            if (array.length > 0) {
                System.out.print(array[array.length - 1]);
                System.out.println("]");
                return;
            }
        }

        for (int i = index; i < array.length; i++) {
            //For each index in the sub array array[index...end]
            //Swap the elements at the indices index and i
            int t = array[index];
            array[index] = array[i];
            array[i] = t;

            //Recurse on the sub array array[index+1...end]
            PermuteElements(array, index + 1);

            //Swap the elements back
            t = array[index];
            array[index] = array[i];
            array[i] = t;
        }
    }
}
