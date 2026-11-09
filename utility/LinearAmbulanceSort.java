package utility;

import java.util.ArrayList;
import java.util.List;

public class LinearAmbulanceSort {
	private static final int REGION_COUNT = 11;

	public static List<Ambulance> sortAmbulances(List<Ambulance> ambulances, int patientRegion) {
		// Initialize buckets
		List<List<Ambulance>> zoneBuckets = new ArrayList<>();
		for (int i = 0; i < REGION_COUNT; i++) {
			zoneBuckets.add(new ArrayList<>());
		}
		// Fill buckets according to region
		for (Ambulance amb : ambulances) {
			if (amb.region >= 0 && amb.region < REGION_COUNT) {
				zoneBuckets.get(amb.region).add(amb);
			}
		}
		// Build sorted list: nearest region first
		List<Ambulance> sorted = new ArrayList<>();
		sorted.addAll(zoneBuckets.get(patientRegion));
		for (int r = 0; r < REGION_COUNT; r++) {
			if (r == patientRegion)
				continue;
			sorted.addAll(zoneBuckets.get(r));
		}
		return sorted;
	}
}
