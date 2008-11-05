package graphNetwork;

/**
 *  
 * @author iGo
 */
public interface RouteReader {

	/**
	 * get the Route's Id
	 * 
	 * @return the id
	 */
	public abstract String getId();

	/**
	 * return the route's kind in readOnly mode
	 * 
	 * @return the kind
	 */
	public KindRouteReader getKindRouteR();

	/**
	 * return all the station used by the station
	 * 
	 * @return an array of the station (no information of order)
	 */
	public abstract StationReader[] getStationsR();

	/**
	 * Allow to know if the route is enable, that mean if you can passe it
	 * 
	 * @return true if you can passe it
	 */
	public boolean isEnable();

	/**
	 * Return the status of the station for this route, and in général.
	 * 
	 * @param idStation
	 * @return false If the station is disabled on this route, or disable in général. true if it enable for this route,
	 *         and in général.
	 */
	public abstract boolean isStationEnable(int idStation);

}
