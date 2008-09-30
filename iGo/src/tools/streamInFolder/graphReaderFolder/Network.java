package tools.streamInFolder.graphReaderFolder;

import java.util.Collection;


public class Network {

	/**
	 * @uml.property   name="service"
	 * @uml.associationEnd   multiplicity="(0 -1)" inverse="network:tools.streamInFolder.graphReaderFolder.Service"
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
	 * @uml.property   name="station"
	 * @uml.associationEnd   multiplicity="(0 -1)" inverse="network:tools.streamInFolder.graphReaderFolder.Station"
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
	 * @uml.property   name="interchange"
	 * @uml.associationEnd   multiplicity="(0 -1)" inverse="network:tools.streamInFolder.graphReaderFolder.Interchange"
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

	/**
	 * @uml.property   name="route"
	 * @uml.associationEnd   multiplicity="(0 -1)" inverse="network:tools.streamInFolder.graphReaderFolder.Route"
	 */
	private Collection route;

	/**
	 * Getter of the property <tt>route</tt>
	 * @return  Returns the route.
	 * @uml.property  name="route"
	 */
	public Collection getRoute() {
		return route;
	}

	/**
	 * Setter of the property <tt>route</tt>
	 * @param route  The route to set.
	 * @uml.property  name="route"
	 */
	public void setRoute(Collection route) {
		this.route = route;
	}

}
