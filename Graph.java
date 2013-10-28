import java.util.*;
import java.io.*;

public class Graph {
	ArrayList<Vertex> allVertices = new ArrayList<Vertex>();
	ArrayList<Edge> allEdges = new ArrayList<Edge>();
	int connectedComponents = 1;
	
	/**
	 * Constructor for the Graph object.
	 */
	public Graph() {
		
	}
	
	/**
	 * This method reads the first line of the text file, which should contain all the names of the
	 * vertices that the user would like to include in the Graph object. All Vertex names should be
	 * separated by a space. Returns a string containing the ArrayList of all the vertices in a graph.
	 * @param filebr
	 * @return
	 * @throws IOException
	 */
	public ArrayList<Vertex> createVertices (BufferedReader filebr) throws IOException {
		String[] vertices = filebr.readLine().split(" ");
		for (int i = 0; i < vertices.length; i++) {
			Vertex v = new Vertex(vertices[i]);
			allVertices.add(v);
		}
		System.out.println("Here are all the vertices in the graph: " + allVertices);
		return allVertices;
		
	}
	
	/**
	 * This method splits the remaining information in the file by a space. It finds the vertices that
	 * a given Edge leaves at and arrives at. It also obtains the weight associated with the Edge. As
	 * each Edge is processed, it is added to the master ArrayList of allEdges. This method will continue
	 * until all data has been read and processed. Returns a string containing the ArrayList of all the edges.
	 * @param filebr
	 * @return
	 * @throws IOException
	 */
	public ArrayList<Edge> createEdges (BufferedReader filebr) throws IOException, WrongFormatException, NumberFormatException, NullPointerException {
		String line = filebr.readLine();
		while (line!= null) {
	
			String[] edges = line.split(" ");
			Vertex outvertex = findVertex(edges[0], allVertices);
			for (int i = 1; i < edges.length; i++) {
				Edge e = new Edge(outvertex, obtainEdge(edges[i]), obtainWeight(edges[i]));
				outvertex.adjacencyList.add(obtainEdge(edges[i])); //add to the vertex's adjacency list
				obtainEdge(edges[i]).adjacencyList.add(outvertex);
				allEdges.add(e);
				
			}
		line = filebr.readLine();
		}
		for (int i = 0; i < allVertices.size(); i++) {
			getOutEdges(allVertices.get(i));
			getInEdges(allVertices.get(i));
		}
		System.out.println("Here are all the edges in the graph: " + allEdges);
		return allEdges;
	}
	
	/**
	 * From the original input format in the file that the user enters, this method obtains the Edge
	 * that points out of a given Vertex and checks to make sure that the Edge arrives at a valid
	 * Vertex that is contained in the graph. 
	 * @param str
	 * @return
	 */
	public Vertex obtainEdge(String str) throws WrongFormatException, NullPointerException {
		int separator = str.indexOf(",");
		if (separator == -1) {
			throw new WrongFormatException();
		}
		String invertex = str.substring(0, separator);
		return findVertex(invertex, allVertices);
		
	}
	
	/**
	 * For a given vertex, this method compiles all of the edges that leave that Vertex and returns
	 * them as an ArrayList.
	 * @param v
	 */
	public void getOutEdges(Vertex v) {
		ArrayList<Edge> oedges = new ArrayList<Edge>();
		for (int i = 0; i < allEdges.size(); i++) {
			if (allEdges.get(i).getLeave().equals(v)) {
				oedges.add(allEdges.get(i));
			}
		}
		v.outedges = oedges;
	}
	
	/**
	 * For a given vertex, this method compiles all of the edges that arrive at that Vertex and returns
	 * them as an ArrayList.
	 * @param v
	 */
	public void getInEdges(Vertex v) {
		ArrayList<Edge> iedges = new ArrayList<Edge>();
		for (int i = 0; i < allEdges.size(); i++) {
			if (allEdges.get(i).getArrive().equals(v)) {
				iedges.add(allEdges.get(i));
			}
		}
		v.inedges = iedges;
	}
	
