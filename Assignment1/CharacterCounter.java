public class CharacterCounter {
    public static void main(String[] args){
        //setting output file
        StdIn.setFile(args[0]);


        //making the main aray of strings and assing the chars to each index
        String[] mainList = new String[128]; 
        for(int i = 32; i< 128; i++){
            char temp = (char) i;
            String temp2 = String.valueOf(temp);
            mainList[i] = temp2;
        }
        //filling in the decimal value
        for(int i = 0; i < 128; i++){
            mainList[i] = (mainList[i] + "," + i);
        }

        //making a seperate arry to find the number of time a char appears
        int[] counter = new int[128];
        //filling the array with zeros
        for(int i = 32; i < 128; i++){
            counter[i] = 0;
        }
        //filling in the array with the count of char's
        int count = 0;
        while(StdIn.hasNextChar()){
            int temp = StdIn.readChar();
            temp = (int)temp;
            counter[temp]++;
            count++;
        }
        //System.out.println(count); //test
        //adding the occurrences number to the main list
        for(int i = 32; i < 128; i++){
            mainList[i] = (mainList[i] + "," + counter[i]);
        }

        //doing the same for the frequency
        double[] frq = new double[128];
        for(int i = 32; i < 128; i++){
            frq[i] = 0;
        }
        for(int i = 32; i < 128; i ++){
            frq[i] = ((double)counter[i]/count)*100;
        }
        for(int i = 0; i < 128; i++){
            mainList[i] = (mainList[i] + "," + frq[i]);
        }

        
        StdOut.setFile(args[1]);
        for(int i = 32; i < 127; i++){
            StdOut.println(mainList[i]);
        }
        

        
    }
}
