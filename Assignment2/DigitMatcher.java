package student;
/*
 * The DigitMatcher class is used to write the logic that will identify unlabeled Digits.
 * 
 * @author Ana Paula Centeno
 * @author Tom Swope
 */
public class DigitMatcher {

	/* Array holding the dataset of training digits */
	private Digit[] digits;

	/*
	 * Constructor 
	 * Initializes the digits array with digits from a file
	 * @param file containing the digits
	 * 
	 * File format:
	 * Each line in the file represents one handwritten digit.
	 * Each of these handwritten digits has a label, which notates the 
	 * number that the digit represents, and the 784 pixels from a 28x28 image 
	 * in a single row of comma separated values. 
	 * 
	 * The data for the digit is represented by the comma separated values.
	 *    - The first value specifies that the digit's lable. 
	 *    - The next 784 values represent pixels. A 0 means that the pixel is white; a 1 means that the pixel is black.
	 * 
	 */
	public DigitMatcher(String trainingInputFile)  {
		//WRITE YOUR CODE HERE
		int numDigits = StdIn.readInt(); // number of digits in the file
		String[] digits = new String[numDigits]; // final array of digits
		String[][] digi = new String [28][28]; //2D array of digits

		for(int i = 1; i < numDigits; i++){
			String[] temp = StdIn.readString().split(",");
			String label = temp[0];
			int count = 1;
			for(int row = 0; row < 28; i++){
				for(int col = 0; col < 28; i++){
					digi[row][col] = temp[count];  //filling 2D array with 1's and 0's
					count++;
				}
			}
			Digit d = new Digit(label, digi); //makes a new digit with the lable and 2D array
			digits[i] = d;
		}


	}

	/*
	 * Computes how similar the parameter digit is to every digit in the digits array.
	 * 
	 * The similarity is calculated by taking the difference (percentage of pixels) 
	 * between the two digits. USE the computeSimilarity method from the Digit class.
	 *
	 * @param digit is the test digit that needs to be identified. 
	 */
	public void computeSimilarity ( Digit digit ) {
		//WRITE YOUR CODE HERE

	}

	/*
	 * Assumes computeSimilarity() has been called to calculate the similaty between
	 * a test/unknown digit and every digit in the digits array.
	 * 
	 * Returns the Digit from the dataset that is most similar to the parameter 
	 * digit of computeSimilarity().
	 * 
	 * The most similar digit has the largest similarity value.
	 * 
	 */
	public Digit mostSimilar() {
		//WRITE YOUR CODE HERE
	}

	/*
	 * Find the Digit from the digits array with the greatest similarity
	 * between the indices [l, h].
	 * 
	 * @param low lowest array index to consider during the search
	 * @param high highest array index to consider during the search
	 * @return the index (from digits array) of the Digit with greatest similarity.
	 */
	public int findGreatestSimilarity (int low, int high) {
		//WRITE YOUR CODE HERE
	}

	/*
	 * Assumes computeSimilarity() has been called to calculate the similarity between
	 * a digit and every digit in the dataset of digits.
	 * 
	 * Rank the Digits in the digits dataset by similarity.
	 * 
	 * Reorders the digits dataset to contain the most similar digit at index 0, 
	 * the next most similar at index 1 and so on.
	 * 
	 * for each index i in the digits dataset
	 * 	   find the most similar digit between index i and the end of the array
	 *     swap digit at index i with the digit at the index where the most similar digit lies
	 */
	public void rankBySimilarity() {
		//WRITE YOUR CODE HERE
	}

	/*
	 * Depends on rankBySimilarity()
	 * 
	 * Steps: 
	 * 1. Find the k nearest neighbors based on similarity (k first items in digits array).
	 * 2. Of the k neighbors, finds the label that appears most often.
	 * 
	 * @param k 
	 * @return the label that occurs the most within the first k objects in digits.
	 */
	public int kNearestNeighbors (int k) {
		//WRITE YOUR CODE HERE
	}

	/*
	 * Depends on rankBySimilarity()
	 * 
	 * 1. Find the k nearest neighbors based on similarity.
	 * 2. For each of the k neighbors, compute the votes (based on similarity).
	 * 3. Find the label with the highest confidence level.
	 * 
	 * @param k 
	 * @return the label that occurs the most within the first k objects in digits.
	 */
	public int weightedKNearestNeighbors (int k) {
		//WRITE YOUR CODE HERE
	}


	/*
	 * Returns the dataset of digits. (DO NOT REMOVE)
	 */
	public Digit[] getDigits() {
		return digits;
	}

	/*
	 * Prints the dataset of digits. (DO NOT REMOVE)
	 */
	public void printDigits() {
		for ( Digit digit : digits ) {
			System.out.println("Label: " + digit.getLabel());
			digit.printPixels();
			System.out.println();
		}
	}
}
