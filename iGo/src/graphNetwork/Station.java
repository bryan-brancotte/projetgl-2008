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
	 * @uml.property  name="interStation"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="stationA:graphNetwork.InterStation"
	 */
	private Collection interStation;

	/**
	 * Getter of the property <tt>interStation</tt>
	 * @return  Returns the interStation.
	 * @uml.property  name="interStation"
	 */
	public Collection getInterStation() {
		return interStation;
	}

	/**
	 * Setter of the property <tt>interStation</tt>
	 * @param interStation  The interStation to set.
	 * @uml.property  name="interStation"
	 */
	public void setInterStation(Collection interStation) {
		this.interStation = interStation;
	}

}
