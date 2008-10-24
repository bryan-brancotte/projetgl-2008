package graphNetwork;

import graphNetwork.exception.StationNotOnRoadException;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;

import java.util.Collection;
import java.util.LinkedList;
import java.util.MissingResourceException;

public class GraphNetwork implements GraphNetworkBuilder {

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

	public GraphNetwork() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @uml.property name="stations"
	 * @uml.associationEnd multiplicity="(0 -1)" inverse="graphNetwork:graphNetwork.Station"
	 */
	private LinkedList<Station> stations;

	@Override
	public void addKind(String newKindOf) {
		KindRoute.addKind(newKindOf);
	}

	@Override
	public Route addRoute(Route nvRoute) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Route addRoute(String id, String name, String kinfOf) throws ViolationOfUnicityInIdentificationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service addService(int id, String name) throws ViolationOfUnicityInIdentificationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service addService(Service nvService) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addServiceToStation(Station station, Service service) {
		// TODO Auto-generated method stub

	}

	@Override
	public Station addStation(int id, String name) throws ViolationOfUnicityInIdentificationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Station addStation(Station nvStation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addStationToRoute(Route origine, Station stationToAdd, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public PathInGraphBuilder getInstancePathInGraphBuilder() {
		return new PathInGraph(this);
	}

	@Override
	public Collection<Inter> getInters(Station stationA, Station stationB) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KindRouteReader getKindFromString(String kindOf) {
		return KindRouteReader.getKindFromString(kindOf);
	}

	@Override
	public KindRouteReader[] getKinds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Route getRoute(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Route getRoute(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RouteReader getRouteR(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RouteReader getRouteR(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Getter of the property <tt>routes</tt>
	 * 
	 * @return Returns the routes.
	 * @uml.property name="routes"
	 */
	@Override
	public LinkedList<Route> getRoutes() {
		return routes;
	}

	@Override
	public RouteReader[] getRoutesR() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service getService(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service getService(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceReader getServiceR(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceReader getServiceR(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Getter of the property <tt>services</tt>
	 * 
	 * @return Returns the services.
	 * @uml.property name="services"
	 */
	@Override
	public LinkedList<Service> getServices() {
		return services;
	}

	@Override
	public ServiceReader[] getServicesR() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Station getStation(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Station getStation(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StationReader getStationR(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StationReader getStationR(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Getter of the property <tt>stations</tt>
	 * 
	 * @return Returns the stations.
	 * @uml.property name="stations"
	 */
	@Override
	public LinkedList<Station> getStations() {

		return stations;
	}

	@Override
	public StationReader[] getStationsR() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void linkStation(Route routeOrigin, Station stationOrigin, Route routeDestination,
			Station stationDestination, float cost, int time, boolean pedestrian) throws StationNotOnRoadException,
			MissingResourceException {
		// TODO Auto-generated method stub

	}

	@Override
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

	@Override
	public void resetEnables() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getEntryCost(KindRouteReader kind) {
		// TODO Auto-generated method stub
		return 0;
	}
}
