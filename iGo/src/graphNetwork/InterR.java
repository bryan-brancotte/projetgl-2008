package graphNetwork;


public interface InterR {

	public abstract byte getTimeBetweenStations();

	public abstract String getKindOfInter();

	public abstract StationR getOtherStationR(StationR me);

	public abstract float getCost();

	public abstract RouteR getOtherRouteR(StationR me);

}
