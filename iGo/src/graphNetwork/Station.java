package graphNetwork;

import java.util.Collection;


public class Station {

	/**
	 * @uml.property   name="route"
	 * @uml.associationEnd   inverse="station:graphNetwork.Route"
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

	/** 
	 * @uml.property name="service"
	 * @uml.associationEnd inverse="station:graphNetwork.Service"
	 */
	private Service service;

	/** 
	 * Getter of the property <tt>service</tt>
	 * @return  Returns the service.
	 * @uml.property  name="service"
	 */
	public Service getService() {
		return service;
	}

	/** 
	 * Setter of the property <tt>service</tt>
	 * @param service  The service to set.
	 * @uml.property  name="service"
	 */
	public void setService(Service service) {
		this.service = service;
	}

	/**
	 * @uml.property  name="interchange"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="stationA:graphNetwork.Inter"
	 */
	private Collection interchange;

	/**
	 * Getter of the property <tt>interchange</tt>
	 * @return  Returns the interchange.
	 * @uml.property  name="interchange"
	 */
	public Collection getInterchange() {
		return interchange;
	}

	/**
	 * Setter of the property <tt>interchange</tt>
	 * @param interchange  The interchange to set.
	 * @uml.property  name="interchange"
	 */
	public void setInterchange(Collection interchange) {
		this.interchange = interchange;
	}

}
