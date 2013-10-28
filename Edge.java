
public class Edge implements Comparable<Edge>{
	Vertex leave;
	Vertex arrive;
	int weight;
	boolean isUsed = false;
	
	/**
	 * Returns the Vertex from which this edge points out of.
	 * @return
	 */
	public Vertex getLeave() {
		return leave;
	}

	/**
	 * Sets leave as the Vertex in which the Edge points out of.
	 * @param leave
	 */
	public void setLeave(Vertex leave) {
		this.leave = leave;
	}

	/**
	 * Returns the Vertex of which the Edge points into.
	 * @return
	 */
	public Vertex getArrive() {
		return arrive;
	}
	
	/**
	 * Sets the arrive to the Vertex in which the Edge points into.
	 * @param arrive
	 */
	public void setArrive(Vertex arrive) {
		this.arrive = arrive;
	}

	/**
	 * Returns the weight associated with the edge.
	 * @return
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * Sets the weight in which the edge is associated with.
	 * @param weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	/**
	 * Returns true if the Edge has been used.
	 * @return
	 */
	public boolean isUsed() {
		return isUsed;
	}

	/**
	 * Sets whether or not the Edge has been used.
	 * @param isUsed
	 */
	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}
	
	/**
	 * Returns true if an Edge has identified the Vertex that it leaves from and the Vertex that it
	 * arrives at.
	 * @return
	 */
	public boolean areVerticesVisited() {
		return (leave.isKnown() & arrive.isKnown());
	}
	
	/**
	 * If an edge has both a leave and arrive Vertex, then the vertices are known and thus set to 
	 * true.
	 */
	public void visitVertices() {
		leave.setKnown(true);
		arrive.setKnown(true);
	}

	/**
	 * Constructs the object Edge.
	 * @param out
	 * @param in
	 * @param number
	 */
	public Edge(Vertex out, Vertex in, int number) {
		leave = out;
		arrive = in;
		weight = number;
	}
	
	/**
	 * Overrides the original toString() method. 
	 */
	public String toString() {
		return "Edge from " + getLeave() + " to " + getArrive() + " with weight " + getWeight();
	}
	
	/**
	 * Allows for to Edges to be compared based off their weight. Returns 0 if the two weights are equal, 
	 * a value less than 0 if this instance's weight is less than the other's weight, and a value 
	 * greater than 0 if other's weight is greater than this instance's weight. 
	 */
	public int compareTo(Edge other) {
		return Integer.compare(this.weight, other.weight);
	}
	
}//end of Edge
