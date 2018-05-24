package parks;
public class Park {

	private String Name;
	private String GoogleMapsName;
	private Integer parkID;
	private boolean isDrivable;

	public Park(String name, Integer parkID) {
		this(name, name, parkID, true);
	}
	
	public Park(String name, String GMName, Integer parkID) {
		this(name, GMName, parkID, true);
	}
	
	public Park(String name, Integer parkID, boolean isDrivable) {
		this(name, name, parkID, isDrivable);
	}
	
	public Park(String name, String GMName, Integer parkID, boolean isDrivable) {
		super();
		Name = name;
		this.parkID = parkID;
		this.isDrivable = isDrivable;
		this.GoogleMapsName = GMName;
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

}
