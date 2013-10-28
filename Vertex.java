import java.util.ArrayList;


public class Vertex implements Comparable<Vertex>{
	String name;
	ArrayList<Vertex> adjacencyList = new ArrayList<Vertex>();
	ArrayList<Edge> outedges = new ArrayList<Edge>();
	ArrayList<Edge> inedges = new ArrayList<Edge>();
	boolean known; 
	Vertex path; //for previous vertex on shortest path
	int dist; //for cost in terms of weight

	
	/**
	 * Returns the name of the vertex.
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the vertex.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the adjacency list of the vertex, an ArrayList containing all
	 * the vertices the current vertex is adjacent to.
	 * @return
	 */
	public ArrayList<Vertex> getAdjacencyList() {
		return adjacencyList;
	}

	/**
	 * Sets the adjacency list of the vertex.
	 * @param adjacencyList
	 */
	public void setAdjacencyList(ArrayList<Vertex> adjacencyList) {
		this.adjacencyList = adjacencyList;
	}

	/**
	 * Returns the ArrayList of edges that point out of a given vertex.
	 * @return
	 */
	public ArrayList<Edge> getOutedges() {
		return outedges;
	}

	/**
	 * Sets the ArrayList of edges that point out of a given vertex.
	 * @param outedges
	 */
	public void setOutedges(ArrayList<Edge> outedges) {
		this.outedges = outedges;
	}
	
	/**
	 * Returns the ArrayList of edges that point into a given vertex.
	 * @return
	 */
	public ArrayList<Edge> getInedges() {
		return inedges;
	}

	/**
	 * Sets the ArrayList of edges that point into a given vertex.
	 * @param inedges
	 */
	public void setInedges(ArrayList<Edge> inedges) {
		this.inedges = inedges;
	}

	/**
	 * Returns true if the vertex has been visited.
	 * @return
	 */
	public boolean isKnown() {
		return known;
	}

	/**
	 * Sets the known boolean.
	 * @param known
	 */
	public void setKnown(boolean known) {
		this.known = known;
	}

	/**
	 * Returns the path of a vertex.
	 * @return
	 */
	public Vertex getPath() {
		return path;
	}

	/**
	 * Sets the path of a vertex.
	 * @param path
	 */
	public void setPath(Vertex path) {
		this.path = path;
	}

	/**
	 * Returns the distance of a vertex (for cost in terms of weight).
	 * @return
	 */
	public int getDist() {
		return dist;
	}

	/**
	 * Sets the distance of a vertex.
	 * @param dist
	 */
	public void setDist(int dist) {
		this.dist = dist;
	}

	/**
	 * The constructor for a Vertex object.
	 * @param n
	 */
	public Vertex(String n) {
		name = n;
		dist = Integer.MAX_VALUE;
		known = false;
		path = null;
	}
	
	/**
	 * Overrides the original toString() method.
	 */
	public String toString() {
		return getName();
	}
	
	/**
	 * Allows for to Vertices to be compared based off their distance. Returns 0 if the two distances are equal, 
	 * a value less than 0 if this instance's distance is less than the other's distance, and a value greater 
	 * than 0 if other's distance is greater than this instance's distance.
	 */
	public int compareTo(Vertex other) {
		return Integer.compare(this.dist, other.dist);
	}
	
	
	
}//end of Vertex
