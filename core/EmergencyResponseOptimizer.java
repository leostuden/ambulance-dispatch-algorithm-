package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import utility.Ambulance;
import utility.Graph;
import utility.Graph.PathResult;
import utility.LinearAmbulanceSort;

public class EmergencyResponseOptimizer {
    private List<Ambulance> ambulances = new ArrayList<>();
    private Graph cityGraph = new Graph();

    public EmergencyResponseOptimizer() {
        // Setup the City Graph (Nodes: Intersections, Weights: Travel Time)
        System.out.println("---- System Setup: Building City Graph ----");
        this.cityGraph.addRoad("A", "B", 5);
        this.cityGraph.addRoad("A", "C", 2);
        this.cityGraph.addRoad("B", "D", 4);
        this.cityGraph.addRoad("C", "B", 1);
        this.cityGraph.addRoad("C", "E", 7);
        this.cityGraph.addRoad("D", "E", 2);
        this.cityGraph.addRoad("D", "F", 3);
        this.cityGraph.addRoad("E", "F", 6);
        System.out.println("--- System Setup: Registering Ambulances ---");
        this.addAmbulance("AMB-501", 1, "C");
        this.addAmbulance("AMB-502", 3, "F");
        this.addAmbulance("AMB-503", 0, "A");
        this.addAmbulance("AMB-504", 0, "B");
        this.addAmbulance("AMB-505", 7, "E");
    }

    public List<Ambulance> sortAmbulancesByRegion(int patientRegion) {
        return LinearAmbulanceSort.sortAmbulances(this.ambulances, patientRegion);
    }

    public void addAmbulance(String id, int region, String currentLocation) {
        Ambulance amb = new Ambulance(id, region, currentLocation);
        this.ambulances.add(amb);
    }

    public static void main(String[] args) {
        EmergencyResponseOptimizer system = new EmergencyResponseOptimizer();
        Scanner sc = new Scanner(System.in);
        System.out.println("\nWhat is your location?");
        System.out.println("1. Hamra");
        System.out.println("2. Tarek Jdede");
        System.out.println("3. Achrafieh");
        System.out.println("4. Verdun");
        System.out.print("Enter number (1-4): ");
        int areaChoice = sc.nextInt();
        sc.nextLine();
        int emergencyRegion = areaChoice;
        String areaName = "";
        String emergencyNode = null;
        String streetInput;
        switch (areaChoice) {
            case 1:
                areaName = "Hamra";
                System.out.println("\nWhat is your street name?");
                System.out.println("A. Barouk");
                System.out.println("B. Kraytem");
                System.out.println("C. Emile Edde");
                System.out.println("D. Antoun Gemayel");
                System.out.print("Enter letter (A-D): ");
                streetInput = sc.nextLine().trim().toUpperCase();
                emergencyNode = mapStreetToNode(streetInput);
                break;
            case 2:
                areaName = "Tarek Jdede";
                System.out.println("\nWhat is your street name?");
                System.out.println("A. Sabra");
                System.out.println("B. Abou Sahel");
                System.out.println("C. Hussein");
                System.out.println("D. Hamam");
                System.out.print("Enter letter (A-D): ");
                streetInput = sc.nextLine().trim().toUpperCase();
                emergencyNode = mapStreetToNode(streetInput);
                break;
            case 3:
                areaName = "Achrafieh";
                System.out.println("\nWhat is your street name?");
                System.out.println("A. Bardawil");
                System.out.println("B. Albeirt Lkhayat");
                System.out.println("C. Adib Ishac");
                System.out.println("D. Sassine");
                System.out.print("Enter letter (A-D): ");
                streetInput = sc.nextLine().trim().toUpperCase();
                emergencyNode = mapStreetToNode(streetInput);
                break;
            case 4:
                areaName = "Verdun";
                System.out.println("\nWhat is your street name?");
                System.out.println("A. Aine el Tineh");
                System.out.println("B. Habib Srour");
                System.out.println("C. Aicha Bakaar");
                System.out.println("D. Dar el Fatwah");
                System.out.print("Enter letter (A-D): ");
                streetInput = sc.nextLine().trim().toUpperCase();
                emergencyNode = mapStreetToNode(streetInput);
                break;
            default:
                System.out.println("Invalid location choice. Exiting.");
                sc.close();
                return;
        }
        if (emergencyNode == null) {
            System.out.println("Please choose a valid street choice. Exiting.");
            sc.close();
            return;
        }
        String emergencyLocation = emergencyNode;
        System.out.println("\n=== INPUT STAGE ===");
        System.out.println("Area: " + areaName);
        System.out.println("Graph node for your street: " + emergencyLocation);
        System.out.println("Patient region (zone): " + emergencyRegion);
        System.out.println("\n=== FILTER STAGE (Linear Sorting by Region) ===");
        List<Ambulance> sortedAmb = system.sortAmbulancesByRegion(emergencyRegion);
        System.out.println("Ambulances sorted by region (closest → farthest):");
        for (Ambulance amb : sortedAmb) {
            System.out.println(" - " + amb.id + " | Region: " + amb.region +
                               " | Location node: " + amb.currentLocation);
        }
        Ambulance selected = sortedAmb.get(0);
        System.out.println("\n=== SELECTED AMBULANCE ===");
        System.out.println("Selected Ambulance: " + selected.id);
        System.out.println("Current Location node: " + selected.currentLocation);
        System.out.println("Region: " + selected.region);
        System.out.println("\n=== OPTIMIZING ROUTE (Dijkstra) ===");
        PathResult result = system.cityGraph.findShortestPath(
                selected.currentLocation, emergencyLocation);
        System.out.println("\n=== OUTPUT STAGE ===");
        System.out.println("\nShortest Path for ambulance " + selected.id + ":");
        System.out.println(result);
        System.out.println("Total Travel Time: " + result.getTotalWeight());
        System.out.println("\nWe are sending you the ambulance.");
        System.out.println("\n=== DISPATCH COMPLETE ===");
        sc.close();
    }

