public class AirParticles {
    public static double findHighestLevel(double[] communities){
        double highest = communities[0];
        for(int i = 0; i < communities.length; i++){
            if(communities[i] > highest){
                highest = communities[i];
            }
        }
       
        return highest;
    }
    public static void main(String[] args){
        int num = StdIn.readInt(); //nummber of lines in the data set
        double[] arr = new double[num];
        for(int i = 0; i < num; i++){
            arr[i] = StdIn.readDouble();
        }
        double a = findHighestLevel(arr);
        System.out.println("Highest PM2.5 Level is " + a);
    }
}
