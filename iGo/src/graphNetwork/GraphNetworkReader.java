package graphNetwork;

import java.util.Collection;

/**
 * 
 * @author iGo
 */
public interface GraphNetworkReader {

	/**
	 * Retourne le type associé au nom passée en paramètre. Si le type n'existe pas on retourne null.
	 * 
	 * @param kindOf
	 *            le nom du type
	 * @return le type en lecture seul, ou null si le type n'est pas associé.
	 */
	public abstract KindRouteReader getKindFromString(String kindOf);

	/**
	 * Return the all the kinds in read-only mode
	 * 
	 * @return an array with all the kinds
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
	 * Return the Inters which are between Station A and B
	 * 
	 * @param stationA
	 * @param stationB
	 * @return the inter between A and B
	 */
	public abstract InterReader[] getInters(StationReader stationA, StationReader stationB);

	/**
	 * Return the cost to go from the outside to the network by this kind of route.
	 * 
	 * @param kind
	 *            the kind of your entry point
	 * @return the cost, or 0 if the cost is unkown
	 */
	public abstract float getEntryCost(KindRouteReader kind);

}
