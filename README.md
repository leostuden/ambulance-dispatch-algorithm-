# Ambulance Dispatch & Route Planning

A Java console application demonstrating region-based ambulance selection and shortest-path routing on a weighted road network. Developed as an algorithms coursework project.

## Features

- Models locations and two-way roads using an adjacency-list graph.
- Prioritizes ambulances in the patient's region using bucket grouping.
- Calculates the selected ambulance's route using Dijkstra's algorithm and a priority queue.
- Reconstructs the route and displays its total travel weight.
- Provides an interactive area and street menu inspired by Beirut neighborhoods.

## Run locally

Requires JDK 8 or later. From the repository root:

```sh
mkdir -p build
javac -d build core/*.java utility/*.java
java -cp build core.EmergencyResponseOptimizer
```

Choose area `1` and street `D` for a sample run.

## Project structure

- `core/EmergencyResponseOptimizer.java`: console interface and dispatch flow.
- `utility/Graph.java`: weighted road network, shortest-path search, and route reconstruction.
- `utility/LinearAmbulanceSort.java`: region-bucket ordering.
- `utility/Ambulance.java`: ambulance data model.
- `utility/Edge.java`: weighted edges and priority-queue ordering.

## Algorithms and data structures

Java · Object-oriented programming · Weighted graphs · Adjacency lists · Priority queues · Dijkstra's algorithm · Bucket-based ordering

## Scope

This educational simulation uses sample road weights and simplified street mappings. It selects an ambulance by region before calculating its route; it does not compare travel times across all ambulances or connect to live maps, traffic data, or emergency services. Other regions follow numeric order after the matching region.

Potential improvements include availability tracking, route-time comparison across candidates, stronger input validation, and disconnected-graph handling.