	/**
	 * This method attempts to see if the entered name of a Vertex can be found on an ArrayList 
	 * containing names of vertices. Will return the name of the Vertex if found.
	 * @param name
	 * @param vertices
	 * @return
	 */
	public Vertex findVertex(String name, ArrayList<Vertex> vertices) {
		Vertex foundVertex = null;
		for (int i=0; i < vertices.size(); i++) {
			if (vertices.get(i).getName().equals(name)) {
				foundVertex = vertices.get(i);
			}
		}
		if (foundVertex == null) {
			return null;
		}
		return foundVertex;
	}
	
	/**
	 * From the original input format in the file that the user enters, this method obtains the weight
	 * associated with a given edge and returns it as an integer.
	 * @param str
	 * @return
	 */
	public int obtainWeight(String str) throws NumberFormatException{
		int separator = str.indexOf(",");
		int number = Integer.parseInt(str.substring(separator+1));
		return number;
	}
	
	/**
	 * Checks to see if an entire graph is connected by compiling each Vertex's adjacency list.
	 * If the combined adjacency list spans all of the vertices of the graph, then the graph is
	 * connected and the method outputs a string that informs the user. Otherwise, the method 
	 * return the number of connected components.
	 * @param vertices
	 * @return
	 */
	public String isConnected(ArrayList<Vertex> vertices) {
		ArrayList<Vertex> connection = new ArrayList<Vertex>();
		Vertex original = vertices.get(0);
		connection = restOfAdjacents(original, connection);
		
		if (connection.size() == allVertices.size()) {
			return "This graph is connected.";
		} 
		else if (connection.size() != vertices.size()) {
			connectedComponents++;
			isConnected(computeRemainders(vertices, connection));
		}
		return "This graph is not connected, but has " + connectedComponents + " connected components.";
	}
	
	/**
	 * This is a helper method for the previous method. When a graph appears to be only partially connected,
	 * the adjacency list breaks off. This method checks the Vertex's that were broken off from the
	 * connection ArrayList and adds them to a new one as the leftovers.
	 * @param previousremainders
	 * @param connection
	 * @return
	 */
	public ArrayList<Vertex> computeRemainders (ArrayList<Vertex> previousremainders, ArrayList<Vertex> connection) {
		ArrayList<Vertex> newrem = new ArrayList<Vertex>();
		for (int i = 0; i < previousremainders.size(); i++) {
			if (findVertex(previousremainders.get(i).getName(), connection)==null) {
				newrem.add(previousremainders.get(i));
			}
		}
		
		return newrem;
	}
	
	/**
	 * This method takes the remaining Vertices that were not connected to the first part of the graph
	 * and checks to see if they are connected to each other. Returns an ArrayList of Vertices 
	 * @param v
	 * @param connection
	 * @return
	 */
	public ArrayList<Vertex> restOfAdjacents(Vertex v, ArrayList<Vertex> connection) {
		if (findVertex(v.name, connection)==null) {
			connection.add(v);
			for (int i = 0; i < v.getAdjacencyList().size(); i++) { //adds the adjacent vertices' adjacent lists
				restOfAdjacents(v.getAdjacencyList().get(i), connection);
			}
		}
		return connection;
	}
	
	/**
	 * This method finds the shortest path from a given vertex to all other vertices that exist in the graph,
	 * using Dijkstra's Algorithm. This method will continue until all vertices in the graph have been visited.
	 * @param original
	 */
	public void dijkstrasPath(Vertex original) {
		for (int i = 0; i < allVertices.size(); i++) { //first initialize all the vertices' distances to infinity
			allVertices.get(i).setDist(Integer.MAX_VALUE);
			allVertices.get(i).setPath(null);
		}
		original.dist = 0;
		
		PriorityQueue <Vertex> vertexQ = new PriorityQueue<Vertex>();
		vertexQ.add(original);
		while (!vertexQ.isEmpty()) {
			Vertex u = vertexQ.poll();
			for (Edge e : u.outedges) {
				Vertex adjacent = e.getArrive();
				int weight = e.getWeight();
				int distThruU = u.dist + weight;
				if (distThruU < adjacent.dist) {
					vertexQ.remove(adjacent);
					adjacent.dist = distThruU; 
					adjacent.path = u;
					vertexQ.add(adjacent);
				}
			}
		}
	
	}//end of dijkstrasPath
	
