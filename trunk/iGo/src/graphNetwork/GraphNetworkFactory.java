package graphNetwork;

import graphNetwork.exception.StationNotOnRoadException;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.MissingResourceException;

/**
 * 
 * @author iGo
 */
public class GraphNetworkFactory {

	/**
	 * @uml.property name="routes"
	 * @uml.associationEnd multiplicity="(0 -1)" inverse="graphNetwork:graphNetwork.Route"
	 */
	private LinkedList<Route> routes;

	/**
	 * @uml.property name="services"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" inverse="graphNetwork:graphNetwork.Service"
	 */
	private LinkedList<Service> services;

	public GraphNetworkFactory() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @uml.property name="stations"
	 * @uml.associationEnd multiplicity="(0 -1)" inverse="graphNetwork:graphNetwork.Station"
	 */
	private LinkedList<Station> stations;

	public void addKind(String newKindOf) {
		KindRoute.addKind(newKindOf);
	}

	public Route addRoute(String id, String kinfOf) throws ViolationOfUnicityInIdentificationException {
		// TODO Auto-generated method stub
		return null;
	}

	public Service addService(int id, String name) throws ViolationOfUnicityInIdentificationException {
		// TODO Auto-generated method stub
		return null;
	}

	public void addServiceToStation(Station station, Service service) {
		// TODO Auto-generated method stub

	}

	public Station addStation(int id, String name) throws ViolationOfUnicityInIdentificationException {
		// TODO Auto-generated method stub
		return null;
	}

	public void addStationToRoute(Route origine, Station stationToAdd, int time) {
		// TODO Auto-generated method stub

	}

	public PathInGraph getInstancePathInGraph() {
		return new PathInGraph(this);
	}

	public Iterator<Inter> getInters(Station stationA, Station stationB) {
		// TODO Auto-generated method stub
		return null;
	}

	public KindRoute getKindFromString(String kindOf) {
		return KindRoute.getKindFromString(kindOf);
	}

	public Iterator<KindRoute> getKinds() {
		// TODO Auto-generated method stub
		return null;
	}

	public Route getRoute(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterator<Route> getRoutes() {
		return routes.iterator();
	}

	public Service getService(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterator<Service> getServices() {
		return services.iterator();
	}

	public Station getStation(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterator<Station> getStations() {

		return stations.iterator();
	}

	public void linkStation(Route routeOrigin, Station stationOrigin, Route routeDestination,
			Station stationDestination, float cost, int time, boolean pedestrian) throws StationNotOnRoadException,
			MissingResourceException {
		// TODO Auto-generated method stub

	}

	public void reset() {
		// TODO Auto-generated method stub

	}

	/**
	 * Setter of the property <tt>routes</tt>
	 * 
	 * @param routes
	 *            The routes to set.
	 * @uml.property name="routes"
	 */
	public void setRoutes(LinkedList<Route> routes) {
		this.routes = routes;
	}

	/**
	 * Setter of the property <tt>services</tt>
	 * 
	 * @param services
	 *            The services to set.
	 * @uml.property name="services"
	 */
	public void setServices(LinkedList<Service> services) {
		this.services = services;
	}

	/**
	 * Setter of the property <tt>stations</tt>
	 * 
	 * @param stations
	 *            The stations to set.
	 * @uml.property name="stations"
	 */
	public void setStations(Collection<Station> stations) {
		this.stations = new LinkedList<Station>(stations);
	}

	public void resetEnables() {
		// TODO Auto-generated method stub

	}

	public float getEntryCost(KindRouteReader kind) {
		// TODO Auto-generated method stub
		return 0;
	}
}
