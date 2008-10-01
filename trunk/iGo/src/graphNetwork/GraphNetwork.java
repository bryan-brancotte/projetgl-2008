package graphNetwork;


import java.util.Collection;
import java.util.LinkedList;

public class GraphNetwork implements GraphNetworkBuilder {

	/**
	 * @uml.property name="stations"
	 * @uml.associationEnd multiplicity="(0 -1)" inverse="graphNetwork:graphNetwork.Station"
	 */
	private LinkedList<Station> stations;

	/**
	 * Getter of the property <tt>stations</tt>
	 * 
	 * @return Returns the stations.
	 * @uml.property name="stations"
	 */
	public LinkedList<Station> getStations() {
		return stations;
	}

	/**
	 * Setter of the property <tt>stations</tt>
	 * 
	 * @param stations
	 *        The stations to set.
	 * @uml.property name="stations"
	 */
	public void setStations(Collection<Station> stations) {
		this.stations = new LinkedList<Station>(stations);
	}

	/**
	 * @uml.property name="services"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" inverse="graphNetwork:graphNetwork.Service"
	 */
	private LinkedList<Service> services;

	/**
	 * Getter of the property <tt>services</tt>
	 * @return Returns the services.
	 * @uml.property name="services"
	 */
	public LinkedList<Service> getServices() {
		return services;
	}

	/**
	 * Setter of the property <tt>services</tt>
	 * @param services
	 *        The services to set.
	 * @uml.property name="services"
	 */
	public void setServices(LinkedList<Service> services) {
		this.services = services;
	}

	/**
	 * @uml.property name="routes"
	 * @uml.associationEnd multiplicity="(0 -1)" inverse="graphNetwork:graphNetwork.Route"
	 */
	private LinkedList<Route> routes;

	/**
	 * Getter of the property <tt>routes</tt>
	 * @return Returns the routes.
	 * @uml.property name="routes"
	 */
	public LinkedList<Route> getRoutes() {
		return routes;
	}

	/**
	 * Setter of the property <tt>routes</tt>
	 * @param routes
	 *        The routes to set.
	 * @uml.property name="routes"
	 */
	public void setRoutes(LinkedList<Route> routes) {
		this.routes = routes;
	}

	public Collection<RouteR> getRoutesR() {
		return new LinkedList<RouteR>(this.getRoutes());
	}

	public void endBuilding() {
		// TODO Auto-generated method stub

	}

	public void reset() {
		// TODO Auto-generated method stub

	}

	public Collection<ServiceR> getServicesR() {
		return new LinkedList<ServiceR>(this.getServices());
	}

	public Collection<StationR> getStationsR() {
		return new LinkedList<StationR>(this.getStations());
	}
}
