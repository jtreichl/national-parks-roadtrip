package parks;
import java.util.HashMap;
import java.util.Set;

public class ParkList {
	
	private HashMap<Integer, Park> parks = new HashMap<Integer, Park>();	
	private HashMap<Integer, Integer> distances = new HashMap<Integer, Integer>();

	public Park add(String parkName, Integer parkID){
		//debug
		//System.out.println("Adding: " + parkName);			
		Park newPark = new Park(parkName, parkID);
		parks.put(parkID, newPark);
		return newPark;
	}
	
	public Park getParkByID(Integer parkID){
		if (!parks.containsKey(parkID)) {return null;}
		return parks.get(parkID);
	}
	
	public Set<Integer> getListOfIDs(){
		return parks.keySet();
	}
	
	public void addDistance(Integer parkID0, Integer parkID1, Integer distanceInK){
		
		if (!parks.containsKey(parkID0) || !parks.containsKey(parkID1)) {return;}
		Integer key = makeKey(parkID0,parkID1);
		
		if (!distances.containsKey(key) || (distances.get(key) < distanceInK)) {
			//debug
			//System.out.println("Adding: " + key + " " + distanceInK);			
			distances.put(key, distanceInK);
		}
	}

	public Integer getDistance(Integer parkID0, Integer parkID1){
		Integer key = makeKey(parkID0,parkID1);
		if (distances.containsKey(key)) return distances.get(key);
		return -1;
	}
	
	private Integer makeKey (Integer parkID0, Integer parkID1) {
		Integer key;
		if (parkID0 > parkID1 ) { key = parkID0 + 1000*parkID1; }
		else { key = parkID1 + 1000*parkID0; }
		return key;
	}

	public boolean isEmpty() {
		return parks.isEmpty();
	}

	public int count() {
		return parks.size();
	}

}
