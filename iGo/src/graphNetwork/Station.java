package graphNetwork;

import java.util.LinkedList;

public class Station implements StationR {

	/**
	 * @uml.property name="service"
	 * @uml.associationEnd inverse="station:graphNetwork.Service"
	 */
	private Service service;

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
	public void setService(Service service)

	{
		this.service = service;
	}

	/**
	 * @uml.property name="inter"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" inverse="stationA:graphNetwork.Inter"
	 */
	private LinkedList<Inter> inter;

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

	public InterR[] getInterchangeR() {
		return this.getInter().toArray(new InterR[0]);
	}

	public ServiceR getServiceR() {
		return this.getService();
	}

	public LinkedList<Inter> getInter() {
		return this.inter;
	}

	public void setInter(LinkedList<Inter> inter) {
		this.inter = inter;
	}

	public RouteR[] getRoutesR() {
		return this.getRoutes().toArray(new RouteR[0]);
	}

	/**
	 * @uml.property name="routes"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" inverse="stations:graphNetwork.Route"
	 */
	private LinkedList<Route> routes;

	/**
	 * Getter of the property <tt>routes</tt>
	 * 
	 * @return Returns the routes.
	 * @uml.property name="routes"
	 */
	public LinkedList<Route> getRoutes() {
		return routes;
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

}
