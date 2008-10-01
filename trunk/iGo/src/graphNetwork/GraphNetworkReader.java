package graphNetwork;


public interface GraphNetworkReader {

	/**
	 * Getter of services. Give all the Services avaible in the network.
	 * 
	 * @return Returns the services.
	 */
	public ServiceR[] getServicesR();

	/**
	 * Getter of stations. Give all the stations of the network.
	 * 
	 * @return Returns the stations.
	 */
	public StationR[] getStationsR();

	/**
	 * Getter of routes. Give all the route (line) of the network.
	 * 
	 * @return Returns the routes.
	 */
	public RouteR[] getRoutesR();
}
