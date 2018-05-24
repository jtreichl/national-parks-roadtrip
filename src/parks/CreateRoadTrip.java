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
	
	static final int MAX_STOPS = 9;
	static ParkList parks;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
				context = new Context(new TrivialRoutes());
			}
			else if (parks.count() <= MAX_STOPS) {
				context = new Context(new BruteForceRoutes());
			}
			else {
				//need something fancier
				//context = new Context(new TravelingSalesman());
				System.out.println("No route available, reduce to " + MAX_STOPS + " or implement better algorithm!");
				System.exit(0);
			}
			Route route = context.getOptimalRoute(parks);
			//generate maps link
			System.out.println(route.getLink(parks));
		}


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

				//debug
				//System.out.println("park [id= " + parkInfo[0] + " , name=" + parkInfo[1] + "]");
				
				// next if non-contiguous || beenAlready)
				boolean noncontiguous = (Integer.parseInt(parkInfo[2]) == 1);
				boolean include = (Integer.parseInt(parkInfo[3]) == 1);
				
				if (!noncontiguous && include) {
					Integer parkID = new Integer(Integer.parseInt(parkInfo[0]));
					Park newPark = parks.add(parkInfo[1], parkID);
					newPark.setGoogleMapsName(parkInfo[4].replace(' ', '+'));
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
