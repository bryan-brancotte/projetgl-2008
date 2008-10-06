package graphNetwork;

public interface RouteReader {

	/**
	 * return all the station used by the station
	 * 
	 * @return an array of the station (no information of order)
	 */
	public abstract StationReader[] getStationsR();

	/**
	 * get the Route's Id
	 * 
	 * @return the id
	 */
	public abstract String getId();

	/**
	 * Allow to know if the route is enable, that mean if you can passe it
	 * 
	 * @return true if you can passe it
	 */
	public boolean isEnable();

	/**
	 * return the route's kind in readOnly mode
	 * 
	 * @return the kind
	 */
	public KindRouteReader getKindRouteR();

}
