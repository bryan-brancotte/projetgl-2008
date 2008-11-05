package graphNetwork;

/**
 *  
 * @author iGo
 */
public interface GraphNetworkReader {

	/**
	 */
	public abstract KindRouteReader getKindFromString(String kindOf);

	/**
	 */
	public abstract KindRouteReader[] getKinds();

	/**
	 * Search and return in readOnlyMode the route who have this id.
	 * 
	 * @param id
	 *            the id of the route to find
	 * @return the route
	 */
	public abstract RouteReader getRouteR(int id);

	/**
	 * Return the first route in readOnlyMode who have this name.
	 * 
	 * @param name
	 *            the name of the route to find
	 * @return the route
	 */
	public abstract RouteReader getRouteR(String name);

	/**
	 * Getter of routes. Give all the route (line) of the network in readOnlyMode.
	 * 
	 * @return Returns the routes.
	 */
	public RouteReader[] getRoutesR();

	/**
	 * Search and return in readOnlyMode the service who have this id.
	 * 
	 * @param id
	 *            the id of the service to find
	 * @return the service
	 */
	public abstract ServiceReader getServiceR(int id);

	/**
	 * Return the first service in readOnlyMode who have this name.
	 * 
	 * @param name
	 *            the name of the service to find
	 * @return the service
	 */
	public abstract ServiceReader getServiceR(String name);

	/**
	 * Getter of services. Give all the Services avaible in the network in readOnlyMode.
	 * 
	 * @return Returns the services.
	 */
	public ServiceReader[] getServicesR();

	/**
	 * Search and return in readOnlyMode the station who have this id.
	 * 
	 * @param id
	 *            the id of the station to find
	 * @return the service
	 */
	public abstract StationReader getStationR(int id);

	/**
	 * Return the first station in readOnlyMode who have this name.
	 * 
	 * @param name
	 *            the name of the station to find
	 * @return the service
	 */
	public abstract StationReader getStationR(String name);

	/**
	 * Getter of stations. Give all the stations of the network in readOnlyMode.
	 * 
	 * @return Returns the stations.
	 */
	public StationReader[] getStationsR();

	/**
	 * Return the cost to go from the outside to the network by this kind of route.
	 * 
	 * @param kind
	 *            the kind of your entry point
	 * @return the cost, or 0 if the cost is unkown
	 */
	public abstract float getEntryCost(KindRouteReader kind);

}
