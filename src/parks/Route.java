package parks;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Route implements Comparable, Cloneable {

	private List<Integer> stops = new ArrayList<Integer>();
	private List<Integer> distances = new ArrayList<Integer>();
	
	public Route clone() {
		Route clone = new Route();
		Iterator<Integer> sIter = this.stops.iterator();
		Iterator<Integer> dIter = this.distances.iterator();
		while (sIter.hasNext() && dIter.hasNext()) {
			clone.addStop(sIter.next(), dIter.next());
		}
		return clone;		
	}
	
	public void addStop (Integer nextParkID, Integer dist) {
		stops.add(new Integer(nextParkID));
		distances.add(new Integer(dist));
	}

	public void removeLastStop () {
		stops.remove(stops.size()-1);
		distances.remove(distances.size()-1);
	}
	
	public String getLink(ParkList parks) {
		//Create maps link (with GMNames)
		if (stops.isEmpty()) {
			return("No route available.");
		}
	
		StringBuilder link = new StringBuilder().append("https://www.google.com/maps/dir/");
		Iterator<Integer> iter = stops.iterator();
		while (iter.hasNext()) {
			link.append(parks.getParkByID(iter.next()).getGoogleMapsName());
			link.append('/');
		}
		return (link.toString());
	}
	

	public Integer totalDistance() {
		Integer distance = new Integer(0);
		Iterator<Integer> iter = distances.iterator();
		while (iter.hasNext()) {
			distance += iter.next();
		}
		return distance;
	}


	public List<Integer> getStops() {
		return stops;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Route) {
			if (o != null) {
				return this.totalDistance().compareTo(((Route) o).totalDistance());
			}
		}
			
		// if other object is wrong this one is better!
		return -1;
	}

	public Integer getLastParkID() {
		if (stops.isEmpty()) {return -1; }
		return stops.get(stops.size()-1);
	}

}
