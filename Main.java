package pkg8puzzle;
import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class Main {

ArrayList total_counts = new ArrayList();

    public static void main(String[] args) {
    
        int s,i,j, type_of_search;
        
        Scanner in = new Scanner(System.in);
       
        int[] startingStateBoard2 = {1, 2, 3, 8,  4, 7, 0, 6, 5 };      //endeiktikh katastash gia dokimes 
        int number_on_nodes=0, number_on_nodes2=0, number_on_nodes3=0;
        
        
        System.out.println("Give n numbers for cases");                 //o xrhsths eisagei to plh8os twn problhmatwn pou 8a lu8oun
        s = in.nextInt();
      
        for (i=0; i<s; i++){
            System.out.print("Initial state ");
            //startingStateBoard2 = RandomizeArray(0,8);                  //klhsh ths RandomizeArray wste na paragei tuxaiai problhmata
            for (int a=0; a< startingStateBoard2.length; a++){
                
                
                int value = startingStateBoard2[a];
                System.out.print(value);
                
            }
            System.out.println();
                        
            System.out.println("That 's BFS");
                number_on_nodes = number_on_nodes + BFSearch.search(startingStateBoard2);
                
            System.out.println("That 's DFS");
                number_on_nodes2 = number_on_nodes2 + DFSearch.search(startingStateBoard2);
            System.out.println("That 's A*");
                number_on_nodes3 = AStarSearch.search(startingStateBoard2);
                
        }
        
        System.out.println("BFS average nodes visited: " + number_on_nodes/s + " found "            //ektupwsh statistikwn stoixeiwn sto telos ths ekteleshs
                + "solution in " + BFSearch.a + " problems");
        System.out.println("DFS average nodes visited: " + number_on_nodes2/s+ " found "
                + "solution in " + DFSearch.a + " problems");
        System.out.println("A* average nodes visited: " + number_on_nodes3/s+ " found "
                + "solution in " + AStarSearch.a + " problems");
        
    }
    
    
    
    public static int[] RandomizeArray(int a, int b){               //h sunartshs auth xrishmopoieite gia na paragei tuxaia katastash tou pinaka pros lush
		Random rgen = new Random();  // Random number generator		
		int size = b-a+1;            //a=0 , b=8 antiproswpeuoun to euros tou pinaka   
		int[] array = new int[size]; //o telikos pinakas pou 8a epistrafei, pros epilush
 
		for(int i=0; i< size; i++){
			array[i] = a+i;      //arxikopoihsh tou pinaka 
		}
 
		for (int i=0; i<array.length; i++) {
		    int randomPosition = rgen.nextInt(array.length);    //paragwgh tuxaias 8eshs
		    int temp = array[i];                    
		    array[i] = array[randomPosition];                   //antimeta8esh 8esewn
		    array[randomPosition] = temp;
		}
 
		return array;
	}
}