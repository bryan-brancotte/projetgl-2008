package graphNetwork;

import java.util.Collection;

public class GraphNetwork implements GraphNetworkBuilder {

	/**
	 * @uml.property name="service"
	 * @uml.associationEnd readOnly="true" multiplicity="(0 -1)" inverse="graphNetwork:graphNetwork.Service"
	 */
	private Collection<Service> service;

	/**
	 * Getter of the property <tt>service</tt>
	 * 
	 * @return Returns the service.
	 * @uml.property name="service"
	 */
	@Override
	public Collection<ServiceReader> getService() {
		return service;
	}

	/**
	 * @uml.property name="station"
	 * @uml.associationEnd readOnly="true" multiplicity="(0 -1)" inverse="graphNetwork:graphNetwork.Station"
	 */
	private Collection<Station> station;

	/**
	 * Getter of the property <tt>station</tt>
	 * 
	 * @return Returns the station.
	 * @uml.property name="station"
	 */
	@Override
	public Collection<StationReader> getStation() {
		return station;
	}

	/**
	 * @uml.property name="route"
	 * @uml.associationEnd readOnly="true" multiplicity="(0 -1)" inverse="graphNetwork:graphNetwork.Route"
	 */
	private Collection<Route> route;

	/**
	 * Getter of the property <tt>route</tt>
	 * 
	 * @return Returns the route.
	 * @uml.property name="route"
	 */
	@Override
	public Collection<RouteReader> getRoute() {
		return route;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void endBuilding() {
		// TODO Auto-generated method stub

	}

}
