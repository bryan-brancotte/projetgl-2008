package graphNetwork;


public interface InterReader {

	public abstract byte getTimeBetweenStations();

	public abstract String getKindOfInter();

	public abstract StationReader getOtherStationR(StationReader me);

	public abstract float getCost();

	public abstract RouteReader getOtherRouteR(StationReader me);

}
