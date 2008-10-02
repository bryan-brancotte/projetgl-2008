package graphNetwork;

public interface GraphNetworkReader {

	/**
	 * Getter of services. Give all the Services avaible in the network in readOnlyMode.
	 * 
	 * @return Returns the services.
	 */
	public ServiceReader[] getServicesR();

	/**
	 * Getter of stations. Give all the stations of the network in readOnlyMode.
	 * 
	 * @return Returns the stations.
	 */
	public StationReader[] getStationsR();

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
}
