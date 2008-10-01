package graphNetwork;

import java.util.Collection;

public class Station implements StationReader {

	/**
	 * @uml.property name="route"
	 * @uml.associationEnd inverse="station:graphNetwork.Route"
	 */
	private RouteReader route;

	/**
	 * Getter of the property <tt>route</tt>
	 * 
	 * @return Returns the route.
	 * @uml.property name="route"
	 */
	public RouteReader getRoute() {
		return route;
	}

	/**
	 * Setter of the property <tt>route</tt>
	 * 
	 * @param route
	 *            The route to set.
	 * @uml.property name="route"
	 */
	public void setRoute(RouteReader route) {
		this.route = route;
	}

	/**
	 * @uml.property name="service"
	 * @uml.associationEnd inverse="station:graphNetwork.Service"
	 */
	private ServiceReader service;

	/**
	 * Getter of the property <tt>service</tt>
	 * 
	 * @return Returns the service.
	 * @uml.property name="service"
	 */
	public ServiceReader getService() {
		return service;
	}

	/**
	 * Setter of the property <tt>service</tt>
	 * 
	 * @param service
	 *            The service to set.
	 * @uml.property name="service"
	 */
	public void setService(ServiceReader service) {
		this.service = service;
	}

	/**
	 * @uml.property name="interchange"
	 * @uml.associationEnd multiplicity="(0 -1)" inverse="stationA:graphNetwork.Inter"
	 */
	private Collection interchange;

	/* (non-Javadoc)
	 * @see graphNetwork.StationReader#getInterchange()
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

	/* (non-Javadoc)
	 * @see graphNetwork.StationReader#getName()
	 */
	public String getName() {
		return name;
	}

	/** 
	 * @uml.property name="id" readOnly="true"
	 */
	private int id;

	/* (non-Javadoc)
	 * @see graphNetwork.StationReader#getId()
	 */
	public int getId() {
		return id;
	}

}
