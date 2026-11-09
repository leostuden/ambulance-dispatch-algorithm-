package utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Graph {
    private final Map<String, List<Edge>> adjList;

    public Graph() {
        this.adjList = new HashMap<>();
    }

    public void addLocation(String location) {
        adjList.putIfAbsent(location, new ArrayList<>());
    }

    public void addRoad(String from, String to, double weight) {
        addLocation(from);
        addLocation(to);
        adjList.get(from).add(new Edge(to, weight));
        adjList.get(to).add(new Edge(from, weight));
    }

    public void addEdge(String source, String destination, double weight) {
        this.adjList.computeIfAbsent(source, k -> new ArrayList<>()).add(new Edge(destination, weight));
        this.adjList.computeIfAbsent(destination, k -> new ArrayList<>()).add(new Edge(source, weight));
    }

    public List<Edge> getEdges(int node) {
        return this.adjList.getOrDefault(node, new ArrayList<>());
    }

    // Dijkstra's algorithm to find shortest path
    public PathResult findShortestPath(String start, String end) {
        if (!adjList.containsKey(start) || !adjList.containsKey(end)) {
            return null;
        }
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        Map<String, Double> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        Set<String> visited = new HashSet<>();
        for (String location : adjList.keySet()) {
            distances.put(location, Double.MAX_VALUE);
        }
        distances.put(start, 0.0);
        pq.offer(new Edge(start, 0));
        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            String currentLocation = current.destination;
            if (visited.contains(currentLocation))
                continue;
            visited.add(currentLocation);
            if (currentLocation.equals(end))
                break;
            for (Edge edge : adjList.get(currentLocation)) {
                if (!visited.contains(edge.destination)) {
                    double newDistance = distances.get(currentLocation) + edge.weight;
                    if (newDistance < distances.get(edge.destination)) {
                        distances.put(edge.destination, newDistance);
                        previous.put(edge.destination, currentLocation);
                        pq.offer(new Edge(edge.destination, newDistance));
                    }
                }
            }
        }
        if (distances.get(end) == Integer.MAX_VALUE) {
            return null;
        }
        List<String> path = new ArrayList<>();
        String current = end;
        while (current != null) {
            path.add(0, current);
            current = previous.get(current);
        }
        return new PathResult(path, distances.get(end));
    }

    public static class PathResult {
        private List<String> path;
        private double totalWeight;
        public PathResult(List<String> path, double totalWeight) {
            this.path = path;
            this.totalWeight = totalWeight;
        }
        public List<String> getPath() { return path; }
        public void setPath(List<String> path) { this.path = path; }
        public double getTotalWeight() { return totalWeight; }
        public void setTotalWeight(double totalWeight) { this.totalWeight = totalWeight; }
        @Override
        public String toString() {
            return "Path: " + String.join(" -> ", path) + "\nTotal Weight: " + totalWeight;
        }
    }
}