    private static String mapStreetToNode(String letter) {
        if (letter == null || letter.isEmpty()) return null;
        char ch = letter.charAt(0);
        switch (ch) {
            case 'A': return "A";
            case 'B': return "B";
            case 'C': return "C";
            case 'D': return "D";
            default: return null;
        }
    }

    public static void main1(String[] args) {
        EmergencyResponseOptimizer system = new EmergencyResponseOptimizer();
        String emergencyLocation = "G_PATIENT_LOC";
        int emergencyRegion = 1;
        System.out.println("\n=== INPUT STAGE ===");
        System.out.println("Emergency reported at location: " + emergencyLocation);
        System.out.println("Patient region: " + emergencyRegion);
        system.cityGraph.addLocation(emergencyLocation);
        system.cityGraph.addRoad("F", emergencyLocation, 1);
        System.out.println("\n=== FILTER STAGE (Linear Sorting) ===");
        List<Ambulance> sortedAmb = system.sortAmbulancesByRegion(emergencyRegion);
        System.out.println("Ambulances sorted by region (closest → farthest):");
        for (Ambulance amb : sortedAmb) {
            System.out.println(" - " + amb.id + " | Region: " + amb.region + " | Location: " + amb.currentLocation);
        }
        Ambulance selected = sortedAmb.get(0);
        System.out.println("\n=== SELECTED AMBULANCE ===");
        System.out.println("Selected Ambulance: " + selected.id);
        System.out.println("Its Current Location: " + selected.currentLocation);
        System.out.println("Its Region: " + selected.region);
        System.out.println("\n=== OPTIMIZING ROUTE (Dijkstra) ===");
        PathResult result = system.cityGraph.findShortestPath(selected.currentLocation, emergencyLocation);
        System.out.println("\n=== OUTPUT STAGE ===");
        System.out.println("Dispatching ambulance: " + selected.id);
        System.out.println("\nShortest Path:");
        System.out.println(result);
        System.out.println("Total Travel Time: " + result.getTotalWeight());
        System.out.println("\n=== DISPATCH COMPLETE ===");
    }
}
