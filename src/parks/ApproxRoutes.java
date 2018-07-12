package parks;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ApproxRoutes implements RouteBuilder {
	/*
	 *  This is an approximation method for routes with too many stops for a brute force algorithm.
	 *  Order stops north-to-south or east-to-west, then swap adjacent pairs in an attempt to bubble 
	 *  out outliers
	 */
	
	Integer distanceToBeat = Integer.MAX_VALUE;
	ParkList allParks = null;
	
	@Override
	public Route getOptimalRoute(ParkList parks) {
		allParks = parks;

		// get strict n-s route, add piecewise optimizations
		System.out.println("Running North to South routes");
		List<Park> NtoSParks = parks.getListOfParks();
		Collections.sort(NtoSParks, new Park.Comparators().LATITUDE);
		Route bestNtoSRoute = optimizeRoute(NtoSParks);
		
		// get strict e-w route, add piecewise optimizations
		System.out.println("Running East to West routes");
		List<Park> EtoWParks = parks.getListOfParks();
		Collections.sort(EtoWParks, new Park.Comparators().LONGITUDE);
		Route bestEtoWRoute = optimizeRoute(EtoWParks);

		// send back the shorter of the two
		if (bestNtoSRoute.totalDistance() < bestEtoWRoute.totalDistance()) {return bestNtoSRoute;}
		return bestEtoWRoute;
	}

	private Route optimizeRoute (Collection<Park> parksInOrder) {

		Route baseRoute = routeBuilder(parksInOrder);
		Integer distanceToBeat = baseRoute.totalDistance();
		Route workingRoute = routeBuilder(parksInOrder);
		// iterate through up to once per stop
		int maxIterations =  parksInOrder.size()-1;

		//copy of list to work on
		ArrayList<Park> workingList = new ArrayList<Park>();
		Iterator<Park> iter = parksInOrder.iterator();
		while (iter.hasNext()) {
			workingList.add(iter.next());
		}
		
		for (int i= 0; i < maxIterations; i++) {
			int counter = 0;
			boolean changeMade = false;
			//piecewise (attempted) optimizations
			while (counter + 1 < workingList.size()) {
				//run down lost and swap each adjacent 2, see if it's shorter
				Park temp = workingList.get(counter);
				workingList.set(counter, workingList.get(counter+1));
				workingList.set(counter+1, temp);
				workingRoute = routeBuilder(workingList);
				
				//System.out.println("Comparing route of length " + distanceToBeat + " with route of length " + workingRoute.totalDistance());
				if (distanceToBeat < workingRoute.totalDistance()) {
					// test route was longer - undo swap
					temp = workingList.get(counter);
					workingList.set(counter, workingList.get(counter+1));
					workingList.set(counter+1, temp);
				}
				else {
					// replace best-so-far route with this one
					distanceToBeat = workingRoute.totalDistance();
					changeMade = true;
				}
				counter++;
			}
			// if no optimizations were possible no point in continuing
			if (!changeMade) {i = maxIterations;}
		}
		workingRoute = routeBuilder(workingList);
		return workingRoute;
	}
	
	private Route routeBuilder (Collection<Park> parksInOrder) {
		//make route of parks in order given
		Route routeSoFar = new Route();
		Iterator<Park> iter = parksInOrder.iterator();
		while (iter.hasNext()) {
			Integer lastPark = routeSoFar.getLastParkID();
			Integer nextPark = iter.next().getParkID();
			Integer distance = allParks.getDistance(lastPark, nextPark);
			routeSoFar.addStop(nextPark, distance);
		}
		return routeSoFar;
	}

}
