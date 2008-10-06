package graphNetwork;

public interface InterReader {

	/**
	 * Get the time between the two station.
	 * 
	 * @return time in second
	 */
	public abstract byte getTimeBetweenStations();

	/**
	 * Get the kind of intersection
	 * 
	 * @return the kind {pedestrian, rolling, wheel chair?}
	 */
	public abstract String getKindOfInter();

	/**
	 * By giving one of the station, you obtain the other station of the intersection
	 * 
	 * @param me
	 *            the station you know
	 * @return the other station, or nothing if the station given is not in the intersection
	 */
	public abstract StationReader getOtherStationR(StationReader me);

	/**
	 * Get the cost to passe $$threw the inter
	 * @return the cost in local $$current
	 */
	public abstract float getCost();

	/**
	 * By giving one of the station, you obtain the route of the other station of the intersection
	 * 
	 * @param me
	 *            the station you know
	 * @return the route of the other station, or nothing if the station given is not in the intersection
	 */
	public abstract RouteReader getOtherRouteR(StationReader me);

	/**
	 * Allow to know if the inter is enable, that mean if you can use it
	 * 
	 * @return true if you can use it
	 */
	public boolean isEnable();

}
