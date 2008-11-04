package graphNetwork;

import graphNetwork.exception.StationNotOnRoadException;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;

import java.util.Collection;
import java.util.LinkedList;
import java.util.MissingResourceException;

public interface GraphNetworkBuilder extends GraphNetworkReader {

	/**
	 * 
	 * Add a new kind to the existing kinds 
	 * @param newKindOf the name of the new kinds
	 */
	public abstract void addKind(String newKindOf);

	/**
	 * Add a new route to the routes' collection.
	 * 
	 * @param nvRoute
	 *            the route to add
	 * @return your route
	 * @throws ViolationOfUnicityInIdentificationException
	 *             if you specify a id already used
	 */
	public abstract Route addRoute(Route nvRoute) throws ViolationOfUnicityInIdentificationException;

	/**
	 * Create a new route, and add it to the routes' collection.
	 * 
	 * @param id
	 *            the id of the route, it must be a none used id
	 * @param name
	 *            the name of the route
	 * @param name
	 *            the kind of route it is, a subway, a train, a boat
	 * @return
	 * @throws ViolationOfUnicityInIdentificationException
	 *             if you specify a id already used
	 */
	public abstract Route addRoute(String id, String name, String kinfOf)
			throws ViolationOfUnicityInIdentificationException;

	/**
	 * Create a new service, and it to the services' collection.
	 * 
	 * @param id
	 *            the id of the service, it must be a none used id
	 * @param name
	 *            the name of the service
	 * @return
	 * @throws ViolationOfUnicityInIdentificationException
	 *             if you specify a id already used
	 */
	public abstract Service addService(int id, String name) throws ViolationOfUnicityInIdentificationException;

	/**
	 * Add a new service to the services' collection.
	 * 
	 * @param nvService
	 *            the service to add
	 * @return your route
	 * @throws ViolationOfUnicityInIdentificationException
	 *             if you specify a id already used
	 */
	public abstract Service addService(Service nvService) throws ViolationOfUnicityInIdentificationException;

	/**
	 * Add a service to the station, if the service is already present, it do nothing.
	 * 
	 * @param station
	 *            the station how will have a new service
	 * @param service
	 *            the new service
	 */
	public abstract void addServiceToStation(Station station, Service service);

	/**
	 * Create a new station, and it to the stations' collection.
	 * 
	 * @param id
	 *            the id of the station, it must be a none used id
	 * @param name
	 *            the name of the station
	 * @return the object created
	 * @throws ViolationOfUnicityInIdentificationException
	 *             if you specify a id already used
	 */
	public abstract Station addStation(int id, String name) throws ViolationOfUnicityInIdentificationException;

	/**
	 * Add a new station to the stations' collection.
	 * 
	 * @param nvStation
	 *            the new station
	 * @return the object created
	 * @throws ViolationOfUnicityInIdentificationException
	 *             if you specify a id already used
	 */
	public abstract Station addStation(Station nvStation) throws ViolationOfUnicityInIdentificationException;

	/**
	 * Add to the specified route, a station at the end. The time between the previous last station and this one is also
	 * specified in the argument. If station is already in the road, we do nothing.
	 * 
	 * @param origine
	 *            The route/line where we want to add a station
	 * @param stationToAdd
	 *            the station to add
	 * @param time
	 *            the time between the previous station ans this one
	 */
	public abstract void addStationToRoute(Route origine, Station stationToAdd, int time);

	/**
	 * Get a new instance of a PathInGraphBuilder linked to this graph
	 * @return the new instance of the PathInGraph
	 */
	public abstract PathInGraphBuilder getInstancePathInGraphBuilder();

	/**
	 * Return the Inters which are between Station A and B
	 * 
	 * @param stationA
	 * @param stationB
	 * @return the inter between A and B
	 */
	public abstract Collection<Inter> getInters(Station stationA, Station stationB);

	/**
	 * Search and return the route who have this id
	 * 
	 * @param id
	 *            the id of the route to find
	 * @return the route
	 */
	public abstract Route getRoute(int id);

	/**
	 * Return the first route who have this name
	 * 
	 * @param name
	 *            the name of the route to find
	 * @return the route
	 */
	public abstract Route getRoute(String name);

	/**
	 * Getter of routes. Give all the route (line) of the network in readOnlyMode.
	 * 
	 * @return Returns the routes.
	 */
	public LinkedList<Route> getRoutes();

	/**
	 * Search and return the service who have this id
	 * 
	 * @param id
	 *            the id of the service to find
	 * @return the service
	 */
	public abstract Service getService(int id);

	/**
	 * Return the first service who have this name
	 * 
	 * @param name
	 *            the name of the service to find
	 * @return the service
	 */
	public abstract Service getService(String name);

	/**
	 * Getter of services. Give all the Services avaible in the network in readOnlyMode.
	 * 
	 * @return Returns the services.
	 */
	public LinkedList<Service> getServices();

	/**
	 * Search and return the station who have this id
	 * 
	 * @param id
	 *            the id of the station to find
	 * @return the service
	 */
	public abstract Station getStation(int id);

	/**
	 * Return the first station who have this name
	 * 
	 * @param name
	 *            the name of the station to find
	 * @return the service
	 */
	public abstract Station getStation(String name);

	/**
	 * Getter of stations. Give all the stations of the network in readOnlyMode.
	 * 
	 * @return Returns the stations.
	 */
	public LinkedList<Station> getStations();

	/**
	 * Link two stations as an interchange. The link is monodirectional. You have to specified the cost in this way, and
	 * also the time. If you specified a second time the same link, it will orverwrite the first one.
	 * 
	 * @param routeOrigin
	 *            on which road the starting station is.
	 * @param stationOrigin
	 *            from where the link start.
	 * @param routeDestination
	 *            on which road the ending station is.
	 * @param stationDestination
	 *            to where the link go.
	 * @param cost
	 *            How many € to use it.
	 * @param time
	 *            How much time to use it.
	 * @param pedestrian
	 *            ?????
	 * @throws StationNotOnRoadException
	 *             throwed if a staion is't on it's route
	 * @throws MissingResourceException
	 *             throwed if one of the object is null
	 */
	public abstract void linkStation(Route routeOrigin, Station stationOrigin, Route routeDestination,
			Station stationDestination, float cost, int time, boolean pedestrian) throws StationNotOnRoadException,
			MissingResourceException;

	/**
	 * Do a complet reset of the GraphNetworkBuilder
	 */
	public abstract void reset();

		
		/**
		 * Reset all the component to the state "enable"
		 */
		public abstract void resetEnables();
		

}
