package utility;

public class Edge implements Comparable<Edge> {
	String destination;
	double weight;

	public Edge(String destination, double weight) {
		this.destination = destination;
		this.weight = weight;
	}

	@Override
	public int compareTo(Edge other) {
		if (this.weight < other.weight)
			return -1;
		else if (this.weight > other.weight)
			return 1;
		else
			return 0;
	}
}
