public class DigitMatcher {

	private Digit[] digits;

	
	public DigitMatcher(String trainingInputFile)  {
		//WRITE YOUR CODE HERE
		StdIn.setFile(trainingInputFile);
		int numDigits = Integer.parseInt(StdIn.readString()); // number of digits in the file
		
		digits = new Digit[numDigits]; 
		for(int i = 0; i < numDigits; i++){
			int[] digi = new int [784]; //array of ints to hold the pixels
			String[] temp = StdIn.readString().split(",");
			int label = Integer.parseInt(temp[0]);
			for(int j = 0; j < 784; j++){
				digi[j] = Integer.parseInt(temp[j+1]); //sets the pixels to the array
			}
			Digit d = new Digit(label, digi); //makes a new digit with the lable and array
			digits[i] = d;
		}
	}

	
	
	public void computeSimilarity ( Digit digit ) {
		//WRITE YOUR CODE HERE
		for(int i = 0; i < digits.length; i++){
			digits[i].computeSimilarity(digit); 
		}		
	}


	public Digit mostSimilar() {
		//WRITE YOUR CODE HERE
		Digit holder = digits[0]; //holds the first digit in the array
		for(int i = 0; i < digits.length; i++){
			if(digits[i].getSimilarity() > holder.getSimilarity()){
				holder = digits[i]; //sets holder to the most similar digit
			}
		}
		return holder;
	}



		public int findGreatestSimilarity (int low, int high) {
		//WRITE YOUR CODE HERE
		int index = low;
		Digit temp = digits[low]; //holds the first digit in the array
		for(int i = low; i <= high; i++){
			if(digits[i].getSimilarity() > temp.getSimilarity()){
				temp = digits[i]; //sets temp to the most similar digit
				index = i;
			}
		}
		return index; //returns the index of the most similar digit
	}

	
	public void rankBySimilarity() {
		//WRITE YOUR CODE HERE
		for(int i = 0; i < digits.length; i++){
			int index = findGreatestSimilarity(i, digits.length-1); //finds the most similar digit in the array
			Digit temp = digits[i]; //holds the first digit in the array
			digits[i] = digits[index]; //sets the first digit to the most similar digit
			digits[index] = temp; //sets the most similar digit to the first digit
		}	
		//for(int i = 0; i < digits.length; i++){
		//	System.out.print(digits[i].getSimilarity() + " ");
	//}
}

	
	public int kNearestNeighbors(int k) {
		//WRITE YOUR CODE HERE\
		int highest = 0; //holds the first digit in the array
		int[] counter = new int[10];
		for(int i = 0; i < k; i++){
			counter[digits[i].getLabel()]++; 
		}
		for(int i = 1; i < counter.length; i++){
			if(counter[i] > counter[highest]){
				
				highest = i; //sets highest to the most common digit
			}
		}
		return highest;
	}

	//highest sum of similarity scores of the k nearest neighbors with the same label
	public int weightedKNearestNeighbors (int k) {
		//WRITE YOUR CODE HERE
		int highest = 0;

		//adds the digits to a new array based on their labels
		Digit[] counter = new Digit[10]; //holds the first digit in the array
		for(int i = 0; i < k; i++){
			counter[digits[i].getLabel()] = digits[i]; //sets the first digit to the most similar digit
		}


		for(int i = 0; i < counter.length; i++){
			if(counter[i] != null){
				if(counter[i].getSimilarity() > counter[highest].getSimilarity()){
					highest = i; //sets highest to the most common digit
				}
			}
		}
		
		return highest;
	}



	public Digit[] getDigits() {
		return digits;
	}

	
	public void printDigits() {
		for ( Digit digit : digits ) {
			System.out.println("Label: " + digit.getLabel());
			digit.printPixels();
			System.out.println();
		}
	}
}
