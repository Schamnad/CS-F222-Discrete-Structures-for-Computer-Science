//Author: Sanjeed Schamnad; 2011B2A7744P

import java.io.*;
import java.util.*;

class aq36_2011B2A7744P {
	
	int Adjacency[][];		//Matrix which stores the adjacency matrix	
	boolean check = true;	//Keeps track whether or not the 2D array is a valid graph 
	int count=-1;			//Counts number of elements in each row of matrix
	int root=0; 				//Counts the number of roots
	int edges=0;				//Counts the number of edges in the graph	
	int marker=0;			//keeps  track of the type of error
	int root_vertex=-1;		//Stores the vertex number of root
	String out;				//String which writes output to file

	/*This method reads the data from the file, checks if it is a valid graph
	   and stores it in a 2-dimensional array */
	void readFile(String file_name){			
		try{
			//Open the file
			BufferedReader br = new BufferedReader(new FileReader(file_name));
			String strLine;		//Reads input from file line-by-line
			 
			int line=-1; 		//Counts the number of rows in the matrix
			
			strLine=br.readLine();	//Read the first line
			
			StringTokenizer st = new StringTokenizer(strLine,",");
			strLine.trim();
			count = st.countTokens();	//Counts number of columns in the file
			line++;
			
			//Declare square matrix of size "count"
			Adjacency = new int[count][count];
			
			//Stores the values in 1st line into the array
			for(int i=0;i<count;i++)
				Adjacency[line][i]=Integer.parseInt(st.nextToken());		
		
			//Read File Line-by-Line
			while((strLine = br.readLine())!=null){
				StringTokenizer str = new StringTokenizer(strLine,",");
				strLine.trim();
				int count2 = str.countTokens();		//Counts number of elements in a row
				
				if(count2!=count){					
					check = false;
					break;
				}
				else{
					line++;
					//The input values are stored in an array
					for(int i=0;i<count;i++)
						Adjacency[line][i]=Integer.parseInt(str.nextToken());	
				}
			}
			
			br.close();		//File reading complete
			
			if(++line<count)
				check = false;
		}
		//Exception Handling	
		catch(FileNotFoundException e)	{			check = false;	this.writeFile("Input file not found " +e,file_name);	}
		catch(IOException e)	{					check = false;	this.writeFile("Error in Input/Output " +e,file_name);	}
		catch(ArrayIndexOutOfBoundsException e){	check = false;	this.writeFile("Invalid input",file_name);	}
		catch(ArrayStoreException e){				check = false;	this.writeFile("Invalid input value(s).",file_name);	}
		catch(NumberFormatException e){				check = false; 	this.writeFile("Invalid conversion from string to integer.",file_name);	}
	}

	/*This method writes the string passed as an argument to a file*/
	void writeFile(String output, String file_name){				
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter(file_name));
			out.append(output);
			out.close();
		}
		catch(IOException e){	System.out.println("Could not write file " +e);	}
	}
	
	/*This method checks if the graph is a rooted tree*/
	int checkRooted(){	
		int vertices = count;
		
		
		/*Block to check whether all entries are 0 or 1, as there
		can be utmost one edge between two given vertices*/
		
		for(int i=0; i<count; i++){
			for(int j=0;j<count; j++){
				if((Adjacency[i][j]!=0)&&(Adjacency[i][j]!=1))
						marker=1;
			}
		}
		
		//Block to check whether diagonal elements are zero
		if(marker==0){
			for(int i=0; i<count; i++){
				if(Adjacency[i][i]!=0)
					marker=2;
			}
		}
		
		//Block to check for disconnected points
		if(marker==0){
			for(int i = 0; i<count; i++){
				int check = 0;
				for(int j = 0; j< count; j++){
					if((Adjacency[i][j]==1)||(Adjacency[j][i]==1)){
						check = 1;
						break;
					}	
				}
			if(check==0)
				marker = 3;
			}
		}
			
		//Block to find the Root
		if(marker==0){
			for(int j = 0; j<count; j++){
				int check = 1;
				for(int i=0; i<count; i++){
					if(Adjacency[i][j]!=0){
						check = 0;
						break;
					}	
				}
				if(check==1){
					root_vertex=++j;
					root++;
				}
			}
		if(root!=1)		marker = 4;
		}
		
		//Block to find number of edges
		if(marker==0){
			for(int i=0; i<count; i++){
				for(int j=0;j<count; j++){
					edges+=Adjacency[i][j];
				}
			}
		}
		
		//Check: No. of vertices = No. of edges + 1
		if(marker==0){
			if(vertices!=(edges+1))
				marker = 5;
				
		}
		
		//To check if the graph is connected
		if(marker==0){
			int joint[] = new int[vertices];		//Array to track the vertices visited
			int arr_size=-1;
			joint[++arr_size]=root_vertex;			//Root is added to "joint"
			
			//This block adds root vertex's daughter(s) to "joint"
			for(int i=0; i<count; i++){
				if(Adjacency[root_vertex-1][i]==1){
					joint[++arr_size]=(i+1);
				}
			}
			
			//This block adds the further branched nodes to "joint"
			for(int i=1; i< vertices; i++){
				if(joint[i]>0){
					int temp = joint[i]-1;
					for(int j=0; j<count; j++){
						if(Adjacency[temp][j]==1){
							joint[++arr_size]=j+1;
						}
					}
				}
			}
			
			/*This block checks if all the vertices have been visited*/
			/*If not, the graph is a disconnected graph*/
			for(int i = 1; i<= vertices; i++){
				int chk = 0;
				for(int j = 0; j< vertices; j++){
					if(joint[j]==i)
						chk = 1;
				}
				if(chk!=1)
					marker = 6;
			}		
		}
		
		return marker;		//Returns 0 if graph is a Rooted tree, else a non-zero integer
		
	}
	
	int getRoot(){	return root_vertex;		}		//Returns the vertex number of root
	
	public static void main(String[] args){
		int output = 0;
		aq36_2011B2A7744P tree = new aq36_2011B2A7744P();
		tree.readFile(args[0]);
		
		if(tree.check){			//If data is inputed successfully
			
			output = tree.checkRooted();
			
			if(output==0){
				tree.out = ("The given graph is a Rooted Tree " + "\n") ;
				tree.out = tree.out + ("The root vertex is: Vertex " +tree.getRoot());
			}
			else
				tree.out = ("The given graph is not a Rooted Tree." + "\n" + "It has Circuit");
		}
		else					//If invalid input or error in file reading
			tree.out = "Error in Input. Not a valid graph.";
		
		/**
		
		if(output==0){
			tree.out = ("The given graph is a Rooted Tree" + "\n" + "The root vertex is: Vertex " + tree.getRoot());
		}else
		if(output == 6){
			tree.out = ("The given graph is not a Rooted Tree." + "\n" + "It has a Circuit");
			
		}else
		if(output == 5){
			tree.out =("The given graph is not a Rooted Tree." + "\n" + "It has " +tree.count + " vertices and " +tree.edges + " edges");
		}else{ 
			tree.out =("The given graph is not a Rooted Tree." + "\n" + "It has " +tree.root + "components");
		}
	}else{
			tree.out=("Not a valid input for this check.");
		}
		*/
		System.out.println(tree.out);		//Display output on screen
		tree.writeFile(tree.out, args[1]);			//Write output to file
	}	
}
