package utility;

public class Ambulance {

	public String id;
	public int region;
	public String currentLocation;

	public Ambulance(String id, int region, String currentLocation) {
		this.id = id;
		this.region = region;
		this.currentLocation = currentLocation;
	}

	@Override
	public String toString() {
		return id + " (Region: " + region + ", Location: " + currentLocation + ")";
	}

}
