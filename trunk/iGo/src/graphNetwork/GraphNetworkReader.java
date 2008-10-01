package graphNetwork;

import java.util.Collection;

public interface GraphNetworkReader {

	/**
	 * Getter of services. Give all the Services avaible in the network.
	 * 
	 * @return Returns the services.
	 */
	public Collection<ServiceR> getServices();

	/**
	 * Getter of stations. Give all the stations of the network.
	 * 
	 * @return Returns the stations.
	 */
	public Collection<StationR> getStations();

	/**
	 * Getter of routes. Give all the route (line) of the network.
	 * 
	 * @return Returns the routes.
	 */
	public Collection<RouteR> getRoutes();
}
