package tools.streamInFolder.graphReaderFolder;


public class Station {

	/**
	 * @uml.property  name="route"
	 * @uml.associationEnd  inverse="station:tools.streamInFolder.graphReaderFolder.Route"
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
	 * @uml.property  name="service"
	 * @uml.associationEnd  inverse="station:tools.streamInFolder.graphReaderFolder.Service"
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

}