	/**
	 * This method displays the calculations of Dijkstra's algorithm, by returning
	 * the shortest path to a destination desired by the user.  The path is represented
	 * by an ArrayList of Edges.
	 * @param destination
	 * @return
	 */
	public ArrayList<Vertex> getShortestPath(Vertex destination) {
		ArrayList<Vertex> path = new ArrayList<Vertex>();
		for (Vertex previous = destination; previous !=null ; previous = previous.path) {
			path.add(previous);
		}
		Collections.reverse(path);
		if (path.size() == 1) {
			System.out.println("Cannot get to destination.");
			return path;
		}
		return path;
	}
	
	/**
	 * This method creates a minimum spanning tree utilizing Prim's algorithm. Takes as an 
	 * input a starting point as the Vertex.  Prints the total cost, as well as an ArrayList of the Edges chosen. 
	 * @param start
	 */
	public void primMinSpanTree(Vertex start) {
		for (int i = 0; i < allEdges.size(); i++) { //first initialize all the edges' use to be false
			allEdges.get(i).setUsed(false);
		}
		for (int i = 0; i < allVertices.size(); i++) { //first initialize all the vertices' knowns to be false
			allVertices.get(i).setKnown(false);
		}
		PriorityQueue<Edge> sortedvedges = new PriorityQueue<Edge>();
		sortedvedges.addAll(getVedges(start));
		ArrayList<Edge> minspantree = new ArrayList<Edge>();
		while(sortedvedges.size() > 0) {
			Edge temp = sortedvedges.poll();
			if (!temp.isUsed() & !temp.areVerticesVisited()) {
				minspantree.add(temp);
				temp.visitVertices();
				sortedvedges.addAll(getVedges(temp.getLeave()));
				sortedvedges.addAll(getVedges(temp.getArrive()));
				
			}
		}
		int totalCost = 0;
		for (int k = 0; k < minspantree.size(); k++) { //finds the total of all the weights in the min span tree
			totalCost += minspantree.get(k).getWeight();
		}
		System.out.println("Here is Prim's minimum spanning tree: " + minspantree);
		System.out.println("Here is the total cost of the spanning tree: " + totalCost + "\n");
	}
	
	/**
	 * This method creates an ArrayList that consists of all edges that point into and out of a given Vertex.
	 * @param v
	 * @return
	 */
	public ArrayList<Edge> getVedges(Vertex v) {
		ArrayList<Edge> vEdges = new ArrayList<Edge>();
		vEdges.addAll(v.getInedges());
		vEdges.addAll(v.getOutedges());
		return vEdges;
	}
	
	/**
	 * This method creates a minimum spanning tree utilizing Kruskal's algorithm. 
	 * Prints the total cost and an ArrayList of the Edges, based off cheapest weights.
	 */
	public void kruskalMinSpanTree () {
		for (int i = 0; i < allVertices.size(); i++) { //first initialize all the vertices' knowns to be false
			allVertices.get(i).setKnown(false);
		}
		PriorityQueue<Edge> sortededges = new PriorityQueue<Edge>();
		sortededges.addAll(allEdges);
		
		ArrayList<Edge> minspantree = new ArrayList<Edge>();
		while(sortededges.size() > 0) { 
			Edge temp = sortededges.poll(); //removes the edge with the minimum weight and stores in temp
			if (!temp.areVerticesVisited()) { 
				minspantree.add(temp);  
				temp.visitVertices(); 
			}
		}
		int totalCost = 0;
		for (int k = 0; k < minspantree.size(); k++) { //finds the total of all the weights in the min span tree
			totalCost += minspantree.get(k).getWeight();
		}
		System.out.println("Here is Kruskal's minimum spanning tree: " + minspantree);
		System.out.println("Here is the total cost of the spanning tree: " + totalCost + "\n");
		
	}//end of kruskalMinSpanTree
	
	
}//end of class Graph
