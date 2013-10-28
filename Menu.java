import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Menu {
	
	/**
	 * This method prompts the user for a file containing the information required
	 * to construct a graph.  
	 */
	public static void promptForFile() {
		System.out.println("Welcome. Please enter the name of the file containing the graph information you would like to create.");
		BufferedReader br = setUpInputReader();
		String fileName = "";
		try {
			fileName = br.readLine();
			readFile(fileName, br);
		
		} catch (IOException e) {
			System.out.println("File could not be found.  Please try again.");
			promptForFile();
		} catch (WrongFormatException wfe) {
			System.out.println(wfe.getMessage());
			promptForFile();
		} catch (NumberFormatException nfe) {
			System.out.println("Each edge must be separated by a space, no more no less. Try again.");
			promptForFile();
		} catch (NullPointerException npe) {
			System.out.println("Each Vertex's edges and and individual edge is represented like this: Vertex1 Vertex2,number.  " +
					"There is a space between the original vertex, and its outgoing vertex.  Try again.");
			promptForFile();
		}
	}
	
	/**
	 * This method merely creates readers to read a user's input into the console.
	 * Returns a BufferedReader
	 * @return
	 */
	public static BufferedReader setUpInputReader() {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		return br;
		
	}
	
	/**
	 * This method reads the file the user has input, and based off the file, the method
	 * calls other methods to create Vertices and Edges.  Lastly, calls a method to display
	 * the other options a user has from here.
	 * @param filename
	 * @param br
	 * @throws IOException
	 * @throws WrongFormatException
	 * @throws NumberFormatException
	 * @throws NullPointerException
	 */
	public static void readFile(String filename, BufferedReader br) throws IOException, WrongFormatException, NumberFormatException, NullPointerException {
		FileReader fr = new FileReader(filename);
		BufferedReader bfr = new BufferedReader(fr);
		Graph g = new Graph();
		g.createVertices(bfr);
		g.createEdges(bfr);
		displayOptions(br, g);
	}
	
	/**
	 * This method prompts the user for the path to be calculated.  Allows the user
	 * to choose a start and end Vertex. After the user has chosen 2 Vertices,
	 * the function calls another method to invoke Dijkstra's algorithm and print the 
	 * resulting shortest pathway.  
	 * @param br
	 * @param g
	 */
	public static void dijkstraPrompt(BufferedReader br, Graph g) {
		System.out.println("Please enter the name of the Vertex you'd like to start from.");
		String start = "";
		try {
			start = br.readLine();
			if (g.findVertex(start, g.allVertices) == null) {
				System.out.println("This vertex is not in the graph.  Please try again.");
				dijkstraPrompt(br, g);   //makes the user try again if what the user requested is not in the graph
			}						
			g.dijkstrasPath(g.findVertex(start, g.allVertices));
		} catch (IOException e){
			System.out.println("This vertex is not in the graph.  Please try again.");
			dijkstraPrompt(br, g);
		} 
		System.out.println("Please enter the Vertex you'd like to go to.");
		try {
			String end = br.readLine();
			if (end.equals(start)) {
				System.out.println("You're already there! Want to try again?");
				dijkstraPrompt(br, g);
				
			}
			if (g.findVertex(end, g.allVertices) == null) {
				System.out.println("This vertex is not in the graph.  Please try again.");
				dijkstraPrompt(br, g);
			}
			System.out.println(g.getShortestPath(g.findVertex(end, g.allVertices))); //prints out the pathway to this vertex from starting point
		} catch (IOException e) {
			System.out.println("This vertex is not in the graph.  Please try again.");
			dijkstraPrompt(br, g);
		}
		
	}
	
	/**
	 * This method allows the user to choose a Vertex to start the minimum spanning tree
	 * associated with Prim's algorithm.  
	 * @param br
	 * @param g
	 */
	public static void primsPrompt(BufferedReader br, Graph g) {
		System.out.println("Please enter the name of the Vertex you'd like to start from.");
		try {
			String start = br.readLine();
			if (g.findVertex(start, g.allVertices) == null) {
				System.out.println("This vertex is not in the graph.  Please try again.");
				primsPrompt(br, g);
			}
			g.primMinSpanTree(g.findVertex(start, g.allVertices));
		} catch (IOException e) {
			System.out.println("This vertex is not in the graph.  Please try again.");
			primsPrompt(br, g);
		} 
	}
	
	/**
	 * This method displays all the options a user has, until a user decides to quit the program.
	 * Calls different functions based off the user's command.
	 * @param reader
	 * @param g
	 * @throws IOException
	 */
	public static void displayOptions(BufferedReader reader, Graph g) throws IOException {
		String options = "What would you like to do next? Here are your options: " + "\n"
				+ "Please type \"C\" to determine if your graph is connected" + "\n"
				+ "Please type \"D\" to find the shortest path from one vertex to another." + "\n"
				+ "Please type \"P\" to output a minimum spanning tree using Prim's algorithm" + "\n"
				+ "Please type \"K\" to output a minimum spanning tree using Kruskal's algorithm" + "\n"
		  		+ "Please type \"N\" to create a new graph" + "\n"
				+ "And if you are finished, please type \"F\".";
		  		
		System.out.println(options);
		String answer = reader.readLine();
		while (!answer.equals("F")) { //once the user hits "F", the program is finished.
			if (answer.equals("C")) {
				System.out.println(g.isConnected(g.allVertices));
			} else if (answer.equals("D")) {
				dijkstraPrompt(reader, g);
			} else if (answer.equals("P")) {
				primsPrompt(reader, g);
			} else if (answer.equals("K")) {
				g.kruskalMinSpanTree();
			} else if (answer.equals("N")) {
				promptForFile();
				return;
			} else {
				System.out.println("Please keep in mind this is case sensitive and try again.");
			}
			System.out.println(options);
			answer = reader.readLine();
		}
		System.out.println("Thank you for using this Graphing tool.  Toodles!");
		return;
	}
	public Menu() {
		
	}

}//end of class Menu
