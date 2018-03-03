package fr.yann.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import fr.yann.model.FF;

public class Main {
	public final static String FILE_TO_USE = "waterPipes.csv";
	
	public static void main(String[] args)
	{
		try
		{
			// Reads the graph
	        System.out.println("----------[USING "+FILE_TO_USE+"]----------\n");
	        Scanner reader = new Scanner(new FileInputStream("ressources/"+ FILE_TO_USE));
	        reader.nextLine(); //reads [Water_reserves]
	        
	        
	        String actual = ""; //will store the current line
	        
	        
	        // Reads and stores in an array list the water reserves part
	        ArrayList<int[]> tmp = new ArrayList<int[]>();
	        while (!((actual = reader.nextLine()).equals("[Cities]"))) {
	            Scanner lineReader = new Scanner(actual); 
	            lineReader.useDelimiter(",");
	            
	        	int[] tempoTab = new int[2];
	        	tempoTab[0]=Integer.parseInt(lineReader.next()); //node number
	        	tempoTab[1]=Integer.parseInt(lineReader.next()); //capacity
	        	tmp.add(tempoTab);
	        	
	        	lineReader.close();
	        }
	        
	        
	        actual = "";
	        // Reads and stores in an array list the cities part
	        ArrayList<int[]> tmp2 = new ArrayList<int[]>();
	        while (!((actual = reader.nextLine()).equals("[Pipes]"))) {
	            Scanner lineReader = new Scanner(actual); 
	            lineReader.useDelimiter(",");

	        	int[] tempoTab = new int[2];
	        	tempoTab[0]=Integer.parseInt(lineReader.next()); //node number
	        	tempoTab[1]=Integer.parseInt(lineReader.next()); //capacity
	        	tmp2.add(tempoTab);
	        	
	        	lineReader.close();
	        }
	        
	        ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>(); //Graph
	        ArrayList<Integer> emptyForTheMoment = new ArrayList<Integer>(); // Initialize the first line (which is only 0s)
	        graph.add(emptyForTheMoment);

	        // Reads the matrix (pipes part)
            int i = 0; // Will store the number of vertex
	        while (reader.hasNextLine()) {
	        	i++;
	        	actual = reader.nextLine(); 
	        	ArrayList<Integer> tmp3 = new ArrayList<Integer>();
	        	
	            Scanner lineReader = new Scanner(actual); 
	            lineReader.useDelimiter(",");
	            
	            tmp3.add(0); //the first column is filled with 0      
	            while (lineReader.hasNext()) {
	            	tmp3.add(Integer.parseInt(lineReader.next()));
	            }
	            int tempoInt;
	            if((tempoInt = isInArrayList(tmp2, i)) != -1)
	            {
	            	
	            	tmp3.add(tempoInt);
	            }
	            else
	            {	
	            	tmp3.add(0);
	            }
		        lineReader.close();
	            graph.add(tmp3);
	        }
	        
	        // Adds the 0s at the beginning
	        ArrayList<Integer> zerosAtTheEnd = new ArrayList<Integer>();
	        for (int j=0; j<i+2; j++)
	        {
	            int tempoInt;
	        	if ((tempoInt = isInArrayList(tmp,j)) != -1) 
	        	{
	        		emptyForTheMoment.add(tempoInt);
	        	}
	            else
	            {	
	            	emptyForTheMoment.add(0);
	            }
	        	zerosAtTheEnd.add(0);
	        }
			reader.close();
			graph.add(zerosAtTheEnd);
			
			
			// Prints the graph
			System.out.println("Number of vertices = "+(i+2)+"\n");
	        for (int k = 0; k < graph.size(); k++) 
	        {
	            for (int j = 0; j < graph.get(k).size(); j++)
	            {
	                System.out.print(graph.get(k).get(j)+" ");
	                if (graph.get(k).get(j) < 10) 
	                {
	                	 System.out.print(" ");
	                }
	            }
	            System.out.println(" ");
	        }
	        
	        // Launches the algo
			FF fordFul = new FF(graph.size());
			int maxFlow = fordFul.fordFulkerson(graph, 0, graph.size()-1);
			ArrayList<int []> minimumCut = fordFul.getMinimumCut(graph);
			
	        System.out.println("\n\n----------[RESULTS]----------\n");
			System.out.println("Maximum Flow = "+maxFlow);
			System.out.print("\nMinimum Cut = ");
			
			for (i=0; i<minimumCut.size(); i++)
			{
				if (i!=0) { System.out.print("\t");} //just so it is beautiful <3
				System.out.println("\t["+minimumCut.get(i)[0]+" -> "+minimumCut.get(i)[1]+"]");
			}
		}
        catch(FileNotFoundException e)
        {
            System.out.println("Error: no file found");
        }
	}
	
	public static int isInArrayList(ArrayList<int[]> array, int i) 
	{
		for(int[] e: array) 
		{
			if (e[0] == i)
			{
				return e[1];
			}
		}
		return -1;
	}
	
}
