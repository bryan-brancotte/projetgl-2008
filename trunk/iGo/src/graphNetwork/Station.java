package graphNetwork;

import java.util.Collection;

public class Station {

	/**
	 * @uml.property name="route"
	 * @uml.associationEnd inverse="station:graphNetwork.Route"
	 */
	private RouteR route;

	/* (non-Javadoc)
	 * @see graphNetwork.Station#getRoute()
	 */
	/* (non-Javadoc)
	 * @see graphNetwork.StationR#getRoute()
	 */
	public RouteR getRoute() {
		return route;
	}

	/**
	 * Setter of the property <tt>route</tt>
	 * 
	 * @param route
	 *            The route to set.
	 * @uml.property name="route"
	 */
	public void setRoute(RouteR route) {
		this.route = route;
	}

	/**
	 * @uml.property name="service"
	 * @uml.associationEnd inverse="station:graphNetwork.Service"
	 */
	private Service service;

	/* (non-Javadoc)
	 * @see graphNetwork.Station#getService()
	 */
	public Service getService() {
		return service;
	}

	/**
	 * Setter of the property <tt>service</tt>
	 * 
	 * @param service
	 *            The service to set.
	 * @uml.property name="service"
	 */
	public void setService(Service service) {
		this.service = service;
	}

	/**
	 * @uml.property name="interchange"
	 * @uml.associationEnd multiplicity="(0 -1)" inverse="stationA:graphNetwork.Inter"
	 */
	private Collection interchange;

	/* (non-Javadoc)
	 * @see graphNetwork.Station#getInterchange()
	 */
	/* (non-Javadoc)
	 * @see graphNetwork.Station#getInterchange()
	 */
	/* (non-Javadoc)
	 * @see graphNetwork.StationR#getInterchange()
	 */
	public Collection getInterchange() {
		return interchange;
	}

	/**
	 * Setter of the property <tt>interchange</tt>
	 * 
	 * @param interchange
	 *            The interchange to set.
	 * @uml.property name="interchange"
	 */
	public void setInterchange(Collection interchange) {
		this.interchange = interchange;
	}

	/** 
	 * @uml.property name="name" readOnly="true"
	 */
	private String name = "";
	
	public String getName() {
		return name;
	}

	/** 
	 * @uml.property name="id" readOnly="true"
	 */
	private int id;
	
	public int getId() {
		return id;
	}

}
