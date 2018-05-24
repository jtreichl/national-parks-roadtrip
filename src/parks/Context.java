package parks;
public class Context {
	private RouteBuilder strategy;
	
	public Context(RouteBuilder strategy){
		this.strategy = strategy;
	}

	public Route getOptimalRoute(ParkList parks){
		return strategy.getOptimalRoute(parks);
	}
}
