package graphNetwork;

import java.util.Collection;


public class GraphNetwork implements GraphNetworkBuilder {

	@Override
	public void addStationFake(Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection getStationsFake() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @uml.property  name="service"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="graphNetwork:graphNetwork.Service"
	 */
	private Collection service;

	/**
	 * Getter of the property <tt>service</tt>
	 * @return  Returns the service.
	 * @uml.property  name="service"
	 */
	public Collection getService() {
		return service;
	}

	/**
	 * Setter of the property <tt>service</tt>
	 * @param service  The service to set.
	 * @uml.property  name="service"
	 */
	public void setService(Collection service) {
		this.service = service;
	}

	/**
	 * @uml.property  name="station"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="graphNetwork:graphNetwork.Station"
	 */
	private Collection station;

	/**
	 * Getter of the property <tt>station</tt>
	 * @return  Returns the station.
	 * @uml.property  name="station"
	 */
	public Collection getStation() {
		return station;
	}

	/**
	 * Setter of the property <tt>station</tt>
	 * @param station  The station to set.
	 * @uml.property  name="station"
	 */
	public void setStation(Collection station) {
		this.station = station;
	}

	/** 
	 * @uml.property name="route"
	 * @uml.associationEnd inverse="graphNetwork:graphNetwork.Route"
	 */
	private Route route;

	/** 
	 * Getter of the property <tt>route</tt>
	 * @return  Returns the route.
	 * @uml.property  name="route"
	 */
	public Route getRoute() {
		return route;
	}

	/** 
	 * Setter of the property <tt>route</tt>
	 * @param route  The route to set.
	 * @uml.property  name="route"
	 */
	public void setRoute(Route route) {
		this.route = route;
	}

}
