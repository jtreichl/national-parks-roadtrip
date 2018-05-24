package parks;

public class TrivialRoutes implements RouteBuilder {

	@Override
	public Route getOptimalRoute(ParkList parks) {
		
		//System.out.println("No route available.");
		return new Route();
	}
}

