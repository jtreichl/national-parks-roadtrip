package parks;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class CreateRoadTrip {
	/*
	 *  This class reads a list of national parks you specify, data about them is 
	 *  used to calculate an optimal or at least very good route between them all,
	 *  presented in a link that can be pasted in a broswer for google map display -
	 *  provided you don't pick more than google maps can handle :)
	 */
	static final int MAX_STOPS = 10; //keeping under 5s running time
	static final int MAX_APPROX_STOPS = 25; //google maps tops out about here
	static ParkList parks;
	
	
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		//future parameters:
		//loop?
		//start point?
		parks = new ParkList();
		
		//read xml & create list
		readData();
		
		//run algorithm
		if (parks.isEmpty()) {
			System.out.println("No route available.");
			System.exit(0);
		}
		else {
			Context context = null;
			//brute force routes if small
			if (parks.count() <= 2) {
				System.out.println("Trivial route");
				context = new Context(new TrivialRoutes());
			}
			else if (parks.count() <= MAX_STOPS) {
				System.out.println("Calculating Optimal Route");
				context = new Context(new BruteForceRoutes());
			}
			else if (parks.count() <= MAX_APPROX_STOPS) {
				System.out.println("Approximating Optimal Route");
				context = new Context(new ApproxRoutes());
			}
			else {
				//need something fancier
				//context = new Context(new TravelingSalesman());
				System.out.println("No route available, reduce from " + parks.count() + " to " + MAX_STOPS + " for a precise solution, " + MAX_APPROX_STOPS + " for an approximation.");
				System.exit(0);
			}
			Route route = context.getOptimalRoute(parks);
			
			//generate maps link
			System.out.println(route.getLink(parks));
		}
		long endTime   = System.nanoTime();
		long totalTime = Math.round((endTime - startTime)/1000000);
		System.out.println("Calculated route with " + parks.count() + " stops in " + totalTime  + " ms");


	}
	
	static void readData() {
		//suck up park list
		getParks();
		//read xml
		DistancesXMLParser parser = new DistancesXMLParser(parks);
		parser.parseFiles();
		
	}
	
	static void getParks() {
		// next if non-contiguous || beenAlready)
		String csvFile = "C:/USers/treichle/workspace/NPRoadTrip/parkDistData/parkList.csv";
		String line = "";
		String cvsSplitBy = ",";

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			//skip header
            line = br.readLine();

			while ((line = br.readLine()) != null) {
				String[] parkInfo = line.split(cvsSplitBy);

				// next if non-contiguous || beenAlready)
				boolean noncontiguous = (Integer.parseInt(parkInfo[2]) == 1);
				boolean include = (Integer.parseInt(parkInfo[3]) == 1);
				
				if (!noncontiguous && include) {
					Integer parkID = new Integer(Integer.parseInt(parkInfo[0]));
					Park newPark = parks.add(parkInfo[1], parkID);
					newPark.setLat(new Float(Float.parseFloat(parkInfo[4])));
					newPark.setLon(new Float(Float.parseFloat(parkInfo[5])));
					newPark.setGoogleMapsName(parkInfo[6].replace(' ', '+'));
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
