package graphNetwork;

/**
 *  
 * @author iGo
 */
public interface InterReader {

	/**
	 * Get the cost to passe $$threw the inter
	 * 
	 * @return the cost in local $$current
	 */
	public abstract float getCost();

	/**
	 * Get the kind of intersection
	 * 
	 * @return the kind {pedestrian, rolling, wheel chair?}
	 */
	public abstract String getKindOfInter();

	/**
	 * By giving one of the station, you obtain the route of the other station of the intersection
	 * 
	 * @param me
	 *            the station you know
	 * @return the route of the other station, or nothing if the station given is not in the intersection
	 */
	public abstract RouteReader getOtherRouteR(StationReader me);

	/**
	 * By giving one of the station, you obtain the other station of the intersection
	 * 
	 * @param me
	 *            the station you know
	 * @return the other station, or nothing if the station given is not in the intersection
	 */
	public abstract StationReader getOtherStationR(StationReader me);

	/**
	 * Get the time between the two station.
	 * 
	 * @return time in second
	 */
	public abstract int getTimeBetweenStations();

	/**
	 * Allow to know if the inter is enable, that mean if you can use it
	 * 
	 * @return true if you can use it
	 */
	public boolean isEnable();

	/**
	 * Allow to know if we have to go out from one station to reache the second
	 * 
	 * @return true if you have to go out from one station to reach the other
	 */
	public boolean isPedestrian();

	/**
	 * Allow to know the kind of inter : if it's an inter between to station by a route, or an inter where you have to
	 * step down from your train/subway,...
	 * 
	 * @return true if it's an inter on a Route (you stay in your train/subway/trolley to use it)
	 */
	public boolean isRouteLink();

}
