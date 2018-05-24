package parks;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BruteForceRoutes implements RouteBuilder {

	Integer distanceToBeat = Integer.MAX_VALUE;
	Route bestRouteSoFar = new Route();
	ParkList allParks = null;
	
	@Override
	public Route getOptimalRoute(ParkList parks) {
		// (parks.count() == 2-5)
		allParks = parks;

		//build the routes
		Set<Integer> parksToVisit  = parks.getListOfIDs();
		Iterator<Integer> iter = parksToVisit.iterator();
		routeBuilder(bestRouteSoFar, parksToVisit);
		
		return bestRouteSoFar;
	}

	//recursive
	private void routeBuilder (Route routeSoFar, Set<Integer> remainingParkIDs) {
		if (remainingParkIDs.isEmpty()) {
			//evaluate route
			Integer routeScore = routeSoFar.totalDistance();
			//debug 
			System.out.println("Comparing route of length " + routeScore + " with route of length " + distanceToBeat);
			if (routeScore < distanceToBeat) {
				distanceToBeat = routeScore;
				bestRouteSoFar = routeSoFar.clone();
				
			}
			return;
		}
		
		//copy working set so we don't mess with the iterator
		Set<Integer> workingSet = new HashSet<Integer>();
		Iterator<Integer> iter = remainingParkIDs.iterator();
		while (iter.hasNext()) {
			workingSet.add(new Integer(iter.next()));
		}
		
		Iterator<Integer> iter2 = remainingParkIDs.iterator();
		while (iter2.hasNext()) {
			//add Next park to route, remove from remaining list
			Integer lastPark = routeSoFar.getLastParkID();
			Integer nextPark = iter2.next();
			Integer distance = allParks.getDistance(lastPark, nextPark);
			routeSoFar.addStop(nextPark, distance);
			workingSet.remove(nextPark);
			routeBuilder(routeSoFar, workingSet);

			//back - readd nextPark to remaining list, remove from route
			workingSet.add(nextPark);
			routeSoFar.removeLastStop();
		}
		
		return;
	}

}
