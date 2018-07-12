package parks;

import java.util.Comparator;

public class Park {

	private String Name;
	private String GoogleMapsName;
	private Integer parkID;
	private float lat;
	private float lon;
	private boolean isDrivable;

	public Park(String name, Integer parkID) {
		this(name, name, parkID, true, 0, 0);
	}
	
	public Park(String name, String GMName, Integer parkID) {
		this(name, GMName, parkID, true, 0, 0);
	}
	
	public Park(String name, Integer parkID, boolean isDrivable) {
		this(name, name, parkID, isDrivable, 0, 0);
	}
	
	public Park(String name, String GMName, Integer parkID, boolean isDrivable) {
		this(name, GMName, parkID, isDrivable, 0, 0);
	}
	
	public Park(String name, String GMName, Integer parkID, boolean isDrivable, float lat, float lon) {
		super();
		Name = name;
		this.parkID = parkID;
		this.isDrivable = isDrivable;
		this.GoogleMapsName = GMName;
		this.lat = lat;
		this.lon = lon;
	}
	
	public String getName() {
		return Name;
	}
	public Integer getParkID() {
		return parkID;
	}
	public boolean isDrivable() {
		return isDrivable;
	}
	public String getGoogleMapsName() {
		return GoogleMapsName;
	}
	public void setGoogleMapsName(String GMName) {
		GoogleMapsName = GMName;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLon() {
		return lon;
	}

	public void setLon(float lon) {
		this.lon = lon;
	}
	
	public static class Comparators {
		public static final Comparator<Park> LATITUDE = (Park p1, Park p2) -> Math.round(p2.getLat() - p1.getLat());
		public static final Comparator<Park> LONGITUDE = (Park p1, Park p2) -> Math.round(p2.getLon() - p1.getLon());
	}	
}
